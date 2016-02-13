package com.tjpld.yuanhangrxjava.login.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tjpld.yuanhangrxjava.R;
import com.tjpld.yuanhangrxjava.login.presenter.ILoginPresenter;
import com.tjpld.yuanhangrxjava.login.presenter.LoginCompl;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 登录页面 使用KnifeButter+RxJava
 * 1.通过注解替代findViewById不能为私有的变量
 * 2.事件类型的注解方法返回值必须是void
 * 3.可以通过{R.id.a,R.id.b}的方式给注解同时传入多个参数 表示多个按钮调用一个事件
 * 4.BaseUrl 尽量以/结尾
 * 5.json字符串一定要和后台的格式对上
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.et_username_login)
    EditText mEtUsername;//登录名
    @Bind(R.id.et_password_login)
    EditText mEtPassword;//登录密码
    @Bind(R.id.btn_login)
    Button mBtnLogin;
    //-------用户名---------
    private String mUsername, mPassword;
    private ILoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);//初始化控件
        //-----init---------
        presenter = new LoginCompl(this);
        //------click-------
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                mUsername = mEtUsername.getText().toString();
                mPassword = mEtPassword.getText().toString();
                presenter.loginSubmit(mUsername, mPassword);
                break;
        }
    }
}
