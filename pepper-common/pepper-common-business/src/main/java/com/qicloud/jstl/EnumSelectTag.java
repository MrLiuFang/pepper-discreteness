package com.pepper.jstl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.pepper.common.emuns.model.IEnum;
import com.pepper.utils.Util;

/**
 *
 * @author Mr.Liu
 *
 */
public class EnumSelectTag extends TagSupport {

	/**
	 *
	 */
	private static final long serialVersionUID = 8662347067785086506L;

	private String enumClass;

	private String selectedValue = "";

	private String documentId;

	private String documentName;

	private String disabled = "";

	private String required = "";

	private String noDefault = "";

	private String dis = "";

	/**
	 * 其他的属性，比如lay-search
	 */
	private String attr = "";

	/**
	 * 占位符，提示请选择xxx
	 * 
	 * @author guojun
	 */
	private String placeholder = "";

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public String getDis() {
		return dis;
	}

	public void setDis(String dis) {
		this.dis = dis;
	}

	public String getNoDefault() {
		return noDefault;
	}

	public void setNoDefault(String noDefault) {
		this.noDefault = noDefault;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public String getEnumClass() {
		return enumClass;
	}

	public void setEnumClass(String enumClass) {
		this.enumClass = enumClass;
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

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	@Override
	public int doAfterBody() throws JspException {
		return super.doAfterBody();
	}

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		try {
			Class<?> class1 = Class.forName(enumClass);
			List<IEnum> list = initialEnum(class1);

			String requiredStr = required.toLowerCase().equals("true") ? "lay-verify='required'" : "";
			String disAttr = "";
			if (Util.isNotEmpty(dis)) {
				disAttr = " disabled='disabled' ";
			}
			out.print("<select id='" + documentId + "' name='" + documentName + "' " + requiredStr + " lay-filter='"
					+ documentId + "' " + disAttr + " " + attr + " >");

			if (!noDefault.toLowerCase().equals("true")) {
				out.print("<option value='' >" + placeholder + "</option>");
			}
			String _disabled[] = disabled.split(",");
			for (IEnum ienum : list) {
				String key = ienum.getKey().toString();
				String value = ienum.getName();
				String selected = "";
				if (selectedValue.equals(key)) {
					selected = "selected='selected'";
				}
				String ___disabled = "";
				for (String str : _disabled) {
					if ((key + "").equals(str)) {
						___disabled = "disabled";
					}
				}
				out.print("<option " + ___disabled + " value='" + key + "' " + selected + " >" + value + "</option>");
			}
			out.print("</select>");
			out.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.doStartTag();

	}

	private List<IEnum> initialEnum(Class<?> cls) throws Exception {
		List<IEnum> list = new ArrayList<>();
		Method method = cls.getMethod("values");
		IEnum inter[] = (IEnum[]) method.invoke(null);
		for (IEnum ienum : inter) {
			list.add(ienum);
		}
		return list;
	}

}
