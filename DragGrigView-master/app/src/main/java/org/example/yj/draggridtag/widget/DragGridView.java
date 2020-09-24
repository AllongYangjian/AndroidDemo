package org.example.yj.draggridtag.widget;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.example.yj.draggridtag.R;
import org.example.yj.draggridtag.adapter.DragGridAdapter;

public class DragGridView extends GridView {
    private static final String TAG = DragGridView.class.getSimpleName();
    //定义基本的成员变量
    private TextView dragItemView;
    private int dragSrcPosition;
    private int dragPosition;
    //x,y坐标的计算
    private int dragPointX;
    private int dragPointY;
    private int dragOffsetX;
    private int dragOffsetY;

    private WindowManager windowManager;
    private WindowManager.LayoutParams windowParams;

    private int upScrollBounce;
    private int downScrollBounce;

    public DragGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(ev.getAction()==MotionEvent.ACTION_DOWN){
            //getX()，getY()以该view的左上角为坐标原点
            //getRawX(),getRawY()以屏幕的左上角为坐标原点
//            event.getX():表示的是触摸的点距离自身左边界的距离
//            event.getY():表示的是触摸的点距离自身上边界的距离
//            event.getRawX:表示的是触摸点距离屏幕左边界的距离
//            event.getRawY:表示的是触摸点距离屏幕上边界的距离

//            View.getTop():子View的顶部到父View顶部的距离
//            View.getRight():子View的右边界到父View的左边界的距离
//            View.getBottom():子View的底部到父View的顶部的距离
//            View.getLeft():子View的左边界到父View的左边界的距离

            int x = (int)ev.getX();
            int y = (int)ev.getY();
            Log.e(TAG,x+"---"+y+"---");

            dragSrcPosition = dragPosition = pointToPosition(x, y);
            if(dragPosition== AdapterView.INVALID_POSITION){
                return super.onInterceptTouchEvent(ev);
            }

            ViewGroup itemView = (ViewGroup) getChildAt(dragPosition-getFirstVisiblePosition());//子view
            Log.e(TAG,itemView.getLeft()+"---"+itemView.getTop()+"---");//
            dragPointX = x - itemView.getLeft();
            dragPointY = y - itemView.getTop();
            dragOffsetX = (int) (ev.getRawX() - x);
            dragOffsetY = (int) (ev.getRawY() - y);
            Log.e(TAG,ev.getRawX()+"--------"+ev.getRawY());
            Log.e(TAG,dragPointX+"--"+dragPointY+"---"+dragOffsetX+"---"+dragOffsetY);

            TextView dragger = (TextView)itemView.findViewById(R.id.drag_grid_item);
            Log.e(TAG,dragger.getLeft()+","+dragger.getRight()+","+dragger.getTop()+","+dragger.getBottom());
            //如果选中拖动图标
            if(dragger!=null&&dragPointX>dragger.getLeft()&&dragPointX<dragger.getRight()&&dragPointY>dragger.getTop()&&dragPointY<dragger.getBottom()+20){

                upScrollBounce = Math.min(y, getHeight()/4);
                downScrollBounce = Math.max(y, getHeight()*3/4);
                Log.e(TAG,upScrollBounce+"ssssssssssssss"+downScrollBounce+"^^^^^^^^^^"+getHeight());
                itemView.setDrawingCacheEnabled(true);

                //Bitmap bm = Bitmap.createBitmap(itemView.getDrawingCache());
                startDrag(dragger.getText().toString(), x, y);
            }
            return false;
        }
        Log.e(TAG,super.onInterceptTouchEvent(ev)+"");
        return super.onInterceptTouchEvent(ev);
    }

    public void startDrag(String text, int x, int y){
        stopDrag();
        //windParams.x windParams.y是相对屏幕上右边界而言的
        windowParams = new WindowManager.LayoutParams();
        windowParams.gravity = Gravity.TOP|Gravity.LEFT;
        windowParams.x = x - dragPointX + dragOffsetX;
        windowParams.y = y - dragPointY + dragOffsetY;
        windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        windowParams.format = PixelFormat.TRANSLUCENT;
        windowParams.windowAnimations = 0;
        Log.e(TAG,windowParams.x+"##########"+windowParams.y);
        TextView textView = new TextView(getContext());
        textView.setText(text);
        windowManager = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(textView, windowParams);
        dragItemView = textView;
    }

    public void onDrag(int x, int y){
        if(dragItemView!=null){
            windowParams.alpha = 0.8f;
            windowParams.x = x - dragPointX + dragOffsetX;
            windowParams.y = y - dragPointY + dragOffsetY;
            windowManager.updateViewLayout(dragItemView, windowParams);
        }

        int tempPosition = pointToPosition(x, y);
        if(tempPosition!=INVALID_POSITION){
            dragPosition = tempPosition;
        }

        //滚动
        if(y<upScrollBounce||y>downScrollBounce){
            //使用setSelection来实现滚动
            setSelection(dragPosition);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(dragItemView!=null&&dragPosition!=INVALID_POSITION){
            int action = ev.getAction();
            switch(action){
                case MotionEvent.ACTION_UP:
                    int upX = (int)ev.getX();
                    int upY = (int)ev.getY();
                    stopDrag( );
                    onDrop(upX,upY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    int moveX = (int)ev.getX();
                    int moveY = (int)ev.getY();
                    onDrag(moveX,moveY);
                    break;
                default:break;
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 停止拖动，去除拖动项的头像
     */
    public void stopDrag(){
        if(dragItemView!=null){
            windowManager.removeView(dragItemView);
            dragItemView = null;
        }
    }

    public void onDrop(int x, int y){

        //为了避免滑动到分割线的时候，返回-1的问题
        int tempPosition = pointToPosition(x, y);
        if(tempPosition!=INVALID_POSITION){
            dragPosition = tempPosition;
        }

        //超出边界处理
        if(y<getChildAt(0).getTop()){
            //超出上边界
            dragPosition = 0;
        }else if(y>getChildAt(getChildCount()-1).getBottom()||
                (y>getChildAt(getChildCount()-1).getTop()&&x>getChildAt(getChildCount()-1).getRight())){
            //超出下边界
            dragPosition = getAdapter().getCount()-1;
        }

        //数据交换
        if(dragPosition!=dragSrcPosition&&dragPosition>-1&&dragPosition<getAdapter().getCount()){
            DragGridAdapter adapter = (DragGridAdapter)getAdapter();
            String dragItem = adapter.getItem(dragSrcPosition);
            adapter.remove(dragItem);
            adapter.insert(dragItem, dragPosition);
            Toast.makeText(getContext(), adapter.getList().toString(), Toast.LENGTH_SHORT).show();
        }

    }
}