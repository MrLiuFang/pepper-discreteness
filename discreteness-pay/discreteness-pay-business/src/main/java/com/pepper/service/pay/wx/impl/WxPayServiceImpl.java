package com.pepper.service.pay.wx.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.util.StringUtils;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.pepper.common.emuns.YesOrNo;
import com.pepper.core.base.impl.BaseServiceImpl;
import com.pepper.dao.pay.PayDao;
import com.pepper.model.pay.Pay;
import com.pepper.service.pay.PayCallback;
import com.pepper.service.pay.wx.WxPayService;
import com.pepper.util.Md5Util;

/**
 * 
 * @author mrliu
 *
 */
@Service(interfaceClass = WxPayService.class)
public class WxPayServiceImpl extends BaseServiceImpl<Pay> implements WxPayService {
	
	

	@Resource
	private Config config;
	
	@Reference(version="*")
	private PayCallback payCallback;

	@Resource
	private PayDao payDao;
 

	@Override
	public Map<String, String> pay(String ip, String openId, String orderName, String orderId, Integer amountPayable,
			byte[] certData) {
		if (!savePayInfo(orderId, amountPayable)) {
			return null;
		}
		config.setCertData(certData);
		WXPay wxpay = new WXPay(config);
		Map<String, String> data = new HashMap<String, String>();
		data.put("body", orderName);
		data.put("out_trade_no", orderId);
		data.put("device_info", "");
		data.put("fee_type", "CNY");
		data.put("total_fee", amountPayable.toString());
		data.put("spbill_create_ip", ip);
		data.put("notify_url", config.getNotifyUrl());
		data.put("trade_type", "JSAPI");
		data.put("openid", openId);

		try {
			Map<String, String> resp = wxpay.unifiedOrder(data);
			Map<String, String> returnMap = new TreeMap<String, String>();
			returnMap.put("appId",resp.get("appid"));
			returnMap.put("timeStamp",System.currentTimeMillis() / 1000 + "");
			returnMap.put("nonceStr",resp.get("nonce_str"));
			returnMap.put("package","prepay_id=" + resp.get("prepay_id"));
			returnMap.put("signType","MD5");
			
			StringBuffer str = new StringBuffer();
			for (Map.Entry<String, String> entry : returnMap.entrySet()) {
				if (!StringUtils.hasText(entry.getValue())) {
					continue;
				}
				if (!StringUtils.hasText(str.toString())) {
					str.append(entry.getKey());
					str.append("=");
					str.append(entry.getValue());
				} else {
					str.append("&");
					str.append(entry.getKey());
					str.append("=");
					str.append(entry.getValue());
				}
			}
			str.append("&key=");
			str.append(config.getKey());
			String paySign = Md5Util.encodeByMD5(str.toString()).toUpperCase();
			returnMap.put("paySign", paySign);
			returnMap.put("signType", "MD5");
			
			return returnMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 保存支付信息
	 * 
	 * @param orderId
	 * @param amountPayable
	 * @return
	 */
	private Boolean savePayInfo(String orderId, Integer amountPayable) {
		Pay pay = new Pay();
		pay.setAmountPayable(amountPayable);
		pay.setOrderId(orderId);
		pay.setPayType(1);
		pay.setIsPay(YesOrNo.NO.getKey());
		pay = payDao.save(pay);
		if (StringUtils.hasText(pay.getId())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean payNotify(String notifyData) {
		try {
			WXPay wxpay = new WXPay(config);
			Map<String, String> notifyMap = WXPayUtil.xmlToMap(notifyData);
			// 转换成map
			if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
				String transaction_id = notifyMap.get("transaction_id");
				String out_trade_no	 = notifyMap.get("out_trade_no");
				String cash_fee = notifyMap.get("cash_fee");
				Pay pay = payDao.getPayBuyOrderId(out_trade_no);
				pay.setIsPay(YesOrNo.YES.getKey());
				pay.setPayId(transaction_id);
				payDao.save(pay);
				return payCallback.payCallback(cash_fee, out_trade_no);
			} else {
				// 签名错误，如果数据里没有sign字段，也认为是签名错误
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public byte[] getCertData() {
		String certPath = "/data/apiclient_cert.p12";
		File file = new File(certPath);
		InputStream certStream;
		byte[] certData = null;
		try {
			certStream = new FileInputStream(file);
			certData = new byte[(int) file.length()];
			certStream.read(certData);
			certStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return certData;
	}

	// private byte[] getCertData() {
	// String certPath = "/data/apiclient_cert.p12";
	// File file = new File(certPath);
	// InputStream certStream;
	// byte[] certData = null;
	// try {
	// certStream = new FileInputStream(file);
	// certData = new byte[(int) file.length()];
	// certStream.read(certData);
	// certStream.close();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return certData;
	//
	// }
	//
	// public static void main(String[] args) {
	// WxPayServiceImpl wxPayServiceImpl = new WxPayServiceImpl();
	// Map<String, String> resp = wxPayServiceImpl.pay("10.10.100.124",
	// "oOh1UwrGuY0LCXxIQ3CuOlJf84Yk", "测试订单",
	// "20170215412547", 1, wxPayServiceImpl.getCertData());
	// System.out.println(resp);
	//
	// }

}
