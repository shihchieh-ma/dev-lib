package dev.majes.base.utils;

import android.content.Context;

/**
 * dp,px转换工具类
 * @author majes
 * @date 11/30/17.
 */

public class DPXUtils {
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
