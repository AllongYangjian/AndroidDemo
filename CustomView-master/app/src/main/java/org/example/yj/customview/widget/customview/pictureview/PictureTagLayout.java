package org.example.yj.customview.widget.customview.pictureview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * @author yj on  2018/5/30 15:02
 * 邮箱 yj@allong.net
 * @version 1.0.0
 */
public class PictureTagLayout extends RelativeLayout implements View.OnTouchListener {

    private static final String TAG = PictureTagLayout.class.getSimpleName();

    private static final int CLICKRANGE = 5;
    int startX = 0;
    int startY = 0;
    int startTouchViewLeft = 0;
    int startTouchViewTop = 0;

    private View touchView, clickView;


    public PictureTagLayout(Context context) {
        this(context, null);
    }

    public PictureTagLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PictureTagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchView = null;
                if (clickView != null) {
                    ((PictureTagView) clickView).setStatus(PictureTagView.Status.Normal);
                    clickView = null;
                }
                startX = (int) event.getX();
                startY = (int) event.getY();
                if (hasView(startX, startY)) {
                    startTouchViewLeft = touchView.getLeft();
                    startTouchViewTop = touchView.getTop();
                } else {
                    addItem(startX, startY);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                moveView((int) event.getX(), (int) event.getY());
                break;
            case MotionEvent.ACTION_UP:
                int endX = (int) event.getRawX();
                int entY = (int) event.getRawY();
                if (touchView != null && Math.abs(endX - startX) < CLICKRANGE
                        && Math.abs(entY - startY) < CLICKRANGE) {
                    ((PictureTagView) touchView).setStatus(PictureTagView.Status.Edit);
                    clickView = touchView;
                }
                touchView = null;
                break;
        }

        return true;
    }

    private void moveView(int x, int y) {
        if (touchView == null) {
            return;
        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = x - startX + startTouchViewLeft;
        params.topMargin = y - startY + startTouchViewTop;
        if (params.leftMargin < 0 || (params.leftMargin + touchView.getWidth()) > getWidth())
            params.leftMargin = touchView.getLeft();
        if (params.topMargin < 0 || (params.topMargin + touchView.getHeight()) > getHeight())
            params.topMargin = touchView.getTop();
        touchView.setLayoutParams(params);
    }


    private boolean hasView(int x, int y) {
        for (int index = 0; index < this.getChildCount(); index++) {
            View view = getChildAt(index);
            int left = (int) view.getX();
            int top = (int) view.getY();
            int right = view.getRight();
            int bottom = view.getBottom();
            Rect rect = new Rect(left, top, right, bottom);
            boolean contains = rect.contains(x, y);
            if (contains) {
                touchView = view;
                touchView.bringToFront();
                return true;
            }
        }
        touchView = null;
        return false;
    }

    private void addItem(int x, int y) {
        View view = null;
        Log.e(TAG, x + " ," + y);
        Log.e(TAG, getMeasuredWidth() + "");
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (x > getMeasuredWidth() * 0.5) {
            params.leftMargin = x - PictureTagView.getViewWidth();
            view = new PictureTagView(getContext(), PictureTagView.Direction.Right);
        } else {
            params.leftMargin = x;
            view = new PictureTagView(getContext(), PictureTagView.Direction.Left);
        }
        params.topMargin = y;
        if (params.topMargin < 0) {
            params.topMargin = 0;
        } else if (params.topMargin + PictureTagView.getViewHeight() > getHeight()) {
            params.topMargin = getHeight() - PictureTagView.getViewHeight();
        }
        this.addView(view, params);

    }
}
