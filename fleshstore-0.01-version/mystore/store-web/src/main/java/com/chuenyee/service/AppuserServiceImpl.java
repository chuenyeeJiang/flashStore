package com.chuenyee.service;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.chuenyee.mapper.AppuserMapper;
import com.chuenyee.pojo.Appuser;
import com.chuenyee.service.api.AppuserService;
import com.chuenyee.service.api.Encryption;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class AppuserServiceImpl implements AppuserService {
	

	
	@Autowired
	private AppuserMapper appuserMapper;
	
	// 加密接口
	Encryption encryption = new JcParseMD5();;
    // 分页信息 接口 
	PageInfo appuser_pageInfo;
	/*
	 * public void insert(Appuser appuser) throws Exception { ApplicationContext
	 * cfg = new ClassPathXmlApplicationContext("ApplicationContext.xml");
	 * AppuserMapper appuserMapper=(AppuserMapper) cfg.getBean("appuserMapper");
	 * // TODO Auto-generated method stub
	 * System.out.println("saveAppuser...run");
	 * System.out.println("appuser��"+appuser);
	 * System.out.println("appuserMapper��"+appuserMapper);
	 * appuserMapper.insert(appuser); }
	 */

	/*
	 * public boolean deleteAppuser(int appuserid) throws Exception { // TODO
	 * Auto-generated method stub // return appuserMapper.delete(appuserid);
	 * return false; }
	 */

	// 用戶信息修改
	public boolean updateAppuser(Appuser appuser) throws Exception {
		// TODO Auto-generated method stub
		// return appuserMapper.update(appuser);
		return false;
	}

	/*
	 * public Appuser findAppuserById(int appuserid) throws Exception { // TODO
	 * Auto-generated method stub Appuser
	 * appuser=appuserMapper.findById(appuserid); return appuser; }
	 */

	// 登陆
	/*
	 * public String login(String username) throws Exception { // TODO
	 * Auto-generated method stub String password =
	 * appuserMapper.findPasswordByUsername(username); return password; }
	 */

	// 新-登陆Test
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response, String username, String password,
			String validatecode, String remenberUsername, String autoLog,String nolog,
			String loginView,String reView) throws Exception {
		System.out.println("login!");
		ModelAndView mv = new ModelAndView();
//		String loginView = request.getHeader("name");    获取发送请求的请求头，有待研究
//		String loginView = "login.html";
		// 获取正确验证码
		HttpSession session = request.getSession();
		String session_validatecode = (String) session
				.getAttribute("Validatecode");
		System.out.println("获取正确验证码：" + session_validatecode);

		// 默认跳转至登录界面
		mv.setViewName(loginView);
		System.out.println("默认跳转至登录界面：" + loginView);

		// 记住用户名(on Cookie添加用户名 null 销毁用户名Cookie)
		if (remenberUsername != null) {
			Cookie cookie_username = new Cookie("username", username);
			cookie_username.setMaxAge(360*24*3600);
			response.addCookie(cookie_username);
			System.out.println("Cookie添加用户名" + username);
		} else {
			try {
				Cookie[] cookies = request.getCookies();
				for (int i = 0; i < cookies.length; i++) {
					if (cookies[i].getName().equals("username")) {
						cookies[i].setValue(null);
						cookies[i].setMaxAge(0);
						response.addCookie(cookies[i]);
					}
				}
				System.out.println("销毁用户名：" + username);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// 自动登录处理与登记(on Cookie添加密码 null 销毁密码Cookie)
		if (autoLog != null) {
			Cookie cookie_password = new Cookie("password", password);
			cookie_password.setMaxAge(360*24*3600);
			response.addCookie(cookie_password);
			
			System.out.println("Cookie添加密码：" + password);
		} else {
			Cookie[] cookies = request.getCookies();
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("password")) {
					cookies[i].setValue(null);
					cookies[i].setMaxAge(0);
					response.addCookie(cookies[i]);
				}
			}
			System.out.println("销毁密码Cookie：" + password);
		}

		System.out.println("nolog:"+nolog);
		// 验证用户密码是否为空
		if (username == null || username == "") {
			mv.addObject("log_error", "用户名不能为空!");
			System.out.println("用户名不能为空!");
		} else if (password == null || password == "") {
			mv.addObject("log_error", "密码不能为空!");
			System.out.println("密码不能为空!");
		}
		// 自动登录跳过验证
		else if (validatecode == null && (autoLog == null||nolog.equals("1"))) {
			mv.addObject("log_error", "验证码不能为空!");
			System.out.println("验证码不能为空!");
		} else if (!session_validatecode
				.equalsIgnoreCase(
						validatecode)
				&& (autoLog == null||nolog.equals("1"))) {
			mv.addObject("log_error", "验证码错误!");
			System.out.println("验证码错误!");
		} else {
			// 用MD5转化并核验密码
			password = encryption.parseStrToMd5L32(password);

			// 查询用户密码
			String repassword = appuserMapper.findPasswordByUsername(username);
			if (repassword == null || repassword == "") {
				mv.addObject("log_error", "用户不存在!");
				System.out.println("用户不存在!");
			}
			
			// 登陆验证
			else if (repassword.equals(password)) {
				Appuser appuser = getUser(username);
				int grade = appuser.getGrade();
				HttpSession httpSession = request.getSession();
				httpSession.setAttribute("username", username);
				httpSession.setAttribute("password", password);
				httpSession.setAttribute("grade", grade);
				httpSession.setAttribute("appuser", appuser);
				httpSession.setMaxInactiveInterval(-1);
				mv.setViewName(reView);
				System.out.println("success!");
			} else {
				mv.addObject("log_error", "密码错误!");
				System.out.println("密码错误!");
			}

		}
		/* String password = appuserMapper.findPasswordByUsername(username); */
		return mv;
	}

	// 注销
	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response, String loginView) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(loginView);
		HttpSession httpSession = request.getSession();
		httpSession.invalidate();
	    mv.addObject("noLog", 1) ;
	    System.out.println(mv.getModel());
		return mv;
	}

	/*
	 * // 注册 public String regis(Appuser appuser) throws Exception { if
	 * (appuserMapper.findPasswordByUsername(appuser.getUsername()) != null) {
	 * return "用户名已存在"; } else { appuserMapper.insert(appuser); return "1"; } }
	 */
	// 注册
	public ModelAndView regis(HttpServletRequest request,
			HttpServletResponse response, String regisValidatecode,
			String regisUsername, String regisPassword, String reView)
			throws Exception {
		System.out.println("regisValidatecode:" + regisValidatecode);
		System.out.println("regisUsername:" + regisUsername);
		System.out.println("regisPassword:" + regisPassword);

		ModelAndView mv = new ModelAndView();

		// 结果
		String reslut = "注册失败";

		/*
		 * 封装注册用户信息
		 */
		Appuser appuser = new Appuser();
		appuser.setUsername(regisUsername);

		// MD5加密
		regisPassword = encryption.parseStrToMd5L32(regisPassword);
		System.out.println("md5:" + regisPassword);

		appuser.setPassword(regisPassword);

		// 验证码取值
		HttpSession session = request.getSession();
		String session_validatecode = (String) session
				.getAttribute("Validatecode");
		System.out.println("session_validatecode:" + session_validatecode);

		// 验证判断
//		if (!regisValidatecode.equalsIgnoreCase(session_validatecode)) {
//			reslut = "验证码错误!";
//			System.out.println(reslut);
//		}
		
		// 注册
	    if (appuserMapper.findPasswordByUsername(appuser.getUsername()) != null) {
			reslut = "用户已存在!";
			System.out.println(reslut);
		} else {
			appuserMapper.insert(appuser);
			mv.setViewName(reView);
			reslut = "注册成功!";
			// 登陆 值栈赋值
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("username", regisUsername);
			httpSession.setAttribute("password", regisPassword);
			System.out.println(reslut);
		}
		if (reslut.equals("注册成功!")) {
			System.out.println("return mv");
			return mv;
		} else {
			PrintWriter pw = response.getWriter();
			pw.print(reslut);
			System.out.println(reslut);
			pw.flush();
			pw.close();
			System.out.println("return null");
			return null;
		}
	}

	// 验证用户是否存在,并获取用户
	public Appuser getUser(String username) throws Exception {
		// TODO Auto-generated method stub
		Appuser appuser = appuserMapper.findByName(username);
		return appuser;
	}

	// 用户列表
	public List<Appuser> getUsers() throws Exception {
		// TODO Auto-generated method stub
		return appuserMapper.findAll();
	}

	// 分页查询
	public ModelAndView tabQuery(HttpServletRequest request,
			HttpServletResponse response, Integer pn, String reView) {
		// TODO Auto-generated method stub
		ModelAndView mv = new ModelAndView(reView);
		System.out.println("appuserMapper:"+appuserMapper);
		PageHelper.startPage(pn, 5);
		List<Appuser> list = null;
		try {
			list = getUsers();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PageInfo appuser_pageInfo = new PageInfo(list, 5);
		/* request.setAttribute("Customers", list); */
		mv.addObject("appuser_pageInfo", appuser_pageInfo);
		mv.addObject("page", "appuserSelect");
		
		return mv;
	}

	//判断是否已经登陆
	public boolean isLog(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		if(username!=null)
		{
			return true;
		}
		return false;
	}

	//获取用户权限
	public int getGrade(String username)  throws Exception{
		// TODO Auto-generated method stub
		int grade = -1;
		System.out.println("appuserMapper:"+appuserMapper);
	
	    grade = appuserMapper.getGrade(username);
		
		return grade;
	}
}
