package com.yj.layout.customui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

public class LineEditText extends androidx.appcompat.widget.AppCompatEditText {

    private Rect rect;

    private Paint paint;

    public LineEditText(Context context, AttributeSet set) {
        super(context,set);
        rect = new Rect();
        paint = new Paint();

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0x8000000F);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int count = getLineCount();
        Rect r = rect;
        Paint p = paint;
        
        for(int x = 0;x<count;x++){
            int baseLine = getLineBounds(x,r);

            canvas.drawLine(r.left,baseLine+10,r.right,baseLine+10,p);
        }

        super.onDraw(canvas);
    }
}
