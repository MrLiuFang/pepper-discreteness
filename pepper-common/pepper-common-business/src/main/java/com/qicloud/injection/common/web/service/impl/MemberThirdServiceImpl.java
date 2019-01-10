package com.pepper.business.common.web.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.pepper.api.common.MemberThirdService;
import com.pepper.business.common.web.dao.MemberThirdDao;
import com.pepper.business.core.impl.BaseServiceImpl;
import com.pepper.model.member.MemberThird;

/**
 *
 * @author weber
 *
 */
@Service(interfaceClass = MemberThirdService.class)
public class MemberThirdServiceImpl extends BaseServiceImpl<MemberThird> implements MemberThirdService<MemberThird> {

	@Resource
	private MemberThirdDao memberThirdDao;

	@Override
	public List<MemberThird> findByTypeAndThird(Integer thirdType, String third) {
		return memberThirdDao.findByTypeAndThird(thirdType, third);
	}

	@Override
	public MemberThird findByTypeAndThirdAndMember(int key, String thirdId, String memberId) {
		return memberThirdDao.findByTypeAndThirdAndMember(key, thirdId, memberId);
	}

	@Override
	public List<Map<String, Object>> findByTypeAndThirdToMap(Integer thirdType, String third) {
		return memberThirdDao.findByTypeAndThirdToMap(thirdType, third);
	}

	@Override
	public List<MemberThird> findByTypeAndMember(Integer thirdType, String memberId) {
		return memberThirdDao.findByThirdTypeAndMemberId(thirdType, memberId);
	}

}
