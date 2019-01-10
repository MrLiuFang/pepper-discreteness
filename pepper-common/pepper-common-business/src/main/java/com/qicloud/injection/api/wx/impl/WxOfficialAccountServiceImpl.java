package com.pepper.business.api.wx.impl;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.jfinal.weixin.sdk.api.AccessToken;
import com.jfinal.weixin.sdk.api.AccessTokenApi;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.kit.ParaMap;
import com.jfinal.weixin.sdk.utils.HttpUtils;
import com.pepper.api.redis.RedisOperationsService;
import com.pepper.api.redis.ValueOperationsService;
import com.pepper.api.wx.WxOfficialAccountService;
import com.pepper.core.exception.BusinessException;
import com.pepper.business.disributed.lock.DistributedReentrantLock;
import com.pepper.mapper.JsonMapper;
import com.pepper.model.wx.WxUserInfo;
import com.pepper.utils.Util;

@Service(interfaceClass = WxOfficialAccountService.class)
public class WxOfficialAccountServiceImpl implements WxOfficialAccountService {

	private final static Logger LOGGER = LoggerFactory.getLogger(WxOfficialAccountServiceImpl.class);

	private final static String WEIXIN_ACCESS_TOKEN = "weixin_access_token";
	private final static String WEIXIN_JSAPI_TICKET = "weixin_jsapi_ticket";
	private final static String DISTRIBUTED_LOCK_NAME = "weixin_access_token_lock";
	private final static String DISTRIBUTED_TICKET_LOCK_NAME = "weixin_jsapi_ticket_lock";
	private static final String WX_USER_INFO_REQ_URL = "https://api.weixin.qq.com/cgi-bin/user/info";
	private static final String WX_TICKET_REQ_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
	private final static int WEIXIN_ACCESS_TOKEN_TIME_OUT = 6900; // 1小时55分
	private final static int WEIXIN_TICKET_TIME_OUT = 6900; // 1小时55分
	@Autowired
	private RegistryConfig registryConfig;

	@Reference
	private ValueOperationsService<String, String> valueOperationsService;
	@Reference
	private RedisOperationsService<String, String> redisOperationsService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pepper.api.basement.wx.WxOfficialAccountService#getAccessTokenStr(java.
	 * lang.String, java.lang.String)
	 */
	@Override
	public String getAccessTokenStr(String appId, String appSecret) {
		String accessTokenStr = valueOperationsService.get(WEIXIN_ACCESS_TOKEN + ":" + appId);
		if (accessTokenStr == null) {
			return readAndCacheAccessToken(appId, appSecret);
		}
		return accessTokenStr;
	}

	@Override
	public boolean isSubscribe(String appId, String appSecret, String openid) {
		boolean isSubscribe = true;
		String accessToken = getAccessTokenStr(appId, appSecret);
		Map<String, String> queryParas = ParaMap.create("access_token", accessToken).put("openid", openid).getData();
		String json = HttpUtils.get(WX_USER_INFO_REQ_URL, queryParas);
		WxUserInfo wxUserInfo = null;
		try {
			wxUserInfo = WxUserInfo.parse(json);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage(), e);
		}
		if (Util.isEmpty(wxUserInfo) || !wxUserInfo.isSubscribe()) {
			isSubscribe = false;
		}
		return isSubscribe;
	}
	
	@Override
	public String getTicket(String appId, String appSecret) {
		String jsapiTicket  = valueOperationsService.get(WEIXIN_JSAPI_TICKET + ":" + appId);
		if (jsapiTicket == null) {
			return readAndCacheJsApiTicket(appId, appSecret);
		}
		return jsapiTicket;
	}

	protected String readAndCacheJsApiTicket(String appId, String appSecret) {
		String accessToken = getAccessTokenStr(appId, appSecret);
		DistributedReentrantLock distributedLock = DistributedReentrantLock.create(getZkAddress(), DISTRIBUTED_TICKET_LOCK_NAME);
		try {
			distributedLock.lock();
			Map<String, String> queryParas = ParaMap.create("access_token", accessToken).put("type", "jsapi").getData();
			String json = HttpUtils.get(WX_TICKET_REQ_URL, queryParas);
			JsonMapper mapper = JsonMapper.nonDefaultMapper();
			try {
				String ticket = (String) mapper.jsonToMap(json).get("ticket");
				valueOperationsService.set(WEIXIN_JSAPI_TICKET + ":" + appId, ticket);
				redisOperationsService.expire(WEIXIN_JSAPI_TICKET + ":" + appId, WEIXIN_TICKET_TIME_OUT, TimeUnit.SECONDS);// 设置有效期
				return ticket;
			} catch (BusinessException e) {
				LOGGER.debug(e.getMessage(), e);
			}
		} finally {
			distributedLock.unlock(); // 释放锁
		}
		return null;
	}

	/**
	 * 获取微信 access_token 并缓存到redis
	 * 
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	protected String readAndCacheAccessToken(String appId, String appSecret) {
		DistributedReentrantLock distributedLock = DistributedReentrantLock.create(getZkAddress(),
				DISTRIBUTED_LOCK_NAME);
		AccessToken accessToken = null;
		try {
			distributedLock.lock(); // 加锁
			String accessTokenStr = valueOperationsService.get(WEIXIN_ACCESS_TOKEN + ":" + appId);
			if (accessTokenStr == null) {
				ApiConfig ac = new ApiConfig();
				ac.setAppId(appId);
				ac.setAppSecret(appSecret);
				accessToken = AccessTokenApi.refreshAccessToken(ac);
				valueOperationsService.set(WEIXIN_ACCESS_TOKEN + ":" + appId, accessToken.getAccessToken());
				redisOperationsService.expire(WEIXIN_ACCESS_TOKEN + ":" + appId, WEIXIN_ACCESS_TOKEN_TIME_OUT,
						TimeUnit.SECONDS);// 设置有效期
			}
		} finally {
			distributedLock.unlock(); // 释放锁
		}
		return accessToken.getAccessToken();
	}

	/**
	 * 获取 Zookeeper 集群地址
	 * 
	 * @return
	 */
	protected String getZkAddress() {
		return registryConfig.getAddress().replaceAll("zookeeper://", "");
	}

}
