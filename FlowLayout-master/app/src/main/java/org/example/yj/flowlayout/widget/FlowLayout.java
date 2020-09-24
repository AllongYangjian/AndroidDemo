package org.example.yj.flowlayout.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author yj on  2018/4/10 21:55
 *         邮箱 yj@allong.net
 * @version 1.0.0
 */

public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int childCount = getChildCount();

        int lineWidth = 0;//对比项

        int viewWidth = 0;
        int viewHeight = 0;

        for (int x = 0; x < childCount; x++) {
            View childView = getChildAt(x);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();
            int childViewHeight = childView.getMeasuredHeight() + params.leftMargin + params.rightMargin;
            int childViewWidth = childView.getMeasuredWidth() + params.topMargin + params.bottomMargin;

            if (lineWidth + childViewWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                viewWidth = Math.max(viewWidth, lineWidth);//将当前行宽和上次行宽进行对比，最大值
                lineWidth = childViewWidth;//重置行宽
                viewHeight += childViewHeight;//当前view宽度为上次view宽度+行高
            } else {
                lineWidth += childViewWidth;//同一行宽度叠加
                viewHeight = Math.max(childViewHeight, viewHeight);//当前view宽度为上次view宽度+行高
            }
        }

        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : viewWidth + getPaddingLeft() + getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : viewHeight + getPaddingTop() + getPaddingBottom()//
        );

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = getWidth();
        int childCount = getChildCount();
        int left = getPaddingLeft();
        int top = getPaddingTop();

        for (int x = 0; x < childCount; x++) {

            View childView = getChildAt(x);

            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();
            //viewWidth - paddingLeft-paddingRight 是实际的宽度，由于起点从left开始计算，所以判断是否超出范围是right比较
            if (left + childWidth + params.leftMargin + params.rightMargin > width  - getPaddingRight()) {//由于view的起始点是paddingLeft，所以只需要比较
                top += params.topMargin + childHeight + params.bottomMargin;//下一次top为开始位置 本次top+控件实际占位(viewHeight+topMargin+bottonMargin)
                left = getPaddingLeft();
                int ll = left + params.leftMargin;
                int lt = top + params.topMargin;
                int lr = ll + childWidth;
                int lb = lt + childHeight;
                childView.layout(ll, lt, lr, lb);
                left += childWidth + params.leftMargin + params.rightMargin;
            } else {
                int ll = left + params.leftMargin;
                int lt = top + params.topMargin;
                int lr = ll + childWidth;
                int lb = lt + childHeight;
                childView.layout(ll, lt, lr, lb);
                left += childWidth + params.leftMargin + params.rightMargin;//下一次left开始位置当前位置+控件实际占的位置（viewWidth +leftMargin+rightMargin）
            }
//            left += childWidth + params.leftMargin + params.rightMargin;

        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
