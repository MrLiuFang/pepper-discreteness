package com.pepper.business.common.web.file.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pepper.api.file.FileService;
import com.pepper.core.BaseController;
import com.pepper.business.aop.NotRecordSystemLog;
import com.pepper.business.core.impl.BaseControllerImpl;
import com.pepper.utils.Util;

/**
 * 
 * <p>
 * Title: UeditorController
 * </p>
 * <p>
 * Description: 集成ueditor富文本控件
 * </p>
 * 
 * @author dorry
 * @date 2018年7月23日
 */
@Controller
@RequestMapping(value = "admin/ueditor", method = { RequestMethod.GET, RequestMethod.POST })
public class UeditorController extends BaseControllerImpl implements BaseController {

	@Reference
	private FileService fileService;

	@RequestMapping(value = "/upload")
	@ResponseBody
	@NotRecordSystemLog
	public Object add(@RequestParam("upfile") MultipartFile file, String action) throws IOException {
		/*
		 * if ("config".equals(action)) { // 这里千万注意 "config.json" 文件前方的目录一定要正确
		 * //render("/assets/admin/ueditor/jsp/config.json"); return
		 * IOUtils.toString(new
		 * ClassPathResource("/META-INF/resources/assets/admin/ueditor/jsp/config.json")
		 * .getInputStream(), Charset.forName("Utf-8")); }
		 */
		String folder = System.getProperty("java.io.tmpdir");
		// IE上传文件，得到的文件名是一串路径，要做兼容
		String fileName = Util.getRealFileName(file.getOriginalFilename());
		// 判断文件是否有后缀
		if (fileName.indexOf(".") <= 0) {
			fileName = fileName + ".file";
		}
		File tempFile = new File(folder + File.separator + fileName);
		file.transferTo(tempFile);
		String fileId = fileService.addFile(tempFile);
		String url = fileService.getUrl(fileId);
		String[] typeArr = file.getContentType().split("/");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", fileId);
		map.put("url", url);
		map.put("src", fileService.getUrl(fileId));
		map.put("original", file.getOriginalFilename());
		map.put("state", "SUCCESS");
		map.put("title", fileName);
		map.put("type", "." + typeArr[1]);
		map.put("size", file.getSize());
		return map;
	}
}
