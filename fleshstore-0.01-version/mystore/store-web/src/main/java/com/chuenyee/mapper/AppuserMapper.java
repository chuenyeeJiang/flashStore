package com.chuenyee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chuenyee.pojo.Appuser;

@Mapper
public interface AppuserMapper {
	// 增
	public void insert(Appuser appuser) throws Exception;

	// 刪
	public boolean deleteById(int appuserid) throws Exception;
	
	// 改
	public boolean update(Appuser appuser) throws Exception;
	
	// 查
	public Appuser findById(int appuserid) throws Exception;
	
	
	public String findPasswordByUsername(String username) throws Exception;

	public Appuser findByName(String username) throws Exception;

	public int getGrade(String username) throws Exception;

	public List<Appuser> findAll() throws Exception;
	
	
}
