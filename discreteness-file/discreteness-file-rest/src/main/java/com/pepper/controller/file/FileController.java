package com.pepper.controller.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.pepper.core.ResultData;
import com.pepper.service.file.FileService;
import com.pepper.service.file.FileUploadService;
import com.pepper.util.FileUtil;

/**
 *
 * @author mrliu
 *
 */
@Controller
@RequestMapping(value = {"/file","/wx/file","/app/file","/front/file"})
public class FileController  {

	@Reference
	private FileService fileService;
	
	@Reference
	private FileUploadService fileUploadService;

	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add(MultipartHttpServletRequest multipartHttpServletRequest, String uploadType) throws IOException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, MultipartFile> files = multipartHttpServletRequest.getFileMap();
		for (MultipartFile file : files.values()) {
			// IE上传文件，得到的文件名是一串路径，要做兼容
			String fileName = FileUtil.getRealFileName(file.getOriginalFilename());
			// 判断文件是否有后缀
			if (fileName.indexOf(".") <= 0) {
				fileName = fileName + ".file";
			}
			String fileId = fileUploadService.addFile(file.getBytes(),fileName);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", fileId);
			map.put("url", fileUploadService.getUrl(fileId));
			list.add(map);

			// layedit上传图片
			if (StringUtils.hasLength(uploadType) && uploadType.equals("layedit")) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("code", 0);
				resultMap.put("msg", "上传成功");
				Map<String, Object> resultMapData = new HashMap<String, Object>();
				resultMapData.put("src", fileUploadService.getUrl(fileId));
				resultMapData.put("title", fileName);
				resultMap.put("data", resultMapData);
				return resultMap;
			}else if(StringUtils.hasLength(uploadType) && uploadType.equals("kindeditor")){ //kindeditor富文本编辑
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("error", 0);
				resultMap.put("url", fileUploadService.getUrl(fileId));
				return resultMap;
			}
		}
		ResultData resultData = new ResultData();
		resultData.setData("list", list);
		return resultData;
	}
}
