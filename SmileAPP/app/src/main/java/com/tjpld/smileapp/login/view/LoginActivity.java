package com.tjpld.smileapp.login.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tjpld.smileapp.R;
import com.tjpld.smileapp.config.widget.LoadingDialog;
import com.tjpld.smileapp.login.presenter.LoginComple;
import com.tjpld.smileapp.login.presenter.ILoginPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 登录页面
 * 使用ButterKnife注解初始化view
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.et_username_login)
    public EditText mEtUserName;
    @Bind(R.id.et_password_login)
    public EditText mEtPassWord;
    @Bind(R.id.btn_login)
    public Button mBtnLogin;
    private ILoginPresenter presenter;
    private LoadingDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter=new LoginComple(this);
        mBtnLogin.setOnClickListener(this);
        Bitmap successBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_dialog_success);
        LoadingDialog.Builder builder = new LoadingDialog.Builder(this);
        builder.setCompleteBitmap(successBitmap)
                .setmDefLoadingText("正在登录...")
                .setOutSideCancel(true)
                .build();
        mDialog=new LoadingDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                mDialog.show();
                presenter.doLogin(mEtUserName.getText().toString(),mEtPassWord.getText().toString());
                break;
        }
    }
}
