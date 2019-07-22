package com.chuenyee.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class GotoController {
 
	Logger  logger=LoggerFactory.getLogger(GotoController.class); 
	
    @RequestMapping(value="/index")
	public String GotoIndex(HttpServletRequest request,Map<String,Object> map) throws Exception{
    	System.out.println("index");
		return "index";
	}
    
    @RequestMapping(value="/login")
 	public String GotoLogin(HttpServletRequest request,Map<String,Object> map) throws Exception{
     	System.out.println("login");
 		return "login";
 	}
    
    @RequestMapping(value="/success")
  	public String GotoSuccess(HttpServletRequest request,Map<String,Object> map) throws Exception{
      	System.out.println("success");
  		return "success";
  	}
    
    @RequestMapping(value="/regis")
  	public String GotoRegis(HttpServletRequest request,Map<String,Object> map) throws Exception{
      	System.out.println("regis");
  		return "regis";
  	}
    
    @RequestMapping(value="/log_test")
  	public String log_test(HttpServletRequest request,Map<String,Object> map) throws Exception{
    	logger.info("hello!!  log info");
    	logger.debug("hello!!  log info");
    	logger.error("hello!!  log error");
    	logger.warn("hello!!  log warn");
  		return "success";
  	}
    

}
