package com.yj.app.view.layoutcomponent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yj.app.R;
import com.yj.app.databinding.ActivityDragBinding;

/**
 * 拖放界面
 */
public class DragActivity extends AppCompatActivity {
    private ActivityDragBinding binding;
    private final String TAG = DragActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_drag);
        setSupportActionBar(binding.toolbar);
        binding.btnDrag.setTag(TAG);
        binding.btnDrag.setOnLongClickListener(v -> {

            ClipData.Item item = new ClipData.Item(TAG);
            ClipData data = new ClipData(TAG, new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN},
                    item);
            View.DragShadowBuilder builder = new MyDragShadowBuilder(binding.btnDrag);
            v.startDrag(data, builder, null, 0);

            return true;
        });

        binding.etTarget.setOnDragListener(new MyDragEventListener(this));
    }

    private static class MyDragShadowBuilder extends View.DragShadowBuilder {
        private static Drawable drawable;

        public MyDragShadowBuilder(View view) {
            super(view);
            drawable = new ColorDrawable(Color.LTGRAY);
        }

        @Override
        public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
            int width, height;
            width = getView().getWidth() / 2;
            height = getView().getHeight() / 2;
            drawable.setBounds(0, 0, width, height);
            outShadowSize.set(width, height);
            outShadowTouchPoint.set(width / 2, height / 2);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            drawable.draw(canvas);
        }
    }

    private static class MyDragEventListener implements View.OnDragListener {
        private Context context;

        public MyDragEventListener(Context context) {
            this.context = context;
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {
            final int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.e("TAG","ACTION_DRAG_STARTED");
                    if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        v.setBackgroundColor(Color.BLUE);
//                        v.invalidate();
                    }
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.e("TAG","ACTION_DRAG_ENTERED");
                    v.setBackgroundColor(Color.GREEN);
//                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    Log.e("TAG","ACTION_DRAG_LOCATION");
                    v.setBackgroundColor(Color.CYAN);
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.e("TAG","ACTION_DRAG_EXITED");
                    v.setBackgroundResource(R.color.colorAccent);
//                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DROP:
                    Log.e("TAG","ACTION_DROP");
                    ClipData.Item item = event.getClipData().getItemAt(0);
                    String text = item.getText().toString();
                    v.setBackgroundResource(R.color.colorAccent);
                    TextView textView = (TextView) v;
                    textView.setText(text);
//                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.e("TAG","ACTION_DRAG_ENDED");
                    if (event.getResult()) {
                        Toast.makeText(context, "拖放成功", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "拖放失败", Toast.LENGTH_LONG).show();
                    }
                    return true;

            }

            return false;
        }
    }
}
