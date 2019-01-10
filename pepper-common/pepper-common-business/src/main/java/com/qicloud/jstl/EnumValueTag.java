package com.pepper.jstl;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 
 * @author liufang
 *
 */
public class EnumValueTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8518409563613044913L;

	private String enumClass;

	private String value = "";

	public String getEnumClass() {
		return enumClass;
	}

	public void setEnumClass(String enumClass) {
		this.enumClass = enumClass;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int doAfterBody() throws JspException {
		return super.doAfterBody();
	}

	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		ServletRequest request = this.pageContext.getRequest();
		try {
			Class<?> class1 = Class.forName(enumClass);
			Method keyVlueMethod = class1.getMethod("getKeyVlue");
			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>) keyVlueMethod.invoke(class1);
			for (Map.Entry<String, String> entry : map.entrySet()) {
				if (value.equals(entry.getKey())) {
					out.print(entry.getValue());
				}
			}
			out.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.doStartTag();

	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return super.getId();
	}

	@Override
	public Tag getParent() {
		// TODO Auto-generated method stub
		return super.getParent();
	}

	@Override
	public Object getValue(String k) {
		// TODO Auto-generated method stub
		return super.getValue(k);
	}

	@Override
	public Enumeration<String> getValues() {
		// TODO Auto-generated method stub
		return super.getValues();
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		super.release();
	}

	@Override
	public void removeValue(String k) {
		// TODO Auto-generated method stub
		super.removeValue(k);
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		super.setId(id);
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		// TODO Auto-generated method stub
		super.setPageContext(pageContext);
	}

	@Override
	public void setParent(Tag t) {
		// TODO Auto-generated method stub
		super.setParent(t);
	}

	@Override
	public void setValue(String k, Object o) {
		// TODO Auto-generated method stub
		super.setValue(k, o);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

}
