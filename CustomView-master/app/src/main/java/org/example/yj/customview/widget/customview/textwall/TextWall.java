package org.example.yj.customview.widget.customview.textwall;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.example.yj.customview.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * @author yj on  2018/5/16 17:03
 *         邮箱 yj@allong.net
 * @version 1.0.0
 *          <p>
 *          文字墙控件,继承自FrameLayout
 *          </p>
 */

public class TextWall extends FrameLayout implements ViewTreeObserver.OnGlobalLayoutListener {

    private int[] colors = {R.color.red, R.color.orange, R.color.yellow, R.color.green, R.color.cyan, R.color.blue, R.color.purple};
    private int mWidth;
    private int mHeight;
    private Random mRandom;

    private int mCount;
    private ScaleAnimation mScaleAnimation;//缩放动画
    private int mAnimation_index;

    public TextWall(@NonNull Context context) {
        this(context, null);
    }

    public TextWall(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextWall(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context ct) {
        mRandom = new Random();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);//API 16及以上
    }

    //布局变化回调该方法
    @Override
    public void onGlobalLayout() {

    }

    public void setData(List<TextItem> items, final Context context) {
        if (items == null || items.size() <= 0)
            return;
        mCount = items.size();
        items = sortTextItem(items);

        //字体大小排序
        int[] frontSizes = generateFrontSize(items.size());

        for (int x = 0; x < mCount; x++) {
            TextItem textItem = items.get(x);
            textItem.setFrontSize(frontSizes[x]);
            textItem.setFrontColor(colors[mRandom.nextInt(colors.length)]);
            items.set(x, textItem);
        }

        for (int x = mCount - 1; x >= 0; x--) {
            TextView textView = new TextView(context);
            textView.setText(items.get(x).getValue());
            textView.setTextSize(items.get(x).getFrontSize());
            textView.setTextColor(context.getResources().getColor(items.get(x).getFrontColor()));

            int b = mRandom.nextInt(2);
            if (b == 1) {
                textView.setSingleLine(true);
            } else {
                textView.setEms(1);
            }

            textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
            textView.setTag(items.get(x));
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, ((TextItem) v.getTag()).getValue(), Toast.LENGTH_SHORT).show();
                }
            });
            addView(textView);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            FrameLayout.MarginLayoutParams marginLayoutParams;
            //获取view的margin设置参数
            if (params instanceof ViewGroup.MarginLayoutParams) {
                marginLayoutParams = (ViewGroup.MarginLayoutParams) params;
            } else {
                //不存在时创建一个新的参数
                //基于View本身原有的布局参数对象
                marginLayoutParams = new ViewGroup.MarginLayoutParams(params);
            }
            //设置margin
            int height_px = getHeight();
            int width_px = getWidth();
            int left = mRandom.nextInt(width_px);
            int top = mRandom.nextInt(height_px);
            left = left > (width_px / 2) ? left - width_px / 10 - getCharacterWidth(items.get(x).getValue(), items.get(x).getFrontSize()) : left;
            top = top > (height_px / 2) ? top - height_px / 10 - ((b == 0) ? getCharacterWidth(items.get(x).getValue(), items.get(x).getFrontSize()) : items.get(x).getFrontSize()) : top;
            marginLayoutParams.setMargins(left, top, 0, 0);
            textView.setLayoutParams(marginLayoutParams);
        }

        TextView tv = null;

        for (int x = 0; x < mCount; x++) {
            tv = (TextView) getChildAt(x);
            mScaleAnimation = new ScaleAnimation(0.8f, 1.3f, 0.8f, 1.3f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            mScaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if(mAnimation_index+1 == mCount){
                        mAnimation_index = 0;
                    }else {
                        mAnimation_index++;
                    }
                    doAnimination();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mScaleAnimation.setDuration(1300);
            mScaleAnimation.setInterpolator(new DecelerateInterpolator());
            mScaleAnimation.setRepeatCount(0);
        }
        getChildAt(0).startAnimation(mScaleAnimation);

    }

    void doAnimination() {
        if (mAnimation_index == 0 || getChildAt(mCount-1).getAnimation() != null) {
            getChildAt(mCount-1).setAnimation(null);
        } else {
            getChildAt(mAnimation_index - 1).setAnimation(null);
        }
        getChildAt(mAnimation_index).startAnimation(mScaleAnimation);
    }

    /**
     * 将结合排序，按照索引从带大到小
     *
     * @param items
     * @return
     */
    private List<TextItem> sortTextItem(List<TextItem> items) {
        Collections.sort(items, new Comparator<TextItem>() {
            @Override
            public int compare(TextItem o1, TextItem o2) {
                return (int) (o1.getIndex() - o2.getIndex());
            }
        });
        return items;
    }

    private int[] generateFrontSize(int count) {
        int[] sizes = new int[count];
        for (int i = 0; i < count; i++) {
            mRandom = new Random();
            sizes[i] = (mRandom.nextInt(6) * 5 + 12);
        }
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                if (sizes[i] > sizes[j]) {
                    int c = sizes[i];
                    sizes[i] = sizes[j];
                    sizes[j] = c;
                }
            }
        }
        return sizes;
    }

    //Android获取当前文字的总体长度的方法
    public int getCharacterWidth(String text, float size) {
        if (null == text || "".equals(text)) {
            return 0;
        }
        Paint paint = new Paint();
        paint.setTextSize(size);
        // int width = text_width/text.length();//每一个字符的长度
        return (int) paint.measureText(text);
    }
}
