package com.chuenyee.pojo;

import java.io.Serializable;
import java.util.Date;

public class Appuser implements Serializable{
    private int appuserid;
    private String username;
    private String password;
    private String realname;
    private String phone;
    private int grade;
    private Date rectime;
    private int state;
	public int getAppuserid() {
		return appuserid;
	}
	public void setAppuserid(int appuserid) {
		this.appuserid = appuserid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getRectime() {
		return rectime;
	}
	public void setRectime(Date rectime) {
		this.rectime = rectime;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "Appuser [appuserid=" + appuserid + ", username=" + username
				+ ", password=" + password + ", realname=" + realname
				+ ", phone=" + phone + ", grade=" + grade + ", rectime="
				+ rectime + ", state=" + state + "]";
	}
	
    
}
