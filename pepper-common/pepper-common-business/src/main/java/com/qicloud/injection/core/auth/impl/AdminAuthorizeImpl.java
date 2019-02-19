package com.pepper.business.core.auth.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import com.pepper.api.redis.ListOperationsService;
import com.pepper.api.redis.RedisOperationsService;
import com.pepper.api.redis.SetOperationsService;
import com.pepper.api.redis.ValueOperationsService;
import com.pepper.common.emuns.model.Scope;
import com.pepper.core.GlobalParameter;
import com.pepper.core.constant.RedisConstant;
import com.pepper.business.api.core.auth.AdminAuthorize;

/**
 * pc后台管理鉴权类
 * 
 * @author mrliu
 *
 */
@Service(interfaceClass = AdminAuthorize.class)
public class AdminAuthorizeImpl extends AuthorizeImpl implements AdminAuthorize {

	public static final String _tokenKey = "_pc_admin_token_user_id";


	@Override
	public String getTokenKey() {
		return _tokenKey;
	}

	@Override
	public Long getSessionTimeOut() {
		return 60L * 30L;
		// return 30L;
	}

	@Override
	public String getAuthorizeCookieName() {
		return GlobalParameter.ADMIN_LOGIN_TOKEN;
	}

	@Override
	public Scope getScope() {
		return Scope.CONSOLE;
	}

	@Override
	public Map<String, Object> getCurrentUser(String token) {
		String userId = getUserId(token);
		Map<String, Object> user = jdbcTemplate.queryForMap(" select * from t_admin_user where id =? ", userId);
		return user;
	}
	



}
