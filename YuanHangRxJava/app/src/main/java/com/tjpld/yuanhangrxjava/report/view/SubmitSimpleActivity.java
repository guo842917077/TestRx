package com.tjpld.yuanhangrxjava.report.view;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tjpld.yuanhangrxjava.BaseActivity;
import com.tjpld.yuanhangrxjava.R;
import com.tjpld.yuanhangrxjava.config.utlis.commonutils.ToastUtils;
import com.tjpld.yuanhangrxjava.config.utlis.fileutils.FileUtils;
import com.tjpld.yuanhangrxjava.config.utlis.imageutils.ImageUtils;
import com.tjpld.yuanhangrxjava.config.utlis.versionutils.VersionUtils;
import com.tjpld.yuanhangrxjava.config.widget.PhotoGalleryActivity;
import com.tjpld.yuanhangrxjava.config.widget.SelectPicPopupWindow;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 简单方式的提交
 */
public class SubmitSimpleActivity extends BaseActivity implements View.OnClickListener {
    //-------------------toolbar-----------------
    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    @Bind(R.id.toolbar_title)
    public TextView toolbar_title;
    @Bind(R.id.toolbar_rightTv)
    public TextView toolbar_rightTv;
    @Bind(R.id.toolbar_leftTv)
    public TextView toolbar_leftTv;
    @Bind(R.id.img_lvtoolbar)
    public ImageButton img_leftbtn;
    //-----------------文字框 和图片框 添加图片按钮---------
    @Bind(R.id.et_commit_content)
    public EditText et_commit_content;
    //-------------------------相机参数-----------------------
    //点击图片拍照
    @Bind(R.id.btn_checkpro_add_img)
    public ImageButton btn_checkpro_add_img;
    //图片选择框
    private SelectPicPopupWindow menuWindow;
    @Bind(R.id.imgbtn_photo)
    public ImageButton imgbtn_photo;
    //相机参数
    private static final int CAMERA_TAKE = 1;
    private static final int CAMERA_SELECT = 2;
    private static final int CAMERA_SELECT_KITKAT = 3;
    private ArrayList<String> mBitmapList = new ArrayList<String>();//显示图片的地址
    private LinearLayout mLinearLayout;
    Intent data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simplesubmit);
        ButterKnife.bind(this);
        initToolbar();
        initEvent();
    }

    private void initEvent() {
        imgbtn_photo.setOnClickListener(this);//选择图片
    }


    private void initToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar_leftTv.setText("隐患上报");
        toolbar_leftTv.setVisibility(View.VISIBLE);
        toolbar_rightTv.setText("提交");
        img_leftbtn.setBackgroundResource(R.drawable.ic_action_back);
        img_leftbtn.setVisibility(View.VISIBLE);
        toolbar_rightTv.setVisibility(View.VISIBLE);
        toolbar_rightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* commitData();*/
            }
        });
        img_leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 提交内容到表单
     */
