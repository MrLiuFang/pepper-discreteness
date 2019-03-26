package com.pepper.service.file.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.util.StringUtils;

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

	@Override
	public String addFile(byte[] fileByte, String fileName) {
		String returnFileName = null;
		String folder=System.getProperty("java.io.tmpdir");
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		if(fileName.lastIndexOf(".")<=0||!StringUtils.hasText(suffix)){
			fileName = fileName +".file";
		}
		File file = new File(folder+"/"+fileName);
		try {
			// 建立输出字节流
			FileOutputStream fos = new FileOutputStream(file);
			// 用FileOutputStream 的write方法写入字节数组
			fos.write(fileByte);
			fos.close();
			returnFileName = fileHelper.add(file);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (file.exists()) {
				file.delete();
			}
		}
		return returnFileName;
	}

}
