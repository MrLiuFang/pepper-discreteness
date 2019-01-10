package com.pepper.business.common.web.pay.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pepper.api.pay.wx.WxPayService;
import com.pepper.core.GlobalParameter;
import com.pepper.core.ResultData;
import com.pepper.core.ResultEnum;
import com.pepper.business.core.impl.BaseControllerImpl;
import com.pepper.utils.Util;

/**
 * 微信支付
 * 
 * @author mrliu
 *
 */
@Controller
@RequestMapping(value = "/wx")
public class WxPayController extends BaseControllerImpl {

	@Reference
	private WxPayService wxPayService;

	@RequestMapping(value = "/pay")
	@ResponseBody
	public ResultData pay(String orderId, String orderName, Double amountPayable) {
		ResultData resultData = new ResultData();
		String ip = this.request.getRemoteAddr();
		String openId = Util.getCookieValue(request, GlobalParameter.COOKIES_OPENID_KEY);
		byte[] certData = getCertData();
		if (certData == null) {
			resultData.setMessage("支付密钥证书读取失败！");
			resultData.setStatus(ResultEnum.Code.FAIL.getKey());
			return resultData;
		}
		Double d = amountPayable * 100;
		Map<String, String> map = wxPayService.pay(ip, openId, orderName, orderId, d.intValue(),
				certData);

		resultData.setData(map);
		return resultData;
	}

	private byte[] getCertData() {
		String os = System.getProperty("os.name");  
		String certPath="";
		if(os.toLowerCase().startsWith("win")){  
			certPath = "d:\\data\\apiclient_cert.p12";
		}else {
			certPath = "/data/apiclient_cert.p12";
		}
		
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

	@RequestMapping(value = "/pay/notify",produces = { "application/xml;charset=UTF-8" })
	@ResponseBody()
	public String payNotify() throws IOException {
		BufferedReader bufferedReader = this.request.getReader();
		StringBuffer buffer = new StringBuffer();
		String line = " ";  
		while ((line = bufferedReader.readLine()) != null){  
		     buffer.append(line);  
		} 
		String notifyData = buffer.toString();
		System.out.println(notifyData);
		Boolean b = wxPayService.payNotify(notifyData);
		Map<String, String> returnMap = new HashMap<String, String>();
		if (!b) {
			returnMap.put("return_code", "FAIL");
			returnMap.put("return_msg", "OK");
		} else {
			returnMap.put("return_code", "SUCCESS");
			returnMap.put("return_msg", "");
		}
		return toXml(returnMap);
	}

	private String toXml(Map<String, String> params) {
		StringBuilder xml = new StringBuilder();
		xml.append("<xml>");
		for (Entry<String, String> entry : params.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			// 略过空值
			if (!StringUtils.hasText(value)){
				continue;
			}
			xml.append("<").append(key).append(">");
			xml.append(entry.getValue());
			xml.append("</").append(key).append(">");
		}
		xml.append("</xml>");
		return xml.toString();
	}
	
	

}
