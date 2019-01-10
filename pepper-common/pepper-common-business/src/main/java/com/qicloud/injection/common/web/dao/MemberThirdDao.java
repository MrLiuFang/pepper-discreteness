package com.pepper.business.common.web.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;

import com.pepper.core.BaseDao;
import com.pepper.model.member.MemberThird;

/**
 *
 * @author weber
 *
 */
public interface MemberThirdDao extends BaseDao<MemberThird> {

	/**
	 * 根据类型和第三方id来获取记录
	 *
	 * @param thirdType
	 * @param third
	 * @return
	 */
	@Query(" from MemberThird where thirdType=?1 and third=?2 ")
	List<MemberThird> findByTypeAndThird(Integer thirdType, String third);

	/**
	 * 根据类型和第三方id来获取记录
	 *
	 * @param thirdType
	 * @param third
	 * @return
	 */
	@Query(value = " select * from t_member_third where third_type=? and third=? ", nativeQuery = true)
	List<Map<String, Object>> findByTypeAndThirdToMap(Integer thirdType, String third);

	/**
	 * 根据第三方类型，第三方id，和用户id来获取记录
	 *
	 * @param key
	 * @param thirdId
	 * @param memberId
	 * @return
	 */
	@Query(" from MemberThird where thirdType=?1 and third=?2 and memberId=?3 ")
	MemberThird findByTypeAndThirdAndMember(int key, String thirdId, String memberId);


	/**
	 * 根据第三方类型，第三方id，获取记录
	 *
	 * @param thirdType
	 * @param memberId
	 * @return
	 */
	List<MemberThird> findByThirdTypeAndMemberId(Integer thirdType, String memberId);

}
