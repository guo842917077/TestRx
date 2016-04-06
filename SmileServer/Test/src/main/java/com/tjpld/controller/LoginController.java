package com.tjpld.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.alibaba.fastjson.JSONObject;
import com.tjpld.entity.LoginEntity;
import com.tjpld.entity.ResultEntity;
import com.tjpld.service.LoginService;

/**
 * joker app登录验证方法
 * 
 * @author guo 1.在Controller中定义你要写的方法 2.建立对应的实体类 3.定义Dao接口 4.定义对应的Service类
 *         5.写对应的功能 ------------------------------------------
 *         在Controller中定义Service接口 Service层中会用到数据库的操作 所以实现Dao接口
 *         在myBatis文件下导入表对应的mapper文件
 */
@Controller
@EnableWebMvc
@RequestMapping(headers="Accept=*/*",  produces="application/json")
public class LoginController {
	@Resource
	LoginService service;// 自動依賴注入
    @Resource
	LoginEntity userEntity;
	@Resource
	ResultEntity resultEntity;

	@RequestMapping(value = "login", method = RequestMethod.GET,produces = "application/json",headers="Accept=*/*")
	@ResponseBody
	public  ResultEntity login(HttpServletRequest request,HttpServletResponse response) {

		//拿到服务器端的参数
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (service.isExitsPeople(username, password)==1) {
		    userEntity.setUsername(username);
			userEntity.setPassword(password);
			resultEntity.setIsSuccess(true);
			resultEntity.setMessage("登录成功");
			resultEntity.setResult(userEntity);
			return  resultEntity;
			
		}else{
			resultEntity.setIsSuccess(false);
			resultEntity.setMessage("登录失败");
			resultEntity.setResult("");
			return  resultEntity;
		}    
	   //System.out.println("测试login方法是否成功" + service.getUserCount(username, password));
       // System.out.println("测试MyBatis使用是否成功" + service.isExitsPeople(username, password));
	}
	
}
