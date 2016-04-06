package com.tjpld.smileapp.login.presenter;

import android.app.Activity;

/**
 * LoginActivity对应的Presenter类 定义login界面要实现的操作
 */
public interface ILoginPresenter {
    /**
     * 登录方法
     * @param username 用户名
     * @param password 密码
     */
    public void doLogin(Activity activity,String username,String password);
}
