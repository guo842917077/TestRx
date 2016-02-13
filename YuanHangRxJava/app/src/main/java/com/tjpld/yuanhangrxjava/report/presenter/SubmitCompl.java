package com.tjpld.yuanhangrxjava.report.presenter;


import android.os.Handler;
import android.os.Looper;

import java.util.List;

/**
 * 图文上报逻辑
 */
public class SubmitCompl implements ISubmitPresenter {
    private ISubmitView iSubmitView;
    private Handler mHandler;

    public SubmitCompl(ISubmitView iSubmitView) {
        this.iSubmitView = iSubmitView;
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void hiddenReport(String description, List<String> mBitmapList, String type) {

    }

    @Override
    public void selectPhoto() {
        iSubmitView.selectPhoto();
    }

    @Override
    public void takePhoto() {
        iSubmitView.takePhoto();
    }
}
