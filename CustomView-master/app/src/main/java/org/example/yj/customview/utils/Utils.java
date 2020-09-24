package org.example.yj.customview.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * @author yj on  2018/5/23 15:37
 *         邮箱 yj@allong.net
 * @version 1.0.0
 */

public class Utils {
    public static int dp2px(Context context,float dipValue){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dipValue,metrics);
    }

    public static int sp2px(Context context,float spValue){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,spValue,metrics);

    }
}
