package com.tjpld.smileapp.login.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tjpld.smileapp.R;
import com.tjpld.smileapp.config.model.ResultModel;
import com.tjpld.smileapp.config.model.UserModel;
import com.tjpld.smileapp.config.network.RetrofitFactory;
import com.tjpld.smileapp.main.view.MainActivity;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * LoginActivity方法的具体实现类
 */
public class LoginComple implements ILoginPresenter {
    private Context mContext;
    //private LoadingDialog dialog;

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
    public void doLogin(final Activity activity,String username, String password) {
        //开启动画
        RetrofitFactory.getInstance().loginApp(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultModel<UserModel>>() {
                    @Override
                    public void onCompleted() {
                        //dialog.loadSuccess();

                    }

                    @Override
                    public void onError(Throwable e) {
                        //dialog.loadFailure();
                        Log.e("tag", "failure---->" + e.toString());
                    }

                    @Override
                    public void onNext(ResultModel<UserModel> result) {
                        Log.e("tag", "成功");
                        Intent intent = new Intent(mContext, MainActivity.class);
                        mContext.startActivity(intent);
                        activity.overridePendingTransition(R.anim.sild_out_top, R.anim.sild_out_top);
                    }
                });
    }
}
