package com.pepper.common.web.assist;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.kit.StrKit;
import com.jfinal.weixin.sdk.api.SnsAccessTokenApi;
import com.jfinal.weixin.sdk.kit.PaymentKit;
import com.pepper.utils.Util;

/**
 * 扩展SnsAccessTokenApi
 * 
 * @author weber
 *
 */
public class SnsAccessTokenApiExt extends SnsAccessTokenApi {

	/**
	 * 生成Authorize链接
	 * 
	 * @param weixinAuthUrl
	 *            授权中转地址（解决一个微信公众号被多个应用使用的情况）。
	 *            其他参数查看SnsAccessTokenApi.getAuthorizeURL
	 * @param appId
	 *            应用id
	 * @param redirectUri
	 *            回跳地址
	 * @param state
	 *            重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
	 * @param snsapiBase
	 *            snsapi_base（不弹出授权页面，只能拿到用户openid）snsapi_userinfo（弹出授权页面，这个可以通过
	 *            openid 拿到昵称、性别、所在地）
	 * @return url
	 * @see SnsAccessTokenApi.getAuthorizeURL()
	 */
	public static String getAuthorizeURL(String weixinAuthUrl, String appId, String redirectUri, String state,
			boolean snsapiBase) {
		if (Util.isNotEmpty(weixinAuthUrl)) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("appid", appId);
			params.put("response_type", "code");
			params.put("redirect_uri", redirectUri);
			if (snsapiBase) {
				params.put("scope", "snsapi_base");
			} else {
				params.put("scope", "snsapi_userinfo");
			}
			if (StrKit.isBlank(state)) {
				params.put("state", "wx#wechat_redirect");
			} else {
				params.put("state", state.concat("#wechat_redirect"));
			}
			String para = PaymentKit.packageSign(params, false);
			return weixinAuthUrl + "?" + para;
		}
		return SnsAccessTokenApi.getAuthorizeURL(appId, redirectUri, state, snsapiBase);
	}

}
