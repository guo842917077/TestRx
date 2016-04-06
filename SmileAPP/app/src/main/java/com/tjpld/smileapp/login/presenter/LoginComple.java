package com.tjpld.smileapp.login.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.tjpld.smileapp.main.view.MainActivity;

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
    public void doLogin(Activity activity,String username, String password) {
        //开启动画

        //  dialog.show();
        Intent intent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(intent);
        activity.finish();
      /*  RetrofitFactory.getInstance().loginApp(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultModel<UserModel>>() {
                    @Override
                    public void onCompleted() {
                        //dialog.loadSuccess();
                        Log.e("tag", "成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        //dialog.loadFailure();
                    }

                    @Override
                    public void onNext(ResultModel<UserModel> result) {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        mContext.startActivity(intent);
                        Log.e("tag", "username---->" + result.getResult().getUsername());
                        activity.overridePendingTransition(R.anim.sild_out_top,R.anim.sild_out_top);
                    }
                });*/
    }
}
