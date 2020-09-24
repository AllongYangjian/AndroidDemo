package org.example.yj.customview.widget.customview.tagview2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.example.yj.customview.R;
import org.example.yj.customview.utils.Utils;
import org.example.yj.customview.common.Constants;

import java.util.ArrayList;
import java.util.List;


/**
 * @author yj on  2018/5/23 15:15
 *         邮箱 yj@allong.net
 * @version 1.0.0
 */

public class TagView2 extends RelativeLayout {
    private static final String TAG = TagView2.class.getSimpleName();
    private List<Tag> mTags = new ArrayList<>();

    private LayoutInflater mInflater;
    private OnTagClickListener onTagClickListener;
    private OnTagDeleteListener onTagDeleteListener;
    private ViewTreeObserver mViewTreeObserver;

    private boolean mInitialized = false;

    int lineMargin;
    int tagMargin;
    int textPaddingLeft;
    int textPaddingRight;
    int textPaddingTop;
    int textPaddingBottom;

    int mWidth;

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.onTagClickListener = onTagClickListener;
    }

    public void setOnTagDeleteListener(OnTagDeleteListener onTagDeleteListener) {
        this.onTagDeleteListener = onTagDeleteListener;
    }

    public TagView2(Context context) {
        this(context, null);
    }

    public TagView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attributeSet, int style) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewTreeObserver = getViewTreeObserver();
        mViewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {//需要添加界面绘制完成监听，否则mWidth = 0
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                Log.e(TAG, "tag " + mWidth);
                if (!mInitialized) {
                    mInitialized = true;
                    drawTags();
                }
            }
        });

        TypedArray ta = context.obtainStyledAttributes(attributeSet, R.styleable.TagView2, style, style);
        this.lineMargin = (int) ta.getDimension(R.styleable.TagView2_lineMargin, Constants.DEFAULT_LINE_MARGIN);
        this.tagMargin = (int) ta.getDimension(R.styleable.TagView2_tagMargin, Constants.DEFAULT_TAG_MARGIN);
        this.textPaddingLeft = (int) ta.getDimension(R.styleable.TagView2_textPaddingLeft, Constants.DEFAULT_TAG_TEXT_PADDING_LEFT);
        this.textPaddingRight = (int) ta.getDimension(R.styleable.TagView2_textPaddingRight, Constants.DEFAULT_TAG_TEXT_PADDING_RIGHT);
        this.textPaddingTop = (int) ta.getDimension(R.styleable.TagView2_textPaddingTop, Constants.DEFAULT_TAG_TEXT_PADDING_TOP);
        this.textPaddingBottom = (int) ta.getDimension(R.styleable.TagView2_textPaddingBottom, Constants.DEFAULT_TAG_TEXT_PADDING_BOTTOM);

        ta.recycle();

    }

    private void drawTags() {
        if (!mInitialized) {
            return;
        }
        Log.e(TAG, mWidth + "");
        this.removeAllViews();
        float total = getPaddingLeft() + getPaddingRight();
        int listIndex = 1;
        int index_bottom = 1;
        int index_header = 1;
        Tag tag_pre = null;
        for (Tag item : mTags) {
            Log.e("TAG", "sss");
            final int position = listIndex - 1;
            final Tag tag = item;
            View tagLayout = mInflater.inflate(R.layout.item_tag_view, null);

            tagLayout.setId(listIndex);
            tagLayout.setBackgroundDrawable(getSelector(tag));

            TextView tagView = tagLayout.findViewById(R.id.tv_tag_item_contain);
            tagView.setText(tag.text);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tagView.getLayoutParams();
            params.setMargins(textPaddingLeft, textPaddingTop, textPaddingRight, textPaddingBottom);
            tagView.setLayoutParams(params);

            tagView.setTextColor(tag.tagTextColor);
            tagView.setTextSize(TypedValue.COMPLEX_UNIT_SP, tag.tagTextSize);

            tagLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onTagClickListener != null) {
                        onTagClickListener.onTagClick(tag, position);
                    }
                }
            });

            float tagWidth = tagView.getPaint().measureText(tag.text) + textPaddingLeft + textPaddingRight;


            final TextView tagDel = tagLayout.findViewById(R.id.tv_tag_item_delete);
            if (tag.isDeletable) {
                tagDel.setVisibility(View.VISIBLE);
                tagDel.setText(tag.deleteIcon);
                int offSet = Utils.dp2px(getContext(), 2);
                tagDel.setPadding(offSet, textPaddingTop, offSet + textPaddingRight, textPaddingBottom);
                tagDel.setTextColor(tag.deleteIndicatorColor);
                tagDel.setTextSize(TypedValue.COMPLEX_UNIT_SP, tag.deleteIndicatorSize);

                tagDel.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TagView2.this.remove(position);
                        if (onTagDeleteListener != null) {
                            onTagDeleteListener.onTagDeleted(tag, position);
                        }
                    }
                });
                tagWidth += tagDel.getPaint().measureText(tag.deleteIcon) + offSet * 2 + textPaddingRight;
            } else {
                tagDel.setVisibility(View.GONE);
            }

            LayoutParams tagParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tagParams.bottomMargin = lineMargin;
            if (tagWidth + total + Utils.dp2px(getContext(), Constants.LAYOUT_WIDTH_OFFSET) >= mWidth) {
                tagParams.addRule(RelativeLayout.BELOW, index_bottom);
                total = getPaddingLeft() + getPaddingRight();
                index_bottom = listIndex;
                index_header = listIndex;
            } else {
                tagParams.addRule(RelativeLayout.ALIGN_TOP, index_header);
                if (listIndex != index_header) {
                    tagParams.addRule(RelativeLayout.RIGHT_OF, listIndex - 1);
                    tagParams.leftMargin = tagMargin;
                    total += tagMargin;
                    if (tag_pre != null && tag_pre.tagTextSize < tag.tagTextSize) {
                        index_bottom = listIndex;
                    }
                }
            }
            total += tagWidth;
            addView(tagLayout, tagParams);
            tag_pre = tag;
            listIndex++;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY || height == MeasureSpec.EXACTLY) {
            mWidth = width;
        } else {
            mWidth = 400;
        }
        setMeasuredDimension(mWidth, height);
    }

    private Drawable getSelector(Tag tag) {
        if (tag.background != null) {
            return tag.background;
        }
        StateListDrawable states = new StateListDrawable();
        GradientDrawable gd_normal = new GradientDrawable();
        gd_normal.setColor(tag.layoutColor);
        gd_normal.setCornerRadius(tag.radius);

        if (tag.layoutBorderSize > 0) {
            gd_normal.setStroke(Utils.dp2px(getContext(), tag.layoutBorderSize), tag.layoutBorderColor);
        }

        GradientDrawable gd_press = new GradientDrawable();
        gd_press.setColor(tag.layoutColorPress);
        gd_press.setCornerRadius(tag.radius);
        states.addState(new int[]{android.R.attr.state_pressed}, gd_press);
        states.addState(new int[]{}, gd_normal);
        return states;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
    }

    public void addTags(Tag tag) {
        mTags.add(tag);
        drawTags();
    }

    public void addTags(String[] tags) {
        if (tags == null)
            return;
        for (String item : tags) {
            Tag tag = new Tag(item);
            addTags(tag);
        }
    }

    public void remove(int position) {
        mTags.remove(position);
        drawTags();
    }

    public void removeAllTags() {
        mTags.clear();
        drawTags();
    }
}
