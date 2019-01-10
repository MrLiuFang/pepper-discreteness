package com.pepper.service.sms.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mr.Liu
 *
 */
@Component
public class GuangZhouSmsContentSender implements SmsContentSender ,ApplicationListener<ContextRefreshedEvent>{

	private static Logger logger = LoggerFactory.getLogger(GuangZhouSmsContentSender.class);
	
	@Autowired
	private Environment env;

	private Executor executor;
	//	private String username = "qiyunkj";
	//	private String passwd = "qiyunkj26";

	private String username = "";
	private String passwd = "";




	public void setExecutor(final Executor executor) {
		this.executor = executor;
	}

	@Override
	public void send(String content, String... mobilePhones) throws ClientProtocolException, IOException {
		sendSMS(content,  mobilePhones);
	}

	private void sendSMS(final String content,
			final String... mobilePhones) throws ClientProtocolException, IOException {

		CloseableHttpClient client = HttpClients.createDefault();
		//配置超时时间
		RequestConfig requestConfig = RequestConfig.custom().
				setConnectTimeout(1000).setConnectionRequestTimeout(1000)
				.setSocketTimeout(1000).setRedirectsEnabled(true).build();

		HttpPost post = new HttpPost("http://www.qybor.com:8500/shortMessage");
		post.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
		post.setConfig(requestConfig);


		// 如要添加多个接收者，号码之间用英文逗号隔开，上限是200。
		StringBuffer sb = new StringBuffer();
		for (int index = 0; index < mobilePhones.length; index++) {
			sb.append(mobilePhones[index]);
			if (index != mobilePhones.length - 1) {
				sb.append(",");
			}
		}

		//装配post请求参数
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("username", username));
		list.add(new BasicNameValuePair("passwd", passwd));
		list.add(new BasicNameValuePair("phone", sb.toString()));
		list.add(new BasicNameValuePair("msg", content));
		list.add(new BasicNameValuePair("needstatus", "true"));
		list.add(new BasicNameValuePair("port", ""));
		list.add(new BasicNameValuePair("sendtime", ""));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"UTF-8");
		post.setEntity(entity);
		CloseableHttpResponse httpResponse = client.execute(post);
		post.releaseConnection();
		InputStream is=httpResponse.getEntity().getContent();
		is.close();
		httpResponse.close();
		logger.debug(IOUtils.toString(is, "UTF-8"));
	}

	@Override
	public void send(String content, String[] mobilePhones, SmsSendCallback callback) {
		if (this.executor == null) {
			executor = Executors.newFixedThreadPool(10);// 默认10个线程
		}
		this.executor.execute(new SendCommand(content, mobilePhones, callback));
	}

	private class SendCommand implements Runnable {
		private String content;
		private String[] mobilePhones;
		@SuppressWarnings("unused")
		private SmsSendCallback callback;

		public SendCommand(final String content,  final String[] mobilePhones,
				final SmsSendCallback callback) {
			this.content = content;
			this.mobilePhones = mobilePhones;
			this.callback = callback;
		}

		@Override
		public void run() {
			try {
				sendSMS(this.content,  this.mobilePhones);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		this.username = env.getProperty("guangzhousms.username");
		this.passwd = env.getProperty("guangzhousms.passwd");
	}

	public static void main(String[] args) throws ClientProtocolException, IOException {
		GuangZhouSmsContentSender guangZhouSmsContentSender = new GuangZhouSmsContentSender();
		guangZhouSmsContentSender.send("测试【旗云科技】", "15916286958");
	}

}
