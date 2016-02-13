package com.tjpld.yuanhangrxjava.config.utlis.jsonutils;

/**
 * Created by guo on 2015/10/16.
 */
public class ResultEntity<T> {
    private boolean isSuccess;
    private String message;
    private T result;

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
