package com.chuenyee.service.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.chuenyee.pojo.Appuser;


public interface AppuserService {
	/* void insert(Appuser appuser) throws Exception; */

	/* boolean deleteAppuser(int appuserid) throws Exception; */

	// 用戶信息修改
	boolean updateAppuser(Appuser appuser) throws Exception;

	ModelAndView tabQuery(HttpServletRequest request,HttpServletResponse response,Integer pn,String reView);
	  
	/* Appuser findAppuserById(int appuserid) throws Exception; */

	// 登录
	/* String login(String username) throws Exception; */
	// 登录
	ModelAndView login(HttpServletRequest request,
			HttpServletResponse response, String username, String password,
			String validatecode, String remenberUsername, String autoLog,String nolog,
			String loginView,String reView) throws Exception;

	// 注销
	ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response, String loginView);

	// 注册
	ModelAndView regis(HttpServletRequest request,
			HttpServletResponse response, String regisValidatecode,
			String regisUsername, String regisPassword, String reView)
			throws Exception;

	// 验证用户是否存在,并获取用户
	Appuser getUser(String username) throws Exception;

	
	//判断是否已经登陆
	boolean isLog(HttpServletRequest request,HttpServletResponse response) throws Exception;
	
	//获取用户权限
	int getGrade(String username) throws Exception;
	
	// 用户列表
	List<Appuser> getUsers() throws Exception;
}
