package com.tjpld.smileapp.config.utlis.commonutils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * 动画工具类
 * 内容来自@see http://www.cnblogs.com/wondertwo/p/5312482.html 哈皮小猿的博客
 */
public class AnimatorUtils {
    private int mCurrentRed = -1;
    private int mCurrentGreen = -1;
    private int mCurrentBlue = -1;

    /**
     *  实现按钮缩放的同时变换颜色
     */
    public boolean animScaleWithColor(final View target, final String start, final String end) {
        boolean isComplete = false;//是否完成
        final float[] value = {0};
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f, 100f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();//获取当前动画的进度
                Log.d("当前动画值", "current value : " + currentValue);
                float fraction = animation.getAnimatedFraction();//获取当前动画流失的百分比
                Log.d("当前动画值", "fraction value : " + fraction);
                //通过百分比计算颜色
                String colorResult = evaluateForColor(fraction, start, end);
                //通过ColorDrawable对象给控件赋值 改变它的颜色
                ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor(colorResult));
                target.setBackground(colorDrawable);
                value[0] = currentValue;
                target.invalidate();
            }
        });

        valueAnimator.setDuration(3 * 1000);//设置持续的时间
        /**
         * 加入缩放动画
         */
        // 组装缩放动画
        ValueAnimator animator_1 = ObjectAnimator.ofFloat(target, "scaleX", 1f, 0.5f);
        ValueAnimator animator_2 = ObjectAnimator.ofFloat(target, "scaleY", 1f, 0.5f);
        ValueAnimator animator_3 = ObjectAnimator.ofFloat(target, "scaleX", 0.5f, 1f);
        ValueAnimator animator_4 = ObjectAnimator.ofFloat(target, "scaleY", 0.5f, 1f);
        //使用动画集合将连续的动画组装到一起
        AnimatorSet set1 = new AnimatorSet();//将缩放动画1和2组装到一起
        set1.play(animator_1).with(animator_2);
        AnimatorSet set2 = new AnimatorSet();//将缩放动画3和4组装到一起
        set2.play(animator_3).with(animator_4);
        AnimatorSet set3 = new AnimatorSet();
        set3.play(set1).with(set2);//将动画集合组装到一起
        //将动画和颜色变换的效果放到一起
        AnimatorSet set4 = new AnimatorSet();
        set4.play(set3).with(valueAnimator);
        set4.start();
        if (value[0] == 100) {
            isComplete = true;
        }
        return isComplete;
    }

    /**
     * evaluateForColor()计算颜色值并返回
     *
     * @param fraction   流失的百分比
     * @param startValue 起始值
     * @param endValue   结束值
     */
    private String evaluateForColor(float fraction, String startValue, String endValue) {
        String startColor = startValue;
        String endColor = endValue;
        int startRed = Integer.parseInt(startColor.substring(1, 3), 16);
        int startGreen = Integer.parseInt(startColor.substring(3, 5), 16);
        int startBlue = Integer.parseInt(startColor.substring(5, 7), 16);
        int endRed = Integer.parseInt(endColor.substring(1, 3), 16);
        int endGreen = Integer.parseInt(endColor.substring(3, 5), 16);
        int endBlue = Integer.parseInt(endColor.substring(5, 7), 16);

        // 初始化颜色的值
        if (mCurrentRed == -1) {
            mCurrentRed = startRed;
        }
        if (mCurrentGreen == -1) {
            mCurrentGreen = startGreen;
        }
        if (mCurrentBlue == -1) {
            mCurrentBlue = startBlue;
        }

        // 计算初始颜色和结束颜色之间的差值
        int redDiff = Math.abs(startRed - endRed);
        int greenDiff = Math.abs(startGreen - endGreen);
        int blueDiff = Math.abs(startBlue - endBlue);
        int colorDiff = redDiff + greenDiff + blueDiff;
        if (mCurrentRed != endRed) {
            mCurrentRed = getCurrentColor(startRed, endRed, colorDiff, 0, fraction);
        } else if (mCurrentGreen != endGreen) {
            mCurrentGreen = getCurrentColor(startGreen, endGreen, colorDiff, redDiff, fraction);
        } else if (mCurrentBlue != endBlue) {
            mCurrentBlue = getCurrentColor(startBlue, endBlue, colorDiff,
                    redDiff + greenDiff, fraction);
        }

        // 将计算出的当前颜色的值组装返回
        String currentColor = "#" + getHexString(mCurrentRed)
                + getHexString(mCurrentGreen) + getHexString(mCurrentBlue);
        return currentColor;
    }

    /**
     * 根据fraction值来计算当前的颜色。
     */
    private int getCurrentColor(int startColor, int endColor, int colorDiff,
                                int offset, float fraction) {
        int currentColor;
        if (startColor > endColor) {
            currentColor = (int) (startColor - (fraction * colorDiff - offset));
            if (currentColor < endColor) {
                currentColor = endColor;
            }
        } else {
            currentColor = (int) (startColor + (fraction * colorDiff - offset));
            if (currentColor > endColor) {
                currentColor = endColor;
            }
        }
        return currentColor;
    }

    /**
     * 将10进制颜色值转换成16进制。
     */
    private String getHexString(int value) {
        String hexString = Integer.toHexString(value);
        if (hexString.length() == 1) {
            hexString = "0" + hexString;
        }
        return hexString;
    }

    /**
     * 参考Topeka APP的动画效果
     * @param view  指定放大的控件
     * @param interpolator  指定插值器
     */
    public void startScaleLargerAnimator(final View view, final Interpolator interpolator){
        ViewCompat.animate(view)
                .scaleX(1.1f)
                .scaleY(1.1f)
                .alpha(1)
                .setInterpolator(interpolator)
                .setStartDelay(300)
                .setDuration(1000)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        ViewCompat.animate(view)
                                .scaleX(1)
                                .scaleY(1)
                                .alpha(1)
                                .setInterpolator(interpolator)
                                .setStartDelay(300)
                                .setDuration(1000)
                                .start();
                    }
                })
                .start();
    }
    public void startRotationLargerAnimator(final View view, final Interpolator interpolator){
        ViewCompat.animate(view)
                .rotation(360)
                .alpha(1)
                .setInterpolator(interpolator)
                .setStartDelay(300)
                .setDuration(2000)
                .start();
    }
}
