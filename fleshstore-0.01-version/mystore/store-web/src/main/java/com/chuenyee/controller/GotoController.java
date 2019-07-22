package com.chuenyee.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class GotoController {

	
    @RequestMapping(value="/index")
	public String GotoIndex(HttpServletRequest request,Map<String,Object> map) throws Exception{
    	System.out.println("index");
		return "index";
	}
    

}
