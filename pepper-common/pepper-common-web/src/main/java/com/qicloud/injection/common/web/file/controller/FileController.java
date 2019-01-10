package com.pepper.business.common.web.file.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pepper.api.file.FileService;
import com.pepper.core.ResultData;
import com.pepper.business.aop.NotRecordSystemLog;
import com.pepper.business.core.impl.BaseControllerImpl;
import com.pepper.utils.Util;

/**
 *
 * @author mrliu
 *
 */
@Controller
@RequestMapping(value = {"/file","/wx/file","/app/file"})
public class FileController extends BaseControllerImpl {

	@Reference
	private FileService fileService;

	@RequestMapping(value = "/add")
	@NotRecordSystemLog
	@ResponseBody
	public Object add(MultipartHttpServletRequest multipartHttpServletRequest, String uploadType) throws IOException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, MultipartFile> files = multipartHttpServletRequest.getFileMap();
		for (MultipartFile file : files.values()) {
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
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", fileId);
			map.put("url", fileService.getUrl(fileId));
			list.add(map);

			// layedit上传图片
			if (StringUtils.hasLength(uploadType) && uploadType.equals("layedit")) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("code", 0);
				resultMap.put("msg", "上传成功");
				Map<String, Object> resultMapData = new HashMap<String, Object>();
				resultMapData.put("src", fileService.getUrl(fileId));
				resultMapData.put("title", file.getOriginalFilename());
				resultMap.put("data", resultMapData);
				return resultMap;
			}
		}
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type");
		response.setHeader("Access-Control-Allow-Credentials", "true");

		ResultData resultData = new ResultData();
		resultData.setData("list", list);
		return resultData;

	}
}
