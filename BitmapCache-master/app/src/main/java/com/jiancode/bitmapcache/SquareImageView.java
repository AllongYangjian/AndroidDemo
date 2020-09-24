package com.jiancode.bitmapcache;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * author：yangjian
 * email：yj@allong.net
 * data：2019-06-1520:35
 * desc：
 * version：1.0
 */
@SuppressLint("AppCompatCustomView")
public class SquareImageView extends ImageView {
    public SquareImageView(Context context) {
        this(context,null);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 控件的初始化操作
     * @param context
     */
    private void initView(Context context) {

    }

    /**
     * 重写onMeasure方法，实现宽高相等
     * @param widthMeasureSpec {@link android.view.View.MeasureSpec}
     * @param heightMeasureSpec {@link android.view.View.MeasureSpec}
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
