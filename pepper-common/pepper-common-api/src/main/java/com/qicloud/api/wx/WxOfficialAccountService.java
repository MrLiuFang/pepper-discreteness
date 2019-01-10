package com.pepper.api.wx;

/**
 * 微信公众号服务
 * 
 * @author pell
 */
public interface WxOfficialAccountService {

	/**
	 * 获取微信授权标识 access_token
	 * 
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	public String getAccessTokenStr(String appId, String appSecret);
	
	/**
	 * 用户是否订阅该公众号
	 * 
	 * @param appId
	 * @param appSecret
	 * @param openid
	 * @return
	 */
	public boolean isSubscribe(String appId, String appSecret, String openid);
	
	/**
	 * 获取ticket
	 * 
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	public String getTicket(String appId, String appSecret);
}
