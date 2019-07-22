package com.chuenyee.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.chuenyee.service.api.AppuserService;





@Controller 
public class AppuserRegisController {
	@Autowired
    private AppuserService appuserService; 
    @RequestMapping("/Regis.do")
	public void userRegis(HttpServletRequest request,HttpServletResponse response,String regisValidatecode,String regisUsername,String regisPassword) throws Exception
	{
        response.setCharacterEncoding("utf-8");
    	
        /*
         * UTF-8转码
         */
        regisUsername= new String(regisUsername.getBytes("ISO-8859-1"),"UTF-8");
        
        //指定返回界面
        String reView = "tbsearch";
        
        ModelAndView mv = appuserService.regis(request,response,regisValidatecode,regisUsername,regisPassword,reView);
       
        /*
         * ajax输出
         */
        //返回的跳转页面
        String gotoView = mv.getViewName();
        
        //成功跳转
        if(gotoView.equals(reView))
        {
        	request.getRequestDispatcher(reView).forward(request, response);
        }
    	
	}
    
//    @RequestMapping(value="/regis")
//	public String GotoRegis(){
//		return "regis";
//	}
//    
    @RequestMapping(value="/regisSuccess")
	public String GotoLogin(){
		return "tbsearch.html";
	}
    
  }
