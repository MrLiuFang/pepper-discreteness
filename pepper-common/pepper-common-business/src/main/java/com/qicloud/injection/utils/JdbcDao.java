package com.pepper.business.utils;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.pepper.utils.Util;
import com.pepper.vo.Pager;

/**
 * 
 * SQL执行类
 * 
 */
@Component
public class JdbcDao extends JdbcTemplate {

	/**
	 * 继承JdbcTemplate需要注入DataSource，否则报异常： Caused by:
	 * java.lang.IllegalArgumentException: Property 'dataSource' is required
	 * 
	 * @param ds
	 */
	@Resource
	public void setDs(DataSource ds) {
		super.setDataSource(ds);
	}

	private static final String DB2 = ":db2:";

	/**
	 * 日志
	 */
	private static final Logger log = LoggerFactory.getLogger(JdbcDao.class);
	private static final String MYSQL = ":mysql:";
	private static final String ORACLE = ":oracle:";
	private static final String SQLSERVER = ":sqlserver:";
	private String JdbcURL = null;

	/**
	 * 兼容数据库字符串的拼接。
	 * 
	 * @param strings
	 * @return
	 */
	public String concat(String... strings) {
		List<String> sList = Arrays.asList(strings);
		if (isOracle() || isDB2()) {
			return concatStr(strings, "||");
		} else if (isMySQL()) {
			if (null != sList && sList.size() > 0) {
				return "CONCAT(" + sList.toString().substring(1, sList.toString().length() - 1) + ")";
			}
		} else if (isSqlServer()) {
			return concatStr(strings, "+");
		}
		return "";
	}

	private String concatStr(String[] strings, String link) {
		StringBuffer sb = new StringBuffer("");
		if (null != strings) {
			for (String s : strings) {
				sb.append(s + link);
			}
		}
		String sbStr = sb.toString();
		if (!Util.isEmpty(sbStr)) {
			return sbStr.substring(0, sbStr.length() - link.length());
		}
		return "";

	}

	/**
	 * 复杂原生sql语句分页
	 * 
	 * @param page
	 * @param pageSize
	 * @param sql
	 *            拼接好的查询语句
	 * @param args
	 *            查询语句的参数
	 * @param target
	 *            查询结果反封装的类型
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> Pager<T> list(Integer page, Integer pageSize, String sql, List<Object> args, Class<T> target) {
		List<T> list = query(converPageSql(sql.toString(), page, pageSize), args.toArray(),
				new BeanPropertyRowMapper(target));
		Long totalRow = (Long) queryForMap("select count(1) totalRow from (" + sql.toString() + ")ttr", args.toArray())
				.get("totalRow");
		Pager<T> pager = new Pager<T>();
		pager.setTotalRow(totalRow);
		pager.setPageNo(page);
		pager.setPageSize(pageSize);
		pager.setResults(list);
		return pager;
	}
	
	/**
	 * 复杂原生sql语句分页
	 * 
	 * @param page
	 * @param pageSize
	 * @param sql
	 *            拼接好的查询语句
	 * @param args
	 *            查询语句的参数
	 * @return
	 */
	public  Pager<Map<String,Object>> list(Integer page, Integer pageSize, String sql, List<Object> args) {
		List<Map<String,Object>> list = this.queryForList(converPageSql(sql.toString(), page, pageSize), args.toArray());
		Long totalRow = (Long) queryForMap("select count(1) totalRow from (" + sql.toString() + ")ttr", args.toArray()).get("totalRow");
		Pager<Map<String,Object>> pager = new Pager<Map<String,Object>>();
		pager.setTotalRow(totalRow);
		pager.setPageNo(page);
		pager.setPageSize(pageSize);
		pager.setResults(list);
		return pager;
	}

