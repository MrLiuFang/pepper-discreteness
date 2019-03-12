package com.pepper.configuration.verification.code;

import java.util.Properties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

/**
 * 
 * @author mrliu
 *
 */
@Configuration
@ConditionalOnClass(value = { DefaultKaptcha.class })
@ConditionalOnProperty(name = "enabled.kaptcha", havingValue = "true")
@EnableConfigurationProperties(KaptchaProperties.class)
public class KaptchaConfiguration {
	
	private KaptchaProperties kaptchaProperties;

	public KaptchaConfiguration(KaptchaProperties kaptchaProperties) {
		super();
		this.kaptchaProperties = kaptchaProperties;
	}

	@Bean
	public DefaultKaptcha defaultKaptcha() {
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		Properties properties = new Properties();
		// 图片边框
		properties.setProperty("kaptcha.border", kaptchaProperties.getBorder());
		// 边框颜色
		properties.setProperty("kaptcha.border.color", kaptchaProperties.getBorderColor());
		// 字体颜色
		properties.setProperty("kaptcha.textproducer.font.color", kaptchaProperties.getTextproducerFontColor());
		// 图片宽
		properties.setProperty("kaptcha.image.width", kaptchaProperties.getImageWidth());
		// 图片高
		properties.setProperty("kaptcha.image.height", kaptchaProperties.getImageHeight());
		// 字体大小
		properties.setProperty("kaptcha.textproducer.font.size", kaptchaProperties.getTextproducerFontSize());
		// session key
		properties.setProperty("kaptcha.session.key", kaptchaProperties.getSessionKey());
		// 验证码长度
		properties.setProperty("kaptcha.textproducer.char.length", kaptchaProperties.getTextproducerCharLength());
		// 字体
		properties.setProperty("kaptcha.textproducer.font.names", kaptchaProperties.getTextproducerFontNames());
		Config config = new Config(properties);
		defaultKaptcha.setConfig(config);
		return defaultKaptcha;
	}

}
