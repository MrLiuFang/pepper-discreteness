package com.pepper.business.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.pepper.core.constant.ParameterConstant;

/**
 * saas 多租户会存在致命bug，因此弃用，不在系统启动的时候读取数据库，并设置值
 * @author mrliu
 *
 */
//@Component
//@Lazy(value=true)
@Deprecated
public class InitApiConfig implements ApplicationListener<ContextRefreshedEvent> {
	
//	@Reference(interfaceName = "com.pepper.api.console.parameter.ParameterService",version="*",generic=true)
//	private Object genericService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try{
			String appId = null;
			String appSecret = null;
	//		GenericService parameterService = (GenericService) genericService;
	//		appId = (String) parameterService.$invoke("findValueByCode", new String[]{String.class.getName()} , new Object[]{ParameterConstant.weixin_appid});
	//		appSecret = (String) parameterService.$invoke("findValueByCode", new String[]{String.class.getName()} , new Object[]{ParameterConstant.weixin_secret});
			appId = this.jdbcTemplate.queryForObject(" select value from t_parameter where code = ? ", new Object[]{ParameterConstant.weixin_appid}, String.class);
			appSecret = this.jdbcTemplate.queryForObject(" select value from t_parameter where code = ? ", new Object[]{ParameterConstant.weixin_secret}, String.class);
			ApiConfig apiConfig = new ApiConfig();
			apiConfig.setAppId(appId==null?"":appId);
			apiConfig.setAppSecret(appSecret==null?"":appSecret);
	//		ApiConfigKit.setThreadLocalAppId(appId);
			ApiConfigKit.putApiConfig(apiConfig);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

}
