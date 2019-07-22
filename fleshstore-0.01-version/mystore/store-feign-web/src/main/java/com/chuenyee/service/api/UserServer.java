package com.chuenyee.service.api;


import java.util.Map;

import com.chuenyee.service.UserFallbackService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//,fallback=FallbackService.class
@FeignClient(name="store-userServer",fallback= UserFallbackService.class)
public interface UserServer {
	
	@RequestMapping(value="/store/hello",consumes=MediaType.APPLICATION_JSON_VALUE,method=RequestMethod.GET)
	String Hello(@RequestParam("arg") String arg);

	@RequestMapping(value="/store/loginCl",consumes=MediaType.APPLICATION_JSON_VALUE,method=RequestMethod.GET)
	public Map<String, Object> loginCl(@RequestParam Map<String,Object> request_map) ;
	
	@RequestMapping(value="/store/Regis.do",consumes=MediaType.APPLICATION_JSON_VALUE,method=RequestMethod.GET)
	public Map<String, Object> regis(@RequestParam Map<String,Object> request_map) ;
	
	@RequestMapping(value="/store/getUsername.do",consumes=MediaType.APPLICATION_JSON_VALUE,method=RequestMethod.GET)
	public String getUsername(@RequestParam("accessToken") String accessToken) ;

	@RequestMapping(value="/store/logOut.do",consumes=MediaType.APPLICATION_JSON_VALUE,method=RequestMethod.GET)
	public Map<String, Object> logOut(@RequestParam("accessToken") String accessToken) ;
}