/*    private void commitData() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(APPConfig.Share_Login, MODE_PRIVATE);
        showProgress("正在提交，请稍后......", true);
        RequestBody requestBody = null;
        MultipartBuilder builder = new MultipartBuilder();
        String commitContent = et_commit_content.getText().toString();
        builder.type(MultipartBuilder.FORM);
        try {
            for (int i = 0; i < mBitmapList.size(); i++) {
                String picName = i + ".png";
                builder.addFormDataPart("file", picName, RequestBody.create(MediaType.parse("application/octet-stream"), new File(mBitmapList.get(i))));
            }
            requestBody = builder.addFormDataPart("description", commitContent)
                    .addFormDataPart("personId", sharedPreferences.getString(UserModel.SP_ID, ""))
                    .addFormDataPart("type", "0")
                    .build();
            Type type = new TypeToken<Object>() {
            }.getType();
            OkHttpUtil.getInstance().PostMultipart(AppUrl.HiddenReport, requestBody, type, new OkHttpCallback<Object>() {
                Handler mainHandler = new Handler(getMainLooper());

                @Override
                public void onSuccess(Object result) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    cancelProgress();
                                    finish();
                                }
                            });
                        }
                    });
                }

                @Override
                public void onFailture(final String message) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            //使用toast信息提示框提示错误信息
                            Toast.makeText(SubmitSimpleActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    //拍照事件
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(
                    InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_take_photo:
                    if (mBitmapList.size() == 3)
                        ToastUtils.show(SubmitSimpleActivity.this, "最多选择3张照片", 2000);
                    else
                        takePhoto();
                    break;
                case R.id.btn_pick_photo:
                    if (mBitmapList.size() == 3)
                        ToastUtils.show(SubmitSimpleActivity.this, "最多选择3张照片", 2000);
                    else
                        selectPhoto();
                    break;
                default:
                    break;
            }
        }
    };

    //拍照
    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = FileUtils.createFile(Environment.getExternalStorageDirectory() + "/YuanHang/", "temp.png");
        Uri uri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, CAMERA_TAKE);
    }

    //选择照片
    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        //大于4.4版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            startActivityForResult(intent, CAMERA_SELECT_KITKAT);
        else
            startActivityForResult(intent, CAMERA_SELECT);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
      /*      case R.id.imgbtn_depart:
                Intent intent = new Intent(SubmitSimpleActivity.this, OrganizationActivity.class);
                startActivityForResult(intent, MessageCantants.RequestOrganzationCode);
                break;*/
            case R.id.imgbtn_photo:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(
                        InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                //弹出window前 先将输入框关闭
                menuWindow = new SelectPicPopupWindow(SubmitSimpleActivity.this, itemsOnClick);
                //显示窗口的位置
                menuWindow.showAtLocation(SubmitSimpleActivity.this.findViewById(R.id.line_pop), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 40); //设置layout在PopupWindow中显示的位
                InputMethodManager imm2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(
                        InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                break;
        }
    }

    //刷新图片
    private void buildImages() {
        final Handler handler = new Handler();
        Runnable runnableOnSeparateThread = new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mLinearLayout.removeAllViews();
                        for (int i = 0; i < mBitmapList.size(); i++) {
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp.setMargins(6, 0, 0, 0);
                            lp.height = btn_checkpro_add_img.getHeight();
                            lp.width = btn_checkpro_add_img.getWidth();
                            ImageView imageView = new ImageView(SubmitSimpleActivity.this);
                            imageView.setLayoutParams(lp);
                            //---------------bitmap-----------
                            Bitmap bp = BitmapFactory.decodeFile(mBitmapList.get(i));
                            //对图片进行剪裁
                            imageView.setImageBitmap(ImageUtils.ImageCropWithZoom(bp, 540, true));
                            final int current = i;
                            //imgview的点击事件 点击图片到一个Gallery可以选择删除图片，传入的是图片的路径
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(SubmitSimpleActivity.this, PhotoGalleryActivity.class);
                                    intent.putStringArrayListExtra("bmps", mBitmapList);
                                    intent.putExtra("positions", current);
                                    //1000代表从Gallery带过来的值 requestCode==1000
                                    startActivityForResult(intent, 1000);
                                }
                            });
                            mLinearLayout.addView(imageView);
                        }
                        if (mBitmapList.size() >= 3) {
                            btn_checkpro_add_img.setVisibility(View.GONE);
                        } else {
                            btn_checkpro_add_img.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        };
        new Thread(runnableOnSeparateThread).start();
    }

    //获得从相册和相机带来的照片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //删除照片时做的操作
        if (requestCode == 1000 && resultCode == 1001) {
            final Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<String> galleyArray = data.getStringArrayListExtra("bmps");
                            mBitmapList = galleyArray;
                            buildImages();
                        }
                    });
                }
            };
            new Thread(runnable).start();
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_TAKE: {
                    final Handler handler = new Handler();
                    Runnable runnableOnSeparateThread = new Runnable() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        //取得本地图片
                                        String dir = Environment.getExternalStorageDirectory() + "/YuanHang/ResultPhotos/";//文件夹路径
                                        String filename = "YuanHang" + "_" + getCurrentYear() + "" + getCurrentMonth() + "" + getCurrentDate() + "" + getCurrentHour() + "" + getCurrentMinuts() + "" + mBitmapList.size() + ".png";//照片名称
                                        String fileUrl = dir + filename;
                                        String filepath = Environment.getExternalStorageDirectory() + "/YuanHang/" + "temp.png";
                                        Bitmap bitmap = ImageUtils.compressBySampleSize(filepath, 720, 1280);
                                        //保存经过压缩的图片
                                        FileUtils.saveBitmap(dir, filename, bitmap, 100);
                                        /**
                                         * 显示缩略图的时候对bitmap对象进行剪裁
                                         */
                                        mBitmapList.add(fileUrl);
                                        buildImages();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    };
                    new Thread(runnableOnSeparateThread).start();
                }
                break;

                case CAMERA_SELECT: {
                    final Handler handler = new Handler();
                    this.data = data;
                    Runnable runnableOnSeparateThread = new Runnable() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        //保存图片到本地
                                        Uri uri = SubmitSimpleActivity.this.data.getData();
                                        String path = "";
                                        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                                        if (cursor != null && cursor.moveToFirst()) {
                                            path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                                        } else {
                                            return;
                                        }
                                        String dir = Environment.getExternalStorageDirectory() + "/YuanHang/ResultPhotos/";
                                        String filename = "YuanHang" + "_" + getCurrentYear() + "" + getCurrentMonth() + "" + getCurrentDate() + "" + getCurrentHour() + "" + getCurrentMinuts() + "" + mBitmapList.size() + ".png";
                                        String fileUrl = dir + filename;
                                        //对原图进行压缩
                                        Bitmap bitmap = ImageUtils.compressBySampleSize(path, 720, 1280);
                                        //保存图片
                                        FileUtils.saveBitmap(dir, filename, bitmap, 100);
                                        Log.e("tag", "save is success");
                                        //添加图片
                                        mBitmapList.add(fileUrl);
                                        buildImages();

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        }
                    };
                    new Thread(runnableOnSeparateThread).start();
                }
                break;
                case CAMERA_SELECT_KITKAT:
                    this.data = data;
                    final Handler handler = new Handler(getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Uri uri = data.getData();
                            String path = VersionUtils.getPathByUri(SubmitSimpleActivity.this, uri);
                            String dir = Environment.getExternalStorageDirectory() + "/YuanHang/ResultPhotos/";
                            String filename = "YuanHang" + "_" + getCurrentYear() + "" + getCurrentMonth() + "" + getCurrentDate() + "" + getCurrentHour() + "" + getCurrentMinuts() + "" + mBitmapList.size() + ".png";
                            String fileUrl = dir + filename;
                            //对原图进行压缩
                            Bitmap bitmap = ImageUtils.compressBySampleSize(path, 720, 1280);
                            //保存图片
                            FileUtils.saveBitmap(dir, filename, bitmap, 100);
                            Log.e("tag", "save is success");
                            //添加图片
                            mBitmapList.add(fileUrl);
                            buildImages();
                        }
                    });
                    break;
            }
        }
    }
}
