package com.chuenyee.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.chuenyee.service.api.HeadPortrait;

@Controller
public class HeadPortraitController {
	@Autowired
	HeadPortrait headPortrait;

	// 上传预览图
	@RequestMapping("/uploadPreviewDiagram.do")
	public void uploadPreviewDiagram(HttpServletRequest request,
			HttpServletResponse response, MultipartFile file, String fileType)
			throws Exception {
		System.out.println("uploadPreviewDiagram.do");

		headPortrait.uploadPreviewDiagram(request, response, file, fileType);

	}

	// 预览
	@RequestMapping("/PreviewHeadPortrait.do")
	public void Preview(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("PreviewHeadPortrait.do");

		headPortrait.Preview(request, response);
	}

	// 上传头像
	@RequestMapping("/uploadHeadPortrait.do")
	public void uploadHeadPortrait(HttpServletRequest request,
			HttpServletResponse response, MultipartFile file, String x,
			String y, String w, String h,String previewW,String previewH) throws Exception {
	 String reView = "/WEB-INF/page/index.jsp";
		
	headPortrait.uploadHeadPortrait(request, response, file, x, y, w, h,previewW,previewH,reView);

	}

	// 获取头像
	@RequestMapping("/getHeadPortrait.do")
	public void getHeadPortrait(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//跳转视图
		
		 headPortrait.getHeadPortrait(request, response);
		
	}

}
