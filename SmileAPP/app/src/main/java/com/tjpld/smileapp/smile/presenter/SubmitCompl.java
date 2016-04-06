package com.tjpld.smileapp.smile.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * 上传笑话的实现
 */
public class SubmitCompl implements ISubmitPresenter{
    private Context mContext;
    private Handler mHandler;
    public SubmitCompl(Context mContext) {
        this.mContext = mContext;
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void submitSmileContent(String reportname, String content, String datetime) {
        //具体的实现类
    }
}
