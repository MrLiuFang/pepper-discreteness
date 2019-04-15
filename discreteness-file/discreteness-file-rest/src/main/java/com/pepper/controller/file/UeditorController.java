package com.pepper.controller.file;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pepper.service.file.FileService;
import com.pepper.util.FileUtil;
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
@RequestMapping(value = "/ueditor")
public class UeditorController  {

	@Reference
	private FileService fileService;

	@RequestMapping(value = "/upload")
	@ResponseBody
	public Object add(@RequestParam("upfile") MultipartFile file) throws IOException {
		/*
		 * if ("config".equals(action)) { // 这里千万注意 "config.json" 文件前方的目录一定要正确
		 * //render("/assets/admin/ueditor/jsp/config.json"); return
		 * IOUtils.toString(new
		 * ClassPathResource("/META-INF/resources/assets/admin/ueditor/jsp/config.json")
		 * .getInputStream(), Charset.forName("Utf-8")); }
		 */
		
		// IE上传文件，得到的文件名是一串路径，要做兼容
		String fileName = FileUtil.getRealFileName(file.getOriginalFilename());
		String fileId = fileService.addFile(file.getBytes(),fileName);
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
