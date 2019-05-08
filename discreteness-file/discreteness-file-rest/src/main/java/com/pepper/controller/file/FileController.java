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
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.pepper.core.ResultData;
import com.pepper.service.file.FileService;
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

	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add(StandardMultipartHttpServletRequest multipartHttpServletRequest, String uploadType) throws IOException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, MultipartFile> files = multipartHttpServletRequest.getFileMap();
		for (String fileName : files.keySet()) {
			MultipartFile file = files.get(fileName);
			// 判断文件是否有后缀
			if (fileName.indexOf(".") <= 0) {
				fileName = fileName + ".file";
			}
			String fileId = fileService.addFile(file.getBytes(),fileName);
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
				resultMapData.put("title", fileName);
				resultMap.put("data", resultMapData);
				return resultMap;
			}else if(StringUtils.hasLength(uploadType) && uploadType.equals("kindeditor")){ //kindeditor富文本编辑
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("error", 0);
				resultMap.put("url", fileService.getUrl(fileId));
				return resultMap;
			}
		}
		ResultData resultData = new ResultData();
		resultData.setData("list", list);
		return resultData;
	}
}
