package com.pepper.configuration.verification.code;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * kaptcha.border 图片边框，合法值：yes , no yes kaptcha.border.color 边框颜色，合法值： r,g,b
 * (and optional alpha) 或者 white,black,blue. black kaptcha.border.thickness
 * 边框厚度，合法值：>0 1 kaptcha.image.width 图片宽 200 kaptcha.image.height 图片高 50
 * kaptcha.producer.impl 图片实现类 com.google.code.kaptcha.impl.DefaultKaptcha
 * kaptcha.textproducer.impl 文本实现类
 * com.google.code.kaptcha.text.impl.DefaultTextCreator
 * kaptcha.textproducer.char.string 文本集合，验证码值从此集合中获取 abcde2345678gfynmnpwx
 * kaptcha.textproducer.char.length 验证码长度 5 kaptcha.textproducer.font.names 字体
 * Arial, Courier kaptcha.textproducer.font.size 字体大小 40px.
 * kaptcha.textproducer.font.color 字体颜色，合法值： r,g,b 或者 white,black,blue. black
 * kaptcha.textproducer.char.space 文字间隔 2 kaptcha.noise.impl 干扰实现类
 * com.google.code.kaptcha.impl.DefaultNoise kaptcha.noise.color 干扰颜色，合法值： r,g,b
 * 或者 white,black,blue. black kaptcha.obscurificator.impl 图片样式：
 * 水纹com.google.code.kaptcha.impl.WaterRipple
 * 鱼眼com.google.code.kaptcha.impl.FishEyeGimpy
 * 阴影com.google.code.kaptcha.impl.ShadowGimpy
 * com.google.code.kaptcha.impl.WaterRipple kaptcha.background.impl 背景实现类
 * com.google.code.kaptcha.impl.DefaultBackground kaptcha.background.clear.from
 * 背景颜色渐变，开始颜色 light grey kaptcha.background.clear.to 背景颜色渐变，结束颜色 white
 * kaptcha.word.impl 文字渲染器 com.google.code.kaptcha.text.impl.DefaultWordRenderer
 * kaptcha.session.key session key KAPTCHA_SESSION_KEY kaptcha.session.date
 * session date KAPTCHA_SESSION_DATE
 * 
 * @author mrliu
 *
 */
@ConfigurationProperties(prefix = "kaptcha", ignoreUnknownFields = true)
public class KaptchaProperties {

	private String border;
	private String borderColor;
	private String textproducerFontColor;
	private String imageWidth;
	private String imageHeight;
	private String textproducerFontSize;
	private String sessionKey;
	private String textproducerCharLength;
	private String textproducerFontNames;

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	public String getTextproducerFontColor() {
		return textproducerFontColor;
	}

	public void setTextproducerFontColor(String textproducerFontColor) {
		this.textproducerFontColor = textproducerFontColor;
	}

	public String getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(String imageWidth) {
		this.imageWidth = imageWidth;
	}

	public String getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(String imageHeight) {
		this.imageHeight = imageHeight;
	}

	public String getTextproducerFontSize() {
		return textproducerFontSize;
	}

	public void setTextproducerFontSize(String textproducerFontSize) {
		this.textproducerFontSize = textproducerFontSize;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getTextproducerCharLength() {
		return textproducerCharLength;
	}

	public void setTextproducerCharLength(String textproducerCharLength) {
		this.textproducerCharLength = textproducerCharLength;
	}

	public String getTextproducerFontNames() {
		return textproducerFontNames;
	}

	public void setTextproducerFontNames(String textproducerFontNames) {
		this.textproducerFontNames = textproducerFontNames;
	}

}
