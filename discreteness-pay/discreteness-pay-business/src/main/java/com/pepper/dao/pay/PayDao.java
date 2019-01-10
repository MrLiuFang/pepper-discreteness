package com.pepper.dao.pay;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.pepper.core.base.BaseDao;
import com.pepper.model.pay.Pay;

/**
 * 
 * @author mrliu
 *
 */
public interface PayDao extends BaseDao<Pay> {

	/**
	 * 查询相关支付信息（appID，key,mchID……）
	 * @param code
	 * @return
	 */
	@Query(value=" select value from t_parameter where code =? ",nativeQuery=true)
	public String getPayInfo(String code);
	
	@Modifying
	@Query(value=" update t_pay set is_pay =? , pay_id =? where id =?  ",nativeQuery=true)
	public Integer updatePayStatus(Integer isPay,String payId ,String id);
	
	/**
	 * 
	 * @param orderId
	 * @return
	 */
	@Query(" from Pay  where orderId = ?1 ")
	public Pay getPayBuyOrderId(String orderId);
}
