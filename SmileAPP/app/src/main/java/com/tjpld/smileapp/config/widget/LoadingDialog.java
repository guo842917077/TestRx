package com.tjpld.smileapp.config.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tjpld.smileapp.R;

/**
 * 加载进度条
 */
public class LoadingDialog extends ProgressDialog {
    private String mDefLoadingText = "正在加载，请稍后...";
    private String mDefLoadingFailure = "加载失败，请稍后...";
    private String mDefLoadingSuccess = "加载成功";
    private Bitmap mDefSuccessBitmap;
    private Bitmap mDefFailureBitmap;
    private TextView mTextView;
    private ProgressBar mProgressBar;
    private ImageView mImageViewSucceed;
    private Context mContext;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private boolean outSideCancelable = false;
    public LoadingDialog(Context context, Builder builder, int theme) {
        this(context);
        this.mContext = context;
        this.mDefLoadingText = builder.mDefLoadingText;
        this.mDefLoadingFailure = builder.mDefLoadingFailure;
        this.mDefSuccessBitmap = builder.mSuccessBitmap;
        this.mDefFailureBitmap = builder.mFailureBitmap;
        this.outSideCancelable = builder.outCancel;

    }

    public LoadingDialog(Context context) {
       super(context);
       Log.e("Tag","LodingDialog--->1");
    }

    public LoadingDialog(Context context, int theme) {
        this(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loadingdailog);
        Log.e("Tag", "dialog");
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar_dialog);
        mTextView = (TextView) findViewById(R.id.progresstitle_dialog);
        mImageViewSucceed = (ImageView) findViewById(R.id.imageView_loadsuccess);
        mProgressBar.setIndeterminate(true);
    }

    public void showDialog() {
     /*   mProgressBar.setVisibility(View.GONE);
        mImageViewSucceed.setVisibility(View.VISIBLE);
        mTextView.setText(mDefLoadingText);*/
        show();
    }

    public void loadSuccess() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mDefLoadingSuccess != null) {
                    mImageViewSucceed.setImageBitmap(mDefSuccessBitmap);
                    mImageViewSucceed.setVisibility(View.VISIBLE);
                }
                mTextView.setText(mDefLoadingSuccess);
                show();
            }
        });
    }

    public void loadFailure() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mDefLoadingSuccess != null) {
                    mImageViewSucceed.setImageBitmap(mDefFailureBitmap);
                    mImageViewSucceed.setVisibility(View.VISIBLE);
                }
                mTextView.setText(mDefLoadingFailure);
                show();
            }
        });
    }

    public void cancel() {
        cancel();
    }

    public static class Builder {
        private String mDefLoadingText = "正在加载，请稍后...";
        private String mDefLoadingFailure = "加载失败...";
        private Context context;
        private Bitmap mSuccessBitmap;
        private Bitmap mFailureBitmap;
        private boolean outCancel = false;
        public Builder(Context context) {
            this.context = context;
        }

        public Builder getmDefLoadingText() {
            return this;
        }

        public Builder setmDefLoadingText(String mDefLoadingText) {
            this.mDefLoadingText = mDefLoadingText;
            return this;
        }

        public Builder getmDefLoadingFailure() {
            return this;
        }

        public Builder setmDefLoadingFailure(String mDefLoadingFailure) {
            this.mDefLoadingFailure = mDefLoadingFailure;
            return this;
        }

        public Builder setCompleteBitmap(Bitmap bitmap) {
            if (bitmap != null) {
                this.mSuccessBitmap = bitmap;
            }
            return this;
        }

        public Builder setOutSideCancel(boolean flag) {
            this.outCancel = flag;
            return this;
        }

        public Builder setFailureBitmap(Bitmap bitmap) {
            if (bitmap != null) {
                this.mFailureBitmap = bitmap;
            }
            return this;
        }

        public LoadingDialog build() {
            //final LoadingDialog dialog = new LoadingDialog(context,this,R.style.LoadingDialog);
            final LoadingDialog dialog = new LoadingDialog(context, this, R.style.LoadingDialog);
            if (outCancel) {
                dialog.setCanceledOnTouchOutside(outCancel);
            }
            Log.e("Tag", "builder2");
            return dialog;
        }
    }
}
