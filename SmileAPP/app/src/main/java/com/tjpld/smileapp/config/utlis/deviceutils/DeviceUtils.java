package com.tjpld.smileapp.config.utlis.deviceutils;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.TypedValue;

import java.util.UUID;

/**
 * Created by nan on 2016/1/29.
 */
public class DeviceUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale / 1.5 + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public int dipToPixels(Context context, int dip) {
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(1, (float) dip, r.getDisplayMetrics());
        return (int) px;
    }

    /**
     * 锁屏
     */
    public static boolean isScreenLocked(Context context) {
        android.app.KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(context.KEYGUARD_SERVICE);
        return keyguardManager.inKeyguardRestrictedInputMode();
    }

    public static String getImieStatus(Context context) {
        String deviceId="";
        if (Build.VERSION.SDK_INT < 23) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();
        }else{
            deviceId= UUID.randomUUID().toString();
        }
        return deviceId;
    }
}
