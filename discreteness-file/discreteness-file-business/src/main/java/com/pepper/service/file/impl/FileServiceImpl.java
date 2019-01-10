package com.pepper.service.file.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.pepper.service.file.FileService;

/**
 *
 * @author mrliu
 *
 */
@Service(interfaceClass = FileService.class)
public class FileServiceImpl implements FileService {

	@Resource
	private FileHelper fileHelper;


	@Override
	public String getUrl(String fileId) {
		return fileHelper.getUrl(fileId);
	}

	@Override
	public List<Map<String, Object>> renderFiles(String... imgId) {
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		Map<String, Object> temp = null;
		if (imgId.length>0) {
			for (String id : imgId) {
				if(StringUtils.hasText(id)){
					temp = new HashMap<String, Object>();
					temp.put("id", imgId);
					temp.put("url", fileHelper.getUrl(id));
					res.add(temp);
				}
			}
		}
		return res;
	}

	@Override
	public String addUrl(String url) {
		return fileHelper.addUrl(url);
	}

}
