package com.tjpld.controller;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
/**
 * 读取Simle表中的数据
 * 
 */

import com.tjpld.entity.ResultEntity;
import com.tjpld.entity.SmileEntity;
import com.tjpld.service.SmileService;

@Controller
@EnableWebMvc
@RequestMapping(headers = "Accept=*/*", produces = "application/json")
public class SmileController {
	@Resource
	SmileService mService;
	@Resource
	ResultEntity resultEntity;
	ArrayList<SmileEntity> mData=new ArrayList<>();

	@RequestMapping(value = "smile", method = RequestMethod.GET, produces = "application/json", headers = "Accept=*/*")
	@ResponseBody
	public ResultEntity findSmileContent() {
		mData = mService.findSmileContent();
		resultEntity.setIsSuccess(true);
		//System.out.println("0000--->"+mData.get(0).getContent());
		resultEntity.setResult(mData);
		resultEntity.setMessage("登录成功");
		return resultEntity;
	}
	@RequestMapping(value = "insertsmile", method = RequestMethod.GET,produces = "application/json",headers="Accept=*/*")
	@ResponseBody
	public ResultEntity insertSmileContent(HttpServletRequest request,HttpServletResponse response){
		String content=request.getParameter("content");
		String people=request.getParameter("reportname");
		String time="2016-4-6";
		mService.submitSmileContent(people, content, time);
			resultEntity.setIsSuccess(true);
			resultEntity.setResult("上传成功");
			resultEntity.setMessage("上传成功");
		
		return resultEntity;
		
	}
}
