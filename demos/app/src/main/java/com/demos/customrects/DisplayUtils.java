package com.demos.customrects;

import android.content.Context;

/**
 * Created by mwg on 16-5-10.
 */
public class DisplayUtils {
    /**
     * px转换成dp
     */
    public static int px2dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;

        return (int)(px / scale + 0.5f);
    }


    /**
     *dp转换成px
     */
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;

        return (int)(dp * scale + 0.5);
    }

    /**
     * px转换成dp
     */
    public static int px2sp(Context context, float px) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;

        return (int)(px /fontScale + 0.5);
    }

    /**
     * dp转换成px
     */
    public static int sp2px(Context context, float dp) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;

        return (int)(dp * fontScale + 0.5);
    }

}
