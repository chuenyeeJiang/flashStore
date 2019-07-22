package com.chuenyee;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Value(value="${server.port}")
	String value ;
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String Say(){
		return value;
	}
}
