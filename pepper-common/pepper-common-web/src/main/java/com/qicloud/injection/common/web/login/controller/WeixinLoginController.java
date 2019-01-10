package com.pepper.business.common.web.login.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.servlet.http.Cookie;

import org.apache.commons.codec.DecoderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jfinal.kit.HashKit;
import com.jfinal.weixin.sdk.api.AccessTokenApi;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.JsTicket;
import com.jfinal.weixin.sdk.api.JsTicketApi.JsApiType;
import com.jfinal.weixin.sdk.api.SnsAccessToken;
import com.jfinal.weixin.sdk.api.SnsApi;
import com.jfinal.weixin.sdk.cache.IAccessTokenCache;
import com.jfinal.weixin.sdk.kit.ParaMap;
import com.jfinal.weixin.sdk.kit.PaymentKit;
import com.jfinal.weixin.sdk.utils.HttpUtils;
import com.jfinal.weixin.sdk.utils.RetryUtils;
import com.pepper.api.common.MemberThirdService;
import com.pepper.api.redis.ValueOperationsService;
import com.pepper.common.emuns.model.ThirdTypeEnum;
import com.pepper.common.web.assist.BaseLoginImpl;
import com.pepper.common.web.assist.SnsAccessTokenApiExt;
import com.pepper.core.BaseController;
import com.pepper.core.GlobalParameter;
import com.pepper.core.MemberCommonService;
import com.pepper.core.ResultData;
import com.pepper.core.constant.ParameterConstant;
import com.pepper.core.exception.AuthorizeException;
import com.pepper.core.exception.BusinessException;
import com.pepper.business.aop.AuthorizeAop;
import com.pepper.business.api.core.auth.WeixinAuthorize;
import com.pepper.model.member.MemberThird;
import com.pepper.utils.EncodeUtil;
import com.pepper.utils.Util;

/**
 * 微信公共鉴权Controller
 *
 * @author weber
 *
 */
@Controller
@RequestMapping(value = "/wx/user/account", method = { RequestMethod.POST })
public class WeixinLoginController extends BaseLoginImpl implements BaseController {

	@Autowired
	private Environment environment;

	private static Logger logger = LoggerFactory.getLogger(WeixinLoginController.class);

	//变异原生DUBBO范型调用，非官方标准版（前提条件需要在部署时LIBS包下面有在这个类。官方标准版的可以没有此类，写法不一样，官方标准版比较复杂）
	@Reference(interfaceName = "com.pepper.api.console.parameter.ParameterService",generic=true)
	private Object parameterService;

	//变异原生DUBBO范型调用，非官方标准版（前提条件需要在部署时LIBS包下面有在这个类。官方标准版的可以没有此类，写法不一样，官方标准版比较复杂）
	@Reference(interfaceName = "com.pepper.api.console.role.RoleService",generic=true)
	private Object roleService;

	@Reference
	private MemberThirdService<MemberThird> memberThirdService;

	@Reference
	private WeixinAuthorize weixinAuthorize;

	@Reference
	private MemberCommonService memberCommonService;

	@Reference
	private ValueOperationsService<String, String> valueOperationsService;

	private static String apiUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

	static IAccessTokenCache accessTokenCache = ApiConfigKit.getAccessTokenCache();

	/**
	 * 登录
	 *
	 * @return
	 * @throws JsonProcessingException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws AuthorizeException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/login")
	@ResponseBody
	public Object login(String account, String pwd)
			throws JsonProcessingException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, AuthorizeException {
		String memberId = memberCommonService.loginMember(account, pwd);
		if (Util.isEmpty(memberId)) {
			throw new AuthorizeException("登录失败!用户名/密码错误!");
		}
		List<String> resourceList = (List<String>) roleService.getClass()
				.getMethod("queryUserAllResources", String.class).invoke(roleService, memberId);
		login(memberId, resourceList, weixinAuthorize);
		String openId = Util.getCookieValue(request, GlobalParameter.COOKIES_OPENID_KEY);
		if (Util.isNotEmpty(openId)) {
			List<MemberThird> memberThirds = memberThirdService.findByTypeAndThird(ThirdTypeEnum.WeiXin.getKey(),
					openId);
			// 不允许同一个openid绑定多个会员，也就是，如果openId之前已经绑定了会员，先解绑之前的会员，再绑定新会员。
			if (Util.isNotEmpty(memberThirds)) {
				memberThirdService.deleteAll(memberThirds);
			}
			MemberThird memberThird = new MemberThird();
			memberThird.setCreateDate(new Date());
			memberThird.setMemberId(memberId);
			memberThird.setThird(openId);
			memberThird.setThirdType(ThirdTypeEnum.WeiXin.getKey());
			memberThirdService.save(memberThird);
		}
		return new ResultData();
	}

	/**
	 * 退出登录
	 *
	 * @return
	 */
	@RequestMapping(value = "/loginOut")
	@Authorize(authorizeResources = false)
	public Object loginOut() {
		String token = this.getCookie(weixinAuthorize.getAuthorizeCookieName());
		logout(token, weixinAuthorize);
		return new ResultData();
	}

