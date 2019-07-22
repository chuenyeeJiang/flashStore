package com.chuenyee.service;  
 

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class EqualizerService {
	
	Logger logger = LoggerFactory.getLogger(EqualizerService.class);
	
	@Autowired
	RestTemplate restTemplate;
	
    @Value(value="${server.port}")
	String value ;
	

	@HystrixCommand(fallbackMethod="PortFallback")
	public String Port(){
		return value;
	}
	
	public String PortFallback(){
		return "error";
	}
	
	@HystrixCommand(fallbackMethod="GotoFallback")
	public String Goto(String target) throws UnsupportedEncodingException{
		logger.info("====================== Web Service Goto: "+target);
		String re = restTemplate.getForObject("http://store-web/store/"+target, String.class);
//		re = new String(re.getBytes("ISO-8859-1"),"UTF-8");
		logger.info("====================== re:"+re);
		return re;
	}
	
	public String GotoFallback(String target) throws UnsupportedEncodingException{
		logger.info("====================== Web Service Goto");
		String re = restTemplate.getForObject("http://store-web/store/login.html", String.class);
		re = new String(re.getBytes("ISO-8859-1"),"UTF-8");
		logger.info("====================== re:"+re);
		return re;
	}
	

}



