package com.chuenyee.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chuenyee.service.LoginServer;
import com.chuenyee.service.api.UserServer;

@Controller
public class UserController extends HttpServlet {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserServer userServer;
	
	@Autowired
	LoginServer loginServer;
	
	// redis
		@Autowired
		private RedisTemplate<String, Object> redisTemplate;

	@RequestMapping("/web/hello/{arg}")
	public String Hello(@PathVariable("arg") String arg) {
		logger.info(arg);
		System.out.println("====================arg:" + arg);
		// return restTemplate.getForObject("", responseType)
		return userServer.Hello(arg);
	}

	@RequestMapping(value="/loginCl",method=RequestMethod.POST)
	public String loginCl(HttpServletRequest request,
			HttpServletResponse response,
		    @RequestParam  Map<String,Object> request_map,Map<String,Object> map) throws UnsupportedEncodingException {
		// return restTemplate.getForObject("", responseType)
		logger.debug("web UserController=====================loginCl");
		System.out.println("web UserController=====================loginCl");
		
		//打包请求
		System.out.println("request_map.keySet():"+request_map.keySet());
		logger.debug("request_map.keySet():"+request_map.keySet());
		
		//记住用户
		if(request_map.get("remenberUsername")!=null){
		loginServer.remenberName(
				response,
				(String)request_map.get("username")
				);
		}else {
			loginServer.forgetName(request,response);
		}
		
		long past = System.currentTimeMillis();
		Map<String, Object> response_map = userServer.loginCl(request_map);
		String accessToken = null;
		if(response_map!=null)
		if(response_map.get("response_param")!=null){
		map.putAll((Map<String, Object>)response_map.get("response_param"));
		accessToken = (String) ((Map<String, Object>) response_map.get("response_param")).get("accessToken");
		}
		Cookie cookie = new Cookie("accessToken", accessToken);
		response.addCookie(cookie);
		
		System.out.println("set accessToken:"+accessToken);
		logger.debug("set accessToken:"+accessToken);
		
		logger.debug("map.keySet():"+map.keySet());
		return	(String) response_map.get("view");
	}
	
	@RequestMapping(value="/Regis.do",method=RequestMethod.POST)
	public String Regis(HttpServletRequest request,
			HttpServletResponse response,
		    @RequestParam  Map<String,Object> request_map,Map<String,Object> map) throws UnsupportedEncodingException {
		// return restTemplate.getForObject("", responseType)
		logger.info("web UserController=====================regis");
		
		/*
		 * 打包请求
		 */
		logger.debug("request_map.keySet():"+request_map.keySet());
		
		
		/*
		 * 请求服务
		 */
		Map<String, Object> response_map = userServer.regis(request_map);
		
		/*
		 * 响应信息
		 */
		
		//登陆令牌
		String accessToken = null;
		if(response_map.get("response_param")!=null){
	    //信息返回
		map.putAll((Map<String, Object>) response_map.get("response_param"));
		// sso登陆令牌
		accessToken = (String) ((Map<String, Object>) response_map.get("response_param")).get("accessToken");
		}
		Cookie cookie = new Cookie("accessToken", accessToken);
		response.addCookie(cookie);
		

		logger.debug("set accessToken:"+accessToken);
		
		logger.debug("map.keySet():"+map.keySet());
		return	(String) response_map.get("view");
	}

	@RequestMapping(value="/logOut.do")
	public String logOut(HttpServletRequest request,HttpServletResponse response,Map<String,Object> map)
	{
		/*
		   获取令牌
		 */
		Cookie[] cookies = request.getCookies();
		String accessToken = null;
		for(int i=0;i<cookies.length;i++)
		{
			if(cookies[i].getName().equals("accessToken"))
			{
				cookies[i].setValue(null);
				cookies[i].setMaxAge(0);
				response.addCookie(cookies[i]);
			}
		}
		Map<String, Object> response_map = userServer.logOut(accessToken);

		if(response_map!=null){
			if(response_map.get("response_param")!=null){
				map.putAll((Map<String, Object>)response_map.get("response_param"));
			}
		}
		//return	(String) response_map.get("view");
		return	"login";
	}
	
	
	@RequestMapping("/getUsername.do")
	public String getusername(HttpServletRequest request,HttpServletResponse response,Map<String, Object> map) throws IOException {
		logger.info("getusername");
		// return restTemplate.getForObject("", responseType)
		Cookie[] cookies = request.getCookies();
		String accessToken = null;
		String username = null;
		for(int i=0;i<cookies.length;i++)
    	{
    		if(cookies[i].getName().equals("accessToken"))
    		{
    			accessToken = cookies[i].getValue();
    		}
    	}
		logger.info("get accessToken:"+accessToken);
        if(accessToken==null||accessToken==""){
		return null;
    	}else{
    		PrintWriter pw = response.getWriter();
    		logger.info("getUsername.do================get pw");
    		username = userServer.getUsername(accessToken);
            pw.print(username);
        	logger.info("getUsername.do================pw.print");
            pw.flush();
        	logger.info("getUsername.do================pw.flush");
            pw.close();
    		return null;
    	}
	}


}
