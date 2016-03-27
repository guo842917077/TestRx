package com.tjpld.smileapp.login.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.tjpld.smileapp.R;
import com.tjpld.smileapp.config.model.ResultModel;
import com.tjpld.smileapp.config.model.UserModel;
import com.tjpld.smileapp.config.network.RetrofitFactory;
import com.tjpld.smileapp.config.widget.LoadingDialog;
import com.tjpld.smileapp.main.view.MainActivity;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * LoginActivity方法的具体实现类
 */
public class LoginComple implements ILoginPresenter {
    private Context mContext;
    private LoadingDialog dialog;

    public LoginComple(Context mContext) {
        this.mContext = mContext;

    }

    /**
     * login方法的实现 使用RxJava
     *
     * @param username 用户名
     * @param password 密码
     */
    @Override
    public void doLogin(String username, String password) {
        dialog.show();
        RetrofitFactory.getInstance().loginApp(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultModel<UserModel>>() {
                    @Override
                    public void onCompleted() {
                        dialog.loadSuccess();
                        Log.e("tag", "成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                         dialog.loadFailure();
                    }

                    @Override
                    public void onNext(ResultModel<UserModel> result) {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        mContext.startActivity(intent);
                        Log.e("tag", "username---->" + result.getResult().getUsername());
                    }
                });
    }
}