	/**
	 * 获取授权地址（基础授权）
	 *
	 * @throws BusinessException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/getAuthorizeUrl")
	@ResponseBody
	public Object getAuthorizeUrl(String pageUri)
			throws BusinessException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, UnsupportedEncodingException {
		String redirectUri = getUrl(pageUri, "loginByWeiXin");
		String appid = (String) parameterService.getClass().getMethod("findValueByCode", String.class)
				.invoke(parameterService, ParameterConstant.weixin_appid);
		String weixinAuthUrl = (String) parameterService.getClass().getMethod("findValueByCode", String.class)
				.invoke(parameterService, ParameterConstant.weixin_auth_url);
		// state参数用appid,用做判断是否微信服务器调用
		return new ResultData().setData("authorizeUrl",
				SnsAccessTokenApiExt.getAuthorizeURL(weixinAuthUrl, appid, redirectUri, appid, true));
	}

	/**
	 * 微信授权回调方法（仅获取openId）。
	 *
	 * @throws DecoderException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loginByWeiXin", method = { RequestMethod.GET })
	public String loginByWeiXin(String pageUri, String code, String state)
			throws DecoderException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, IOException, BusinessException {
		if (Util.isEmpty(pageUri)) {
			pageUri = "/index.html";
		}
		String appid = (String) parameterService.getClass().getMethod("findValueByCode", String.class)
				.invoke(parameterService, ParameterConstant.weixin_appid);
		String openId = getAccessToken(code, state, appid).getOpenid();
		// 判断是否已存在用户
		List<MemberThird> memberThirds = memberThirdService.findByTypeAndThird(ThirdTypeEnum.WeiXin.getKey(), openId);

		if (Util.isNotEmpty(memberThirds)) {
			MemberThird memberThird = memberThirds.get(0);
			List<String> resourceList = (List<String>) roleService.getClass()
					.getMethod("queryUserAllResources", String.class).invoke(roleService, memberThird.getMemberId());
			login(memberThird.getMemberId(), resourceList, weixinAuthorize);
		}
		Cookie cookieToken = new Cookie(GlobalParameter.COOKIES_OPENID_KEY, openId);
		cookieToken.setMaxAge(-1);
		cookieToken.setPath("/");
		response.addCookie(cookieToken);
		request.setAttribute("weixinPage", new String(EncodeUtil.urlDecode(pageUri)));
		return "common/weixin_page";
	}

	/**
	 * 获取授权地址（高级授权）
	 *
	 * @throws BusinessException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/getAuthorizeUrlSuper")
	@ResponseBody
	public Object getAuthorizeUrlSuper(String pageUri)
			throws BusinessException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, UnsupportedEncodingException {
		String redirectUri = getUrl(pageUri, "loginByWeiXinSuper");
		String appid = (String) parameterService.getClass().getMethod("findValueByCode", String.class)
				.invoke(parameterService, ParameterConstant.weixin_appid);
		String weixinAuthUrl = (String) parameterService.getClass().getMethod("findValueByCode", String.class)
				.invoke(parameterService, ParameterConstant.weixin_auth_url);
		// state参数用appid,用做判断是否微信服务器调用
		return new ResultData().setData("authorizeUrl",
				SnsAccessTokenApiExt.getAuthorizeURL(weixinAuthUrl, appid, redirectUri, appid, false));
	}

	/**
	 * 微信授权回调方法（获取openId，并且通过 openid 拿到昵称、性别、所在地）。
	 *
	 * @throws DecoderException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loginByWeiXinSuper", method = { RequestMethod.GET })
	public String loginByWeiXinSuper(String pageUri, String code, String state)
			throws DecoderException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, IOException, BusinessException {
		if (Util.isEmpty(pageUri)) {
			pageUri = "/index.html";
		}
		String appid = (String) parameterService.getClass().getMethod("findValueByCode", String.class)
				.invoke(parameterService, ParameterConstant.weixin_appid);
		SnsAccessToken accessToken = getAccessToken(code, state, appid);
		String openId = accessToken.getOpenid();

		// 判断是否已存在用户（超级授权情况下，一个openid只可能对应一个会员，因为根本没有手动注册会员的过程，用户只能通过自动授权注册会员的方式）
		List<MemberThird> memberThirds = memberThirdService.findByTypeAndThird(ThirdTypeEnum.WeiXin.getKey(), openId);
		MemberThird memberThird = null;
		if (Util.isEmpty(memberThirds)) {
			/**
			 * 自动注册微信用户。示例：
			 * '{"subscribe":1,"openid":"oUlT2vt0KQsbeUEddg4Egn1abiXZuI0","nickname":"霞","sex":1,"language":"zh_CN","city":"g3","province":"SN¬","country":"","headimgurl":"http://wx.qlogo.cn/mmopen/Ghbfb9JxWz9ER5Y1OgduNCo5fTiawEE9cFDEIzuVtQkoQ37hwxLliaK7pibcXSngjHo12r2zib8UvRbWXcicwnA1qteZiaf7M7XGhQu/0","subscribe_time":1500372709,"remark":"","groupid":0,"tagid_list":[]}'
			 */
			ApiResult weixinUserInfo = SnsApi.getUserInfo(accessToken.getAccessToken(), openId);
			logger.info("<<<<<<UserInfo:" + weixinUserInfo.getJson());
			String nickname = weixinUserInfo.get("nickname"); // 昵称
			Integer sex = weixinUserInfo.get("sex"); // 性别
			String headimgurl = weixinUserInfo.get("headimgurl"); // 头像url
			String province = weixinUserInfo.get("province");// 地区
			String country = weixinUserInfo.get("country");
			String language = weixinUserInfo.get("language");
			String subscribe_time = weixinUserInfo.get("subscribe_time");
			String city = weixinUserInfo.get("city");
			String memberId = memberCommonService.registWeixinMember(nickname, sex, headimgurl, province, country, city,
					language, subscribe_time);
			/**
			 * 关联t_member_third表
			 */
			memberThird = new MemberThird();
			memberThird.setCreateDate(new Date());
			memberThird.setMemberId(memberId);
			memberThird.setThird(openId);
			memberThird.setThirdType(ThirdTypeEnum.WeiXin.getKey());
			memberThirdService.save(memberThird);
		} else {
			memberThird = memberThirds.get(0);
		}
		List<String> resourceList = (List<String>) roleService.getClass()
				.getMethod("queryUserAllResources", String.class).invoke(roleService, memberThird.getMemberId());
		login(memberThird.getMemberId(), resourceList, weixinAuthorize);
		Cookie cookieToken = new Cookie(GlobalParameter.COOKIES_OPENID_KEY, openId);
		cookieToken.setMaxAge(-1);
		cookieToken.setPath("/");
		response.addCookie(cookieToken);
		request.setAttribute("weixinPage", new String(EncodeUtil.urlDecode(pageUri)));
		return "common/weixin_page";
	}

	/**
	 * 获取授权地址封装
	 *
	 * @param pageUri
	 * @return
	 * @throws BusinessException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws UnsupportedEncodingException
	 */
	protected String getUrl(String pageUri, String callback)
			throws BusinessException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, UnsupportedEncodingException {
		if (Util.isEmpty(pageUri)) {
			throw new BusinessException("pageUri不能为空");
		}
		String domain = (String) parameterService.getClass().getMethod("findValueByCode", String.class)
				.invoke(parameterService, ParameterConstant.weixin_domain);
		String redirectUri =  domain + "/wx/user/account/" + callback + "?pageUri=" + pageUri;
		try {
			redirectUri = EncodeUtil.urlEncode(redirectUri);
		} catch (Exception e) {
			throw new BusinessException("获取授权地址失败。", e);
		}
		return redirectUri;
	}

	/**
	 * 获取openId封装
	 *
	 * @param code
	 * @param state
	 * @param appid
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws BusinessException
	 */
	protected SnsAccessToken getAccessToken(String code, String state, String appid)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, BusinessException {
		String secret = (String) parameterService.getClass().getMethod("findValueByCode", String.class)
				.invoke(parameterService, "weixin_secret");
		if (!state.equals(appid)) {
			throw new BusinessException("微信服务请求不合法");
		}
		// 能过code取openId
		SnsAccessToken accessToken = SnsAccessTokenApiExt.getSnsAccessToken(appid, secret, code);
		logger.info(String.format(
				"<<<<<<WeixinLoginController.getAccessToken's parameters: code:%s, state:%s, appid:%s, accessToken:%s",
				code, state, appid, accessToken.getJson()));
		if (Util.isEmpty(accessToken.getOpenid())) {
			throw new BusinessException("请求微信服务器获取用户标识失败");
		}
		return accessToken;
	}

	@RequestMapping(value = "/getJsSdkConfig")
	@ResponseBody
	public Object getJsSdkConfig(String url) throws BusinessException, IllegalAccessException, IllegalArgumentException,
	InvocationTargetException, NoSuchMethodException, SecurityException {
		String _wxShareUrl = url;
		String domain = (String) parameterService.getClass().getMethod("findValueByCode", String.class)
				.invoke(parameterService, ParameterConstant.weixin_domain);
		_wxShareUrl =domain + url;
		// if (Util.isNotEmpty(_wxShareUrl)) {
		// _wxShareUrl = _wxShareUrl.substring(_wxShareUrl.lastIndexOf("/")+1);
		// } else {
		// throw new BusinessException("_wxShareUrl不存在！");
		// }
		// logger.info(_wxShareUrl);

		// String jsapi_ticket = valueOperationsService.get("jsapi_ticket");
		// if(!StringUtils.hasText(jsapi_ticket)){

		// if (Util.isNotEmpty(_wxShareUrl)) {
		// _wxShareUrl = _wxShareUrl.split("#")[0];
		// } else {
		// throw new BusinessException("_wxShareUrl不存在！");
		// }
		// String jsapi_ticket = valueOperationsService.get("jsapi_ticket");
		// if(!StringUtils.hasText(jsapi_ticket)){
		JsTicket jsTicket = getTicket(JsApiType.jsapi);
		// valueOperationsService.set("jsapi_ticket", jsTicket.getTicket(), 71990,
		// TimeUnit.SECONDS);
		// }

		// 参数封装
		Map<String, String> packageParams = new HashMap<String, String>();

		packageParams.put("noncestr", System.currentTimeMillis() + "");
		packageParams.put("jsapi_ticket", jsTicket.getTicket());
		packageParams.put("timestamp", System.currentTimeMillis() / 1000 + "");
		packageParams.put("url", _wxShareUrl);
		// 加密获取signature
		String _wxSignString = PaymentKit.packageSign(packageParams, false);
		// signature
		String _wxSignature = HashKit.sha1(_wxSignString);

		Map<String, Object> reMap = new HashMap<String, Object>();
		reMap.put("appid", ApiConfigKit.getAppId());
		reMap.put("timestamp", packageParams.get("timestamp"));
		reMap.put("noncestr", packageParams.get("noncestr"));
		reMap.put("sign", _wxSignature);
		return new ResultData().setData(reMap);
	}

	/**
	 *
	 * http GET请求获得jsapi_ticket（有效期7200秒，开发者必须在自己的服务全局缓存jsapi_ticket）
	 *
	 * @param jsApiType
	 *            jsApi类型
	 * @return JsTicket
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public JsTicket getTicket(JsApiType jsApiType) throws IllegalAccessException, IllegalArgumentException,
	InvocationTargetException, NoSuchMethodException, SecurityException {

		String appId = ApiConfigKit.getAppId();
		String access_token = AccessTokenApi.getAccessTokenStr();
		String key = appId + ':' + jsApiType.name();
		final ParaMap pm = ParaMap.create("access_token", access_token).put("type", jsApiType.name());

		// 2016.07.21修改By L.cm 为了更加方便扩展
		String jsTicketJson = accessTokenCache.get(key);
		JsTicket jsTicket = null;
		if (Util.isNotEmpty(jsTicketJson)) {
			jsTicket = new JsTicket(jsTicketJson);
		}
		if (null == jsTicket || !jsTicket.isAvailable()) {
			// 最多三次请求
			jsTicket = RetryUtils.retryOnException(3, new Callable<JsTicket>() {
				@Override
				public JsTicket call() throws Exception {
					return new JsTicket(HttpUtils.get(apiUrl, pm.getData()));
				}

			});

			if (null != jsApiType) {
				accessTokenCache.set(key, jsTicket.getCacheJson());
			}

		}
		return jsTicket;
	}

}
