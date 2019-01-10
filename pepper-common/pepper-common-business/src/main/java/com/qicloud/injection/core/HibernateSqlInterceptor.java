package com.pepper.business.core;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.util.StringUtils;

import com.pepper.business.spring.SpringContextUtil;
import com.pepper.business.utils.RedisUtil;
import com.pepper.utils.TraceContextUtil;

/**
 * jpa sql语句拦截 用户saas 多租户处理
 * @author mrliu
 *
 */
public class HibernateSqlInterceptor  implements StatementInspector  {
	
	private static RedisUtil redisUtil;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3810343218120594181L;

	@Override
	public String inspect(String sql) {
//		redisUtil = getRedisUtil();
//		String domain = TraceContextUtil.getDomain();
//		String schema = redisUtil.getValueOperationsService().get(domain);
//		if(StringUtils.hasText(schema)){
//			return "/*!mycat : schema = "+domain+" */"+sql;
//		}
		return sql;
	}
	
	/**
	 * 
	 */
	private static RedisUtil getRedisUtil(){
		synchronized (redisUtil) {
			if(redisUtil == null){
				redisUtil = (RedisUtil) SpringContextUtil.getBean("redisUtil");
			}
		}
		return redisUtil;
	}


}
