package org.example.yj.customview.common;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author yj on  2018/5/16 18:12
 *         邮箱 yj@allong.net
 * @version 1.0.0
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public MyItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = space;
    }
}