	/**
	 * 需要先注入SqlUtil对象后调用。 兼容数据库分页。
	 * Oracle,SQLServer,DB2時，別名ta,tb已經使用，sql不能再使用ta,tb這兩作為別名了。
	 * 
	 * @param jdbcType
	 * @param sql
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public String converPageSql(String sql, int page, int pageSize) {
		int start = (page - 1) * pageSize;
		int end = page * pageSize;
		StringBuffer buf = new StringBuffer();
		// oracle
		if (isOracle()) {
			if (page == 1) {
				buf.append("SELECT ta.*,ROWNUM RN FROM(");
				buf.append(sql);
				buf.append(") ta WHERE ROWNUM <=" + end);
			} else {
				buf.append("SELECT tb.* FROM (SELECT ta.*,ROWNUM RN FROM (");
				buf.append(sql);
				buf.append(") ta WHERE ROWNUM<=" + end + ")tb where tb.RN>" + start);
			}
			// mysql
		} else if (isMySQL()) {
			buf.append(sql);
			buf.append(" limit " + start + "," + pageSize);
			// sqlserver or db2
		} else if (isSqlServer() || isDB2()) {
			int idx = Util.trim(sql).toUpperCase().indexOf("ORDER BY");
			String order = Util.trim(sql).substring(idx);
			order = order.toUpperCase();
			order = order.replace("ORDER", "").replace("BY", "");
			String[] arr = Util.trim(order).split(",");
			String tmp = "";
			for (String s : arr) {
				String str = s;
				if (s.indexOf(".") != -1) {
					str = s.split("\\.")[1];
				}
				if (!Util.isEmpty(tmp)) {
					tmp += ",";
				}
				tmp += "ta." + Util.trim(str);
			}
			order = "ORDER BY " + tmp;

			String search = Util.trim(sql).substring(0, idx);
			buf.append("SELECT tb.* FROM (SELECT ta.*,ROW_NUMBER() over(" + order + ") RN");
			buf.append(" FROM (" + search + ") ta )tb ");
			buf.append("WHERE tb.RN>" + start + " AND tb.RN<=" + end);
		} else {
			buf.append(sql);
		}
		log.info("Conver sql for page:[" + buf.toString() + "]...");
		return buf.toString();
	}

	/**
	 * 调用无返回结果集的存储过程
	 * 
	 * @param spName
	 *            存储过程名称
	 * @param inVals
	 *            输入参数
	 * @return int
	 */
	public int execStoreProcedure(final String spName, final Vector<Object> inVals) {
		return execute(new CallableStatementCreator() {

			@Override
			public CallableStatement createCallableStatement(Connection conn) throws SQLException {
				String procName = "";
				procName = spName;
				procName += "(";
				// in values
				for (int i = 0; i < inVals.size(); i++) {
					procName += "?";
					if (i != inVals.size() - 1)
						procName += ",";
				}
				procName += ")";
				String storedProc = "{call " + procName + "}";// 调用的sql
				log.info("execStoreProcedure - " + storedProc);
				CallableStatement cs = conn.prepareCall(storedProc);
				setStoredProcedureValues(cs, inVals);
				return cs;
			}
		}, new CallableStatementCallback<Integer>() {
			@Override
			public Integer doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				return cs.executeUpdate();
			}
		});
	}

	/**
	 * 调用带输出参数的存储过程
	 * 
	 * @param spName
	 *            存储过程名
	 * @param inVals
	 *            输入参数
	 * @param outTypes
	 *            输出参数类型
	 * @return Vector<Object>输出结果值
	 */
	public Vector<Object> execStoreProcedureWithOutput(final String spName, final Vector<Object> inVals,
			final Vector<Integer> outTypes) {
		return execute(new CallableStatementCreator() {

			@Override
			public CallableStatement createCallableStatement(Connection conn) throws SQLException {
				String procName = "";
				procName = spName;
				procName += "(";
				// in values
				for (int i = 0; i < inVals.size(); i++) {
					procName += "?";
					if (i != inVals.size() - 1)
						procName += ",";
				}
				// out values
				for (int i = 0; i < outTypes.size(); i++) {
					if (inVals.size() > 0 || i > 0) {
						procName += ",";
					}
					procName += "?";
				}
				procName += ")";
				String storedProc = "{call " + procName + "}";// 调用的sql
				log.info("execStoreProcedure - " + storedProc);
				CallableStatement cs = conn.prepareCall(storedProc);
				setStoredProcedureValues(cs, inVals);
				// output
				for (int i = 0; i < outTypes.size(); i++) {
					cs.registerOutParameter(inVals.size() + i + 1, outTypes.get(i));
				}
				return cs;
			}
		}, new CallableStatementCallback<Vector<Object>>() {
			@Override
			public Vector<Object> doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				log.info("Running the Stored Procedure " + spName + "--" + cs.execute());
				// output
				Vector<Object> out = new Vector<Object>();
				for (int i = 0; i < outTypes.size(); i++) {
					out.add(cs.getObject(inVals.size() + i + 1));
				}
				return out;
			}
		});
	}

	private String getJdbcUrl() {
		if (Util.isEmpty(JdbcURL)) {
			JdbcURL = getJdbcUrlFromDataSource();
		}
		return JdbcURL;
	}

	/**
	 * 判断数据库类型
	 * 
	 * @return
	 */
	public String getJdbcUrlFromDataSource() {
		Connection connection = null;
		try {
			connection = getDataSource().getConnection();
			if (connection == null) {
				throw new IllegalStateException("Connection returned by DataSource [" + getDataSource() + "] was null");
			}
			return connection.getMetaData().getURL();
		} catch (SQLException e) {
			throw new RuntimeException("Could not get database url", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	/**
	 * 判断当前数据库是否为DB2，需要先注入SqlUtil对象后调用。
	 * 
	 * @return
	 */
	public boolean isDB2() {
		return isWhichDB(JdbcURL, DB2);
	}

	/**
	 * 判断当前数据库是否为MySQL，需要先注入SqlUtil对象后调用。
	 * 
	 * @return
	 */
	public boolean isMySQL() {
		return isWhichDB(JdbcURL, MYSQL);
	}

	/**
	 * 判断当前数据库是否为Oracle，需要先注入SqlUtil对象后调用。
	 * 
	 * @return
	 */
	public boolean isOracle() {
		return isWhichDB(JdbcURL, ORACLE);
	}

	/**
	 * 判断当前数据库是否为SqlServer，需要先注入SqlUtil对象后调用。
	 * 
	 * @return
	 */
	public boolean isSqlServer() {
		return isWhichDB(JdbcURL, SQLSERVER);
	}

	/**
	 * 判断当前数据库是否为某种数据库。
	 * 
	 * @param jdbcUrl
	 * @return
	 */
	public boolean isWhichDB(String jdbcUrl, String dBSign) {
		if (Util.isEmpty(jdbcUrl)) {
			jdbcUrl = getJdbcUrl();
		}
		return StringUtils.contains(jdbcUrl, dBSign);
	}

	/**
	 * 返回指定行数的数据
	 * 
	 * @param sql
	 * @param topnum
	 * @param args
	 * @return
	 */
	public List<Map<String, Object>> queryByNativeSQLTopN(String sql, final int topnum, Object... args) {
		return query(sql, args, new ResultSetExtractor<List<Map<String, Object>>>() {

			@Override
			public List<Map<String, Object>> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Map<String, Object>> list = new Vector<Map<String, Object>>();
				int num = 0;
				while (rs.next()) {
					if (num >= topnum) {
						break;
					}
					ResultSetMetaData rsmd = rs.getMetaData();
					Map<String, Object> row = new LinkedHashMap<String, Object>();
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						String colName = rsmd.getColumnName(i);
						row.put(colName, rs.getObject(colName));
					}
					list.add(row);
					num++;
				}
				return list;
			}
		});
	}

	/**
	 * 调用有返回结果集的存储过程
	 * 
	 * @param spName
	 *            存储过程名称
	 * @param inVals
	 *            输入参数
	 * @return 结果集
	 */
	public List<Map<String, Object>> queryStoreProcedure(final String spName, final Vector<Object> inVals) {
		return execute(new CallableStatementCreator() {

			@Override
			public CallableStatement createCallableStatement(Connection conn) throws SQLException {
				String procName = "";
				procName = spName;
				procName += "(";
				// in values
				for (int i = 0; i < inVals.size(); i++) {
					procName += "?";
					if (i != inVals.size() - 1)
						procName += ",";
				}
				procName += ")";
				String storedProc = "{call " + procName + "}";// 调用的sql
				log.info("execStoreProcedure - " + storedProc);
				CallableStatement cs = conn.prepareCall(storedProc);
				setStoredProcedureValues(cs, inVals);
				return cs;
			}
		}, new CallableStatementCallback<List<Map<String, Object>>>() {
			@Override
			public List<Map<String, Object>> doInCallableStatement(CallableStatement cs)
					throws SQLException, DataAccessException {
				List<Map<String, Object>> list = new Vector<Map<String, Object>>();
				ResultSet rs = cs.executeQuery();
				while (rs.next()) {
					ResultSetMetaData rsmd = rs.getMetaData();
					Map<String, Object> row = new LinkedHashMap<String, Object>();
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						String colName = rsmd.getColumnName(i);
						row.put(colName, rs.getObject(colName));
					}
					list.add(row);
				}
				return list;
			}
		});
	}

	/**
	 * 调用有返回结果集的存储过程
	 * 
	 * @param spName
	 *            存储过程名称
	 * @param inVals
	 *            输入参数
	 * @param outpus
	 *            输出参数
	 * @return 结果集
	 */
	public List<Map<String, Object>> queryStoreProcedure(final String spName, final Vector<Object> inVals,
			final LinkedHashMap<Integer, Object> outputs) {
		return execute(new CallableStatementCreator() {
			@Override
			public CallableStatement createCallableStatement(Connection conn) throws SQLException {
				String procName = "";
				procName = spName;
				procName += "(";
				// in values
				for (int i = 0; i < inVals.size(); i++) {
					if (i > 0) {
						procName += ",";
					}
					procName += "?";
				}
				// out values
				for (int i = 0; i < outputs.size(); i++) {
					if (inVals.size() > 0 || i > 0) {
						procName += ",";
					}
					procName += "?";
				}
				procName += ")";
				String storedProc = "{call " + procName + "}";// 调用的sql
				log.info("execStoreProcedure - " + storedProc);
				CallableStatement cs = conn.prepareCall(storedProc);
				setStoredProcedureValues(cs, inVals);
				// output
				registerStoredProcedureOutputs(cs, inVals.size(), outputs);
				return cs;
			}
		}, new CallableStatementCallback<List<Map<String, Object>>>() {
			@Override
			public List<Map<String, Object>> doInCallableStatement(CallableStatement cs)
					throws SQLException, DataAccessException {
				List<Map<String, Object>> list = new Vector<Map<String, Object>>();
				ResultSet rs = cs.executeQuery();
				while (rs != null && rs.next()) {
					ResultSetMetaData rsmd = rs.getMetaData();
					Map<String, Object> row = new LinkedHashMap<String, Object>();
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						String colName = rsmd.getColumnName(i);
						row.put(colName, rs.getObject(colName));
					}
					list.add(row);
				}
				// output
				for (int i = 0; i < outputs.size(); i++) {
					outputs.put(i, cs.getObject(inVals.size() + i + 1));
				}
				return list;
			}
		});
	}

	private void registerStoredProcedureOutputs(CallableStatement cs, int insize,
			LinkedHashMap<Integer, Object> outputs) throws SQLException {
		for (int i = 0; i < outputs.size(); i++) {
			Object tmpVal = outputs.get(i);
			if (tmpVal instanceof Integer) {
				cs.registerOutParameter(insize + i + 1, Types.INTEGER);
			} else if (tmpVal instanceof Double) {
				cs.registerOutParameter(insize + i + 1, Types.DOUBLE);
			} else if (tmpVal instanceof Float) {
				cs.registerOutParameter(insize + i + 1, Types.FLOAT);
			} else if (tmpVal instanceof String) {
				cs.registerOutParameter(insize + i + 1, Types.NVARCHAR);
			} else if (tmpVal instanceof Boolean) {
				cs.registerOutParameter(insize + i + 1, Types.BOOLEAN);
			} else if (tmpVal instanceof Long) {
				cs.registerOutParameter(insize + i + 1, Types.DECIMAL);
			} else if (tmpVal instanceof BigDecimal) {
				cs.registerOutParameter(insize + i + 1, Types.BIGINT);
			} else if (tmpVal instanceof Short) {
				cs.registerOutParameter(insize + i + 1, Types.SMALLINT);
			} else if (tmpVal instanceof Date) {
				cs.registerOutParameter(insize + i + 1, Types.DATE);
			} else if (tmpVal instanceof Byte) {
				cs.registerOutParameter(insize + i + 1, Types.BINARY);
			} else if (tmpVal instanceof byte[]) {
				cs.registerOutParameter(insize + i + 1, Types.ARRAY);
			} else if (tmpVal instanceof Clob) {
				cs.registerOutParameter(insize + i + 1, Types.CLOB);
			} else if (tmpVal instanceof Blob) {
				cs.registerOutParameter(insize + i + 1, Types.BLOB);
			} else {
				cs.registerOutParameter(insize + i + 1, Types.JAVA_OBJECT);
			}
		}
	}

	private void setStoredProcedureValues(CallableStatement cs, Vector<Object> inVals) throws SQLException {
		for (int i = 0; i < inVals.size(); i++) {
			Object tmpVal = inVals.get(i);
			if (tmpVal instanceof Integer) {
				cs.setInt(i + 1, (Integer) tmpVal);
			} else if (tmpVal instanceof Double) {
				cs.setDouble(i + 1, (Double) tmpVal);
			} else if (tmpVal instanceof Float) {
				cs.setFloat(i + 1, (Float) tmpVal);
			} else if (tmpVal instanceof String) {
				cs.setString(i + 1, (String) tmpVal);
			} else if (tmpVal instanceof Boolean) {
				cs.setBoolean(i + 1, (Boolean) tmpVal);
			} else if (tmpVal instanceof Long) {
				cs.setLong(i + 1, (Long) tmpVal);
			} else if (tmpVal instanceof BigDecimal) {
				cs.setBigDecimal(i + 1, (BigDecimal) tmpVal);
			} else if (tmpVal instanceof Short) {
				cs.setShort(i + 1, (Short) tmpVal);
			} else if (tmpVal instanceof Date) {
				cs.setDate(i + 1, new java.sql.Date(((Date) tmpVal).getTime()));
			} else if (tmpVal instanceof Byte) {
				cs.setByte(i + 1, (Byte) tmpVal);
			} else if (tmpVal instanceof byte[]) {
				cs.setBytes(i + 1, (byte[]) tmpVal);
			} else if (tmpVal instanceof Clob) {
				cs.setClob(i + 1, (Clob) tmpVal);
			} else if (tmpVal instanceof Blob) {
				cs.setBlob(i + 1, (Blob) tmpVal);
			} else {
				cs.setObject(i + 1, tmpVal);
			}
		}
	}

}
