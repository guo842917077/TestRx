package com.tjpld.yuanhangrxjava;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.tjpld.yuanhangrxjava.config.utlis.commonutils.ToastUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;

/**
 * 封装一些常用的方法
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected ProgressDialog progressDialog;
    final Calendar mCalendar = Calendar.getInstance();
    //AlertView的控件
    private AlertDialog mAlertDialog;//提示对话框
    private TextView mTv_alertMessage, mTv_alertNegaetive, mTv_alertPostive;
    int titleString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long time = System.currentTimeMillis();
        mCalendar.setTimeInMillis(time);
        initProgressDialog();
        initAlertDialog(titleString);
        setMiuiStatusBarDarkMode(this, true);
    }

    private void initProgressDialog() {
        //创建ProgressDialog对象
        progressDialog = new ProgressDialog(this);
        // 设置进度条风格，风格为圆形，旋转的
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // 设置ProgressDialog 的进度条是否不明确
        progressDialog.setIndeterminate(false);
    }

    private void initAlertDialog(int title) {
        mAlertDialog = new AlertDialog.Builder(this).create();
        View view = LayoutInflater.from(this).inflate(R.layout.layout_hiddenalert, null);
        mAlertDialog.setView(view);
        mTv_alertMessage = (TextView) view.findViewById(R.id.tv_hiddenalert);
        mTv_alertNegaetive = (TextView) view.findViewById(R.id.tv_negative);
        mTv_alertPostive = (TextView) view.findViewById(R.id.tv_postive);
        mTv_alertNegaetive.setOnClickListener(this);//关闭警告框
    }

    //得到dialog的确认事件
    protected TextView commitDiaolgListener() {
        return mTv_alertPostive;
    }

    //得到警告框
    protected AlertDialog getAlertDialog(int title) {
        this.titleString = title;
        mTv_alertMessage.setText(title);
        return mAlertDialog;
    }

    protected void dismissDialog() {
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
        }
    }

    /**
     * @param msg      进度条上展示的信息
     * @param isCancel 是否可以取消
     */
    protected void showProgress(String msg, boolean isCancel) {
        // 设置ProgressDialog 是否可以按退回按键取消
        progressDialog.setCancelable(isCancel);
        //设置进度条提示信息
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    ;

    protected void cancelProgress() {
        progressDialog.dismiss();
    }

    /**
     * @param tarActivity 跳转到指定界面
     */
    protected void intent2Activity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }

    /**
     * @param msg 内容
     */
    protected void showToast(String msg) {
        ToastUtils.show(this, msg, Toast.LENGTH_SHORT);
    }

    /**
     * 得到系统当前时间
     */
    public int getCurrentHour() {
        final int mHour = mCalendar.get(Calendar.HOUR);
        return mHour;
    }

    public int getCurrentYear() {
        final int mYear = mCalendar.get(Calendar.YEAR);
        return mYear;
    }

    public int getCurrentMonth() {
        final int mMonth = mCalendar.get(Calendar.MONTH) + 1;
        return mMonth;
    }

    public int getCurrentMinuts() {
        int mMinuts = mCalendar.get(Calendar.MINUTE);
        return mMinuts;
    }

    public int getCurrentDate() {
        final int mDate = mCalendar.get(Calendar.MILLISECOND);
        return mDate;
    }

    public int getCurrentSecond() {
        final int second = mCalendar.get(Calendar.SECOND);
        return second;
    }

    public static boolean setMiuiStatusBarDarkMode(Activity activity, boolean darkmode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_negative:
                mAlertDialog.dismiss();
                break;
        }
    }
}
