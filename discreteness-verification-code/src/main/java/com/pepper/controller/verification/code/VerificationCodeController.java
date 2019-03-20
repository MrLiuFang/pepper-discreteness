package com.pepper.controller.verification.code;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.pepper.core.ResultData;
import com.pepper.core.ResultEnum.Code;
import com.pepper.core.base.BaseController;
import com.pepper.core.base.impl.BaseControllerImpl;
import com.pepper.core.constant.GlobalConstant;
import com.pepper.service.redis.string.serializer.ValueOperationsService;
import com.pepper.service.verification.code.VerificationCodeService;

/**
 * 
 * @author mrliu
 *
 */
@Controller
@RequestMapping(value = "/verification")
@DependsOn(value={"defaultKaptcha"})
public class VerificationCodeController extends BaseControllerImpl implements BaseController {
	
	

	@Resource
	private DefaultKaptcha defaultKaptcha;
	
	@Reference
	private ValueOperationsService valueOperationsService;
	
	@Autowired
	private VerificationCodeService verificationCodeService;

	@RequestMapping("/code")
	public void verificationCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		byte[] captchaChallengeAsJpeg = null;
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();

		String code = defaultKaptcha.createText();
		saveVerificationCode(code);
		// 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
		BufferedImage challenge = defaultKaptcha.createImage(code);
		ImageIO.write(challenge, "jpg", jpegOutputStream);

		// 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
		captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		ServletOutputStream responseOutputStream = response.getOutputStream();
		responseOutputStream.write(captchaChallengeAsJpeg);
		responseOutputStream.flush();
		responseOutputStream.close();
	}
	
	/**
	 * 
	 * @param code
	 */
	private void saveVerificationCode(String code){
		String uuid = UUID.randomUUID().toString();
		Cookie cookie =new Cookie(GlobalConstant.VERIFICATION_CODE_COOKIE, uuid);
		cookie.setMaxAge(-1);
		cookie.setPath("/");
		response.addCookie(cookie);
		valueOperationsService.set(uuid, code, 10, TimeUnit.MINUTES);
	}
	
	@RequestMapping("/code/validate")
	public ResultData validateVerificationCode(@NotBlank(message="请输入验证码") String code){
		ResultData resultData = new ResultData();
		if(!verificationCodeService.validateVerificationCode(code)){
			resultData.setMessage("验证码错误");
			resultData.setStatus(Code.FAIL.getKey());
		}else{
			resultData.setMessage("验证码校验正确");
		}
		return resultData;
	}
	
	
}
