package com.tjpld.yuanhangrxjava.login.presenter;

import android.content.Context;
import android.content.Intent;

import com.tjpld.yuanhangrxjava.main.MainActivity;
import com.tjpld.yuanhangrxjava.config.model.UserModel;
import com.tjpld.yuanhangrxjava.config.network.RetrofitClient;
import com.tjpld.yuanhangrxjava.config.utlis.commonutils.SharePreferenceUtil;
import com.tjpld.yuanhangrxjava.config.utlis.commonutils.ToastUtils;
import com.tjpld.yuanhangrxjava.config.utlis.jsonutils.ResultEntity;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 登录界面的业务逻辑实现
 */
public class LoginCompl implements ILoginPresenter {
    private Context mContext;

    public LoginCompl(Context context) {
        this.mContext = context;
    }
    //登录
    @Override
    public void loginSubmit(String username, String password) {
        new RetrofitClient().getYuanHangApi().login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultEntity<UserModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.show(mContext, e.toString(), 2000);
                    }

                    @Override
                    public void onNext(ResultEntity<UserModel> resultEntity) {
                        String id = resultEntity.getResult().getId();
                        String username = resultEntity.getResult().getUsername();
                        String password = resultEntity.getResult().getPassword();
                        String depid = resultEntity.getResult().getDepartmentId();
                        //将数值保存到共享文件
                        SharePreferenceUtil.setValue(mContext, UserModel.SP_ID, id);
                        SharePreferenceUtil.setValue(mContext, UserModel.SP_UserName, username);
                        SharePreferenceUtil.setValue(mContext, UserModel.SP_PassWord, password);
                        SharePreferenceUtil.setValue(mContext, UserModel.SP_departmentID, depid);
                        Intent intent = new Intent(mContext, MainActivity.class);
                        mContext.startActivity(intent);
                    }
                });
    }
}
