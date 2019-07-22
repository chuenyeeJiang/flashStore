//package com.chuenyee.controller;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping(value="/web")
//public class EqualizerController {
//	
//	Logger logger = LoggerFactory.getLogger(EqualizerController.class);
//	
////    @Value(value="${server.port}")
////	String value ;
//    
//    @Autowired
//    EqualizerService EqualizerService;
//    
//    
//    
//    @RequestMapping(value="/port")
//	public String port(){
//		return EqualizerService.Port();
//	}
//
//	
//	@RequestMapping(value="/index",method=RequestMethod.GET)
//	public String index(){
//		logger.info("====================== Web Controller GotoIndex");
//		return EqualizerService.Index();
//	}
//	
//
//	
//	@RequestMapping(value="/login",method=RequestMethod.GET)
//	public String login(){
//		logger.info("====================== Web Controller GotoLogin");
//		return EqualizerService.Login();
//	}
//	
//
//}
