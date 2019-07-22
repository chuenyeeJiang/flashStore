package com.chuenyee.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.chuenyee.service.HttpBuffer;
import com.chuenyee.tool.MyHttphandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.chuenyee.service.api.HeadPortrait;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Controller
public class HeadPortraitController {
	Logger logger = LoggerFactory.getLogger(HeadPortraitController.class);

	@Autowired
    HeadPortrait headPortrait;

	// 上传头像
	@RequestMapping(value="/Controller_uploadHeadPortrait.do")
	public Map<String, Object> uploadHeadPortrait(@RequestParam MultipartFile local_file, @RequestParam Map<String,Object> request_map, Map<String,Object> map) throws IOException {
		// return restTemplate.getForObject("", responseType)
		logger.info("web HeadPortraitController=====================uploadHeadPortrait");
		logger.debug("local_file："+local_file);


		byte[] local_fileBytes = local_file.getBytes();
		logger.debug("byte:"+local_fileBytes);
		String str_bytefile = base64Encode(local_fileBytes);
		logger.debug("base64:"+str_bytefile);
		request_map.put("str_bytefile",str_bytefile);


		/*
		 * 请求服务
		 */

/*		try {
			HttpBuffer.Wirter(local_file.getBytes(),myHttphandle.getFileServerURL());
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		//Map<String, Object> response_map = headPortrait.upload(request_map);
		headPortrait.upload(request_map);

/*		*//*
		 * 响应信息
		 *//*
		//  return	(String) response_map.get("view");
		if(response_map.get("response_param")!=null){
			//信息返回
			map.putAll((Map<String, Object>) response_map.get("response_param"));
		}
		logger.debug("map："+map);*/
		return	map;
	}

	// 获取头像
	@RequestMapping("/getHeadPortrait.do")
	public void getHeadPortrait(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//跳转视图
		
		 //headPortrait.getHeadPortrait(request, response);
		
	}
	private static String base64Encode(byte[] content) {
		Base64.Encoder encoder = Base64.getEncoder(); // JDK 1.8  推荐方法
		String str = encoder.encodeToString(content);
		return str;

		/**
		 char[] chars = Base64Util.encode(content); // 1.7 及以下，不推荐，请自行跟换相关库
		 String str = new String(chars);

		 return str;
		 */
	}

}
