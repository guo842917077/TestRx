package com.tjpld.smileapp.config.model;

/**
 * 接口返回类型的一种统一格式
 */
public class ResultModel<T> {
    private boolean isSuccess;//请求是否成功
    private String message;//请求结果 错误原因
    private T result;//返回的结果

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
