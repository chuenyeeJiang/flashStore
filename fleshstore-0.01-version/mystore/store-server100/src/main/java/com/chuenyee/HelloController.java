package com.chuenyee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/server")
public class HelloController {

	Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Value(value="${server.port}")
	String value ;
	
	
	@RequestMapping(value="/port",method=RequestMethod.GET)
	public String Port(){
		return value;
	}
	
	@RequestMapping(value="/hello")
	public String hello(@RequestParam("arg") String arg) throws InterruptedException{
		Thread.sleep(1000);
		logger.info("====================== Service arg:"+arg);
		System.out.println("====================== Service arg:"+arg);
		return "server"+value+":Hello "+arg;
	}
}
