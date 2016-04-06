package com.tjpld.smileapp.login.view;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tjpld.smileapp.R;
import com.tjpld.smileapp.config.utlis.commonutils.AnimatorUtils;
import com.tjpld.smileapp.login.presenter.ILoginPresenter;
import com.tjpld.smileapp.login.presenter.LoginComple;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 登录页面
 * 使用ButterKnife注解初始化view
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.et_username_login)
    public EditText mEtUserName;
    @Bind(R.id.et_password_login)
    public EditText mEtPassWord;
    @Bind(R.id.btn_login)
    public Button mBtnLogin;
    @Bind(R.id.img_logo)
    public ImageView mImageLogo;
    //登录功能的实现
    private ILoginPresenter presenter;
    //插值器
    private Interpolator mInterpolatro;
    private AnimatorUtils animatorUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter = new LoginComple(this);
        initComponentAnimator();
        mBtnLogin.setOnClickListener(this);
    }

    private void initComponentAnimator() {
        animatorUtils = new AnimatorUtils();
        mInterpolatro = new FastOutSlowInInterpolator();
        animatorUtils.startScaleLargerAnimator(mBtnLogin, mInterpolatro);
        animatorUtils.startRotationLargerAnimator(mImageLogo, mInterpolatro);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                //  mDialog.showDialog();
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN)
                    animatorUtils.animScaleWithColor(mBtnLogin, "#66b6f5", "#ff3168");
                presenter.doLogin(this, mEtUserName.getText().toString(), mEtPassWord.getText().toString());
                //添加动画的实现
                break;
        }
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        this.overridePendingTransition(R.anim.sild_in_bottom, R.anim.sild_out_top);
    }
}
