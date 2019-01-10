package com.pepper.jstl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.util.StringUtils;

/**
 * 
 * @author mrliu
 *
 */
public class EnumSwitchTag extends TagSupport {

	private String enumClass;

	private String checked = "";

	private String documentId;

	private String documentName;

	public String getEnumClass() {
		return enumClass;
	}

	public void setEnumClass(String enumClass) {
		this.enumClass = enumClass;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1671602530430426528L;

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		ServletRequest request = this.pageContext.getRequest();
		Object _checked = "";
		try {
			Class<?> class1 = Class.forName(enumClass);
			Method keyVlueMethod = class1.getMethod("getKeyVlue");
			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>) keyVlueMethod.invoke(class1);
			String layText = "";
			for (Map.Entry<String, String> entry : map.entrySet()) {
				if (StringUtils.hasText(layText)) {
					layText += "|" + entry.getValue();
				} else {
					layText = entry.getValue();
				}
				if (checked.equals(entry.getKey())) {
					_checked = entry.getValue();
				}
			}
			out.print("<input type=\"checkbox\" name=\"" + documentName + "\" id=\"" + documentId
					+ "\" lay-skin=\"switch\"  lay-text=\"" + layText + "\"  checked=\"" + _checked + "\"/>");
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.doStartTag();

	}

}
