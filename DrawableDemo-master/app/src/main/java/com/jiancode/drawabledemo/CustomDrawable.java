package com.jiancode.drawabledemo;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * author：yangjian
 * email：yj@allong.net
 * data：2019-05-2823:31
 * desc：自定义Drawable
 * version：1.0
 * <p>
 *     1.必须重写 onDraw，setAlpha，setColorFilter，getOpacity这四个方法
 *     2、draw方法中实现操作
 * </p>
 */
public class CustomDrawable extends Drawable {

    private Paint mPaint;

    public CustomDrawable(int color) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
    }

    @Override
    public void draw(Canvas canvas) {
        Rect rect  = getBounds();
        float cx = rect.exactCenterX();
        float cy = rect.exactCenterX();
        canvas.drawCircle(cx,cy,Math.min(cx,cy),mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    /**
     * 添加设置颜色的方法
     * @param color
     */
    public void setColor(int color){
        mPaint.setColor(color);
        invalidateSelf();
    }
}
