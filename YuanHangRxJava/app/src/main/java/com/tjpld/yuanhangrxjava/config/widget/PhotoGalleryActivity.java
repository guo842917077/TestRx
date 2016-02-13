package com.tjpld.yuanhangrxjava.config.widget;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tjpld.yuanhangrxjava.R;
import com.tjpld.yuanhangrxjava.config.utlis.imageutils.ImageUtils;
import com.tjpld.yuanhangrxjava.report.view.SubmitSimpleActivity;

import java.util.ArrayList;

public class PhotoGalleryActivity extends Activity implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;

    private ImageView[] mTips;
    //在ViewPager中放置图片ImageView
    private ZoomImageView[] mImageViews;

    private LinearLayout mGroup;
    //存放bitmap的地址
    private ArrayList<String> mBitmapList;

    private int mPostion;

    private RelativeLayout mToolbar;
    private Intent intent;
    private Bitmap bitmap;
    String str_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);
        initToolBar();
        intent = this.getIntent();
        str_activity = intent.getStringExtra("activity");
        mPostion = intent.getIntExtra("positions", 0);
        mBitmapList = intent.getStringArrayListExtra("bmps");
        mGroup = (LinearLayout) findViewById(R.id.viewGroup);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        buildImages();
    }

    private void initToolBar() {

        mToolbar = (RelativeLayout) findViewById(R.id.toolbar);

        ImageButton btnback = (ImageButton) findViewById(R.id.btn_back);
        btnback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goBack(mBitmapList);
            }

        });

        ImageButton btndel = (ImageButton) findViewById(R.id.btn_del);
        btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(PhotoGalleryActivity.this).setTitle("提示")
                        .setMessage("确定删除?")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                delImage();

                            }
                        })
                        .show();

            }
        });
    }

    /**
     * 刷新viewpager
     */
    private void buildImages() {
        final Handler handler = new Handler();
        Runnable runnableOnSeparateThread = new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mGroup.removeAllViews();
                        mViewPager.removeAllViews();
                        //将点点加入到ViewGroup中
                        mTips = new ImageView[mBitmapList.size()];
                        for (int i = 0; i < mTips.length; i++) {
                            ImageView imageView = new ImageView(PhotoGalleryActivity.this);
                            imageView.setLayoutParams(new ViewGroup.LayoutParams(10, 10));
                            mTips[i] = imageView;
                            if (i == 0) {
                                mTips[i].setBackgroundResource(R.drawable.page_indicator_focused);
                            } else {
                                mTips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
                            }

                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                            layoutParams.leftMargin = 5;
                            layoutParams.rightMargin = 5;
                            mGroup.addView(imageView, layoutParams);
                        }

                        //将图片装载到数组中
                        mImageViews = new ZoomImageView[mBitmapList.size()];

                        for (int i = 0; i < mImageViews.length; i++) {
                            final ZoomImageView imageView = new ZoomImageView(PhotoGalleryActivity.this);
                            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT));

                            mImageViews[i] = imageView;

                            bitmap = ImageUtils.getLoacalBitmap(mBitmapList.get(i));
                            imageView.setImageBitmap(bitmap);

                        }
                        //设置Adapter
                        mViewPager.setAdapter(new MyAdapter());
                        //设置监听，主要是设置点点的背景
                        mViewPager.setOnPageChangeListener(PhotoGalleryActivity.this);

                        mViewPager.setCurrentItem(mPostion);
                    }
                });
            }
        };

        new Thread(runnableOnSeparateThread).start();

    }

    /**
     * 返回页面
     */
    private void goBack(ArrayList<String> mBitmapList) {
        this.mBitmapList = mBitmapList;
        Intent data = new Intent(PhotoGalleryActivity.this, SubmitSimpleActivity.class);
        data.putStringArrayListExtra("bmps", mBitmapList);

 /*       Bundle bundle = new Bundle();
        bundle.putStringArrayList("bmps", mBitmapList);
        intent.putExtras(bundle);*/
       /* startActivity(intent);
        this.setResult(RESULT_OK, intent);*/
        setResult(1001, data);
        finish();
    }

    //适配器
    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageViews.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
        }

        /**
         * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
         */
        @Override
        public Object instantiateItem(View container, int position) {
            try {
                ((ViewPager) container).addView(mImageViews[position], 0);

            } catch (Exception e) {
                //handler something
            }
            return mImageViews[position];
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setImageBackground(position % mImageViews.length);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 设置选中的tip的背景
     *
     * @param selectItems
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < mTips.length; i++) {
            if (i == selectItems) {
                mTips[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                mTips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
        }
    }

    /**
     * 删除图片
     */
    private void delImage() {
        int current = mViewPager.getCurrentItem();
        mBitmapList.remove(current);
        bitmap.recycle();
        if (mBitmapList.size() > 0) {
            if (current - 1 >= 0) {
                mPostion = current - 1;
            } else {
                mPostion = 0;
            }
            //删一次 回调一次
            buildImages();
            goBack(mBitmapList);
        } else {
            goBack(mBitmapList);
        }
    }
}
