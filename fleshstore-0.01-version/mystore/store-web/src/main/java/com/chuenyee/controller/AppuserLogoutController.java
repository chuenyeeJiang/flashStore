package com.chuenyee.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.chuenyee.service.api.AppuserService;


@RestController
public class AppuserLogoutController {
	@Autowired
	private AppuserService appuserService;

    @Autowired
	com.chuenyee.service.EqualizerService EqualizerService;
    
	@RequestMapping("/logOut.do")
	public String userLogout(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("/logOut.do");
		String loginView = "/login.html";
		ModelAndView mv = appuserService.logout(request,response,loginView);
		return EqualizerService.Goto(mv.getViewName());
	}
}
