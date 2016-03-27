package com.tjpld.smileapp.smile.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.tjpld.smileapp.config.model.ResultModel;
import com.tjpld.smileapp.config.model.SmileContentModel;
import com.tjpld.smileapp.config.network.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by guo on 2016/3/13.
 */
public class SmileCompl implements ISmilePresenter {
    private Context mContext;
    private Handler mHandler;
    private ISmileView mSmileView;
    final List<SmileContentModel> mData = new ArrayList<>();

    public SmileCompl(Context mContext, ISmileView mSmileView) {
        this.mSmileView = mSmileView;
        this.mContext = mContext;
        mHandler = new Handler(Looper.getMainLooper());
    }

    //加载网络数据到适配器
    @Override
    public void loadSmileData() {
        //data.clear();
        RetrofitFactory.getInstance().smileContent()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultModel<List<SmileContentModel>>>() {
                    @Override
                    public void onCompleted() {
                        Log.e("tag", "completed" + "成功");
                        mSmileView.loadSmileData(mData);
                        mSmileView.refreshSmileData();
                    }

                    @Override
                    public void onError(final Throwable e) {
                        Log.e("tag", "message" + e.toString());
                    }

                    @Override
                    public void onNext(ResultModel<List<SmileContentModel>> listResultModel) {
                        mData.addAll(listResultModel.getResult());
                    }
                });
        //return data;
    }

    @Override
    public void refreshSmileData() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mSmileView.refreshSmileData();
            }
        });
    }
}
