package com.pepper.model.pay;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.pepper.common.emuns.PayType;
import com.pepper.common.emuns.YesOrNo;
import com.pepper.core.base.BaseModel;

/**
 * 订单
 * @author mrliu
 *
 */
@Entity()
@Table(name = "t_pay")
@DynamicUpdate(true)
public class Pay extends BaseModel {

	/**
	 *
	 */
	private static final long serialVersionUID = 8352092045583807948L;

	/**
	 * 支付ID，对应微信支付transaction_id和支付宝的***
	 */
	@Column(name = "pay_id")
	private String payId;

	/**
	 * 订单ID
	 */
	@Column(name = "order_id")
	private String orderId;

	/**
	 * 订单应付金额
	 */
	@Column(name = "amount_payable")
	private Integer amountPayable;

	/**
	 * 实付金额，支付宝/微信返回的实付金额
	 */
	@Column(name = "pay_amount")
	private Integer payAmount;

	/**
	 * 是否支付
	 */
	@Column(name = "is_pay")
	private YesOrNo isPay;

	/**
	 * 支付方式
	 */
	@Column(name = "pay_type")
	private PayType payType;



	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getAmountPayable() {
		return amountPayable;
	}

	public void setAmountPayable(Integer amountPayable) {
		this.amountPayable = amountPayable;
	}

	public Integer getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Integer payAmount) {
		this.payAmount = payAmount;
	}

	public YesOrNo getIsPay() {
		return isPay;
	}

	public void setIsPay(YesOrNo isPay) {
		this.isPay = isPay;
	}

	public PayType getPayType() {
		return payType;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}
	
}
