package com.pepper.service.authentication;

/**
 * 
 * @author mrliu
 *
 */
public interface AuthorizeFactory {

	/**
	 * 获取鉴权bean
	 * @param scope
	 * @return
	 */
	public IAuthorize getAuthorize(String scope);
	
	/**
	 * 新增鉴权bean（用于增加自定义鉴权bean）
	 * @param scope
	 * @param authorize
	 */
	public void setAuthorize(String scope, IAuthorize authorize);
}
