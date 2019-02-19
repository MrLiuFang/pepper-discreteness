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
import com.pepper.business.api.core.auth.PcAuthorize;

/**
 * pc后台管理鉴权类
 * 
 * @author mrliu
 *
 */
@Service(interfaceClass = PcAuthorize.class)
public class PcAuthorizeImpl extends AuthorizeImpl implements PcAuthorize {

	private final String _tokenKey = "_p_token_user_id";


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
		return GlobalParameter.PC_LOGIN_TOKEN;
	}


	@Override
	public Scope getScope() {
		return Scope.FRONT;
	}

}
