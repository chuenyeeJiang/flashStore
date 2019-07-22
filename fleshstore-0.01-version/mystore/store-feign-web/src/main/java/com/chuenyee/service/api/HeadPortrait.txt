package com.chuenyee.service.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

public interface HeadPortrait {
	
	public void uploadPreviewDiagram(HttpServletRequest request,
			HttpServletResponse response, MultipartFile file, String fileType)
			throws Exception;
	
	public void Preview(HttpServletRequest request,
			HttpServletResponse response)
			throws Exception;

	// 上传头像
	public void uploadHeadPortrait(HttpServletRequest request,
			HttpServletResponse response,MultipartFile file,String x,String y,String w,String h,String previewW,String previewH,String reView) throws Exception;
    
	//获取头像
	public void getHeadPortrait(HttpServletRequest request, HttpServletResponse response)
			throws Exception;
}
