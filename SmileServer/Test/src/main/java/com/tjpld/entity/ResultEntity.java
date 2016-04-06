package com.tjpld.entity;

import org.springframework.stereotype.Component;

@Component
public class ResultEntity {
	private boolean IsSuccess;// 请求是否成功
	private Object Result;// 返回的结果集
	private String Message;// 错误消息

	public boolean isIsSuccess() {
		return IsSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		IsSuccess = isSuccess;
	}



	public Object getResult() {
		return Result;
	}

	public void setResult(Object result) {
		Result = result;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

}
