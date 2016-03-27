/*
 * Copyright (c) 2014.
 * Jackrex
 */

package com.tjpld.smileapp.config.utlis.screenutils;

import android.content.Context;
import android.util.Log;

/*
* 获取屏幕分辨率
* <p> dp 和 px 之间的 转换 </p>
* */
public class DesityUtil {

	  /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        Log.e("desitiny", scale + "");
        return (int) (dpValue * scale /1.5 + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale  + 0.5f);  
    }
}
