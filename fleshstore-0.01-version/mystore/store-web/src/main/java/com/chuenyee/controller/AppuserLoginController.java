package com.chuenyee.controller;



import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.chuenyee.service.api.AppuserService;



@RestController
public class AppuserLoginController{

	@Autowired
	private AppuserService appuserService;

	Logger logger = LoggerFactory.getLogger(AppuserLoginController.class);


    @Autowired
	com.chuenyee.service.EqualizerService EqualizerService;
	
	@RequestMapping(value="/loginCl")
	public String userLog(HttpServletRequest request,
			HttpServletResponse response, String username, String password,
			String validatecode,String remenberUsername,String autoLog,String nolog,String reView,Map<String,Object> map) throws Exception {
		
		//用户名密码UTF-8转码
		username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
		password = new String(password.getBytes("ISO-8859-1"), "UTF-8");
		
		//跳转页面
/*		reView = "WEB-INF/page/index.jsp";*/
		
		String loginView = "login";
		
		//调用登陆验证
		//可用旧版 String re_password = appuserService.login(username);  
		
		ModelAndView mv = appuserService.login(request,response,username,password,validatecode,remenberUsername,autoLog,nolog,loginView,reView);
		map.putAll(mv.getModelMap());
		
		logger.info("reView: "+EqualizerService.Goto(mv.getViewName()));
		//return "reView";
		System.out.println("reView: "+EqualizerService.Goto(mv.getViewName()));
		
		reView = new String(EqualizerService.Goto(mv.getViewName()).getBytes("ISO-8859-1"),"UTF-8");
		return EqualizerService.Goto(mv.getViewName());
	}
	
//	@RequestMapping(value="/login")
//	public String GotoLogin(){
//		return "login";
//	}
	
	@RequestMapping(value="/getUsername.do")
	public void getUsername(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		System.out.println("getUsername.do");
		logger.info("getUsername.do================run");
		HttpSession session = request.getSession();
		logger.info("getUsername.do================get session");
		String username = (String)session.getAttribute("username");
		logger.info("getUsername.do================get username");
		PrintWriter pw = response.getWriter();
		logger.info("getUsername.do================get pw");
        pw.print(username);
    	logger.info("getUsername.do================pw.print");
        pw.flush();
    	logger.info("getUsername.do================pw.flush");
        pw.close();
        logger.info("getUsername.do================pw.close");
	}

}