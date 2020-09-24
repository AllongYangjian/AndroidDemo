package org.example.yj.customview.widget.customview.tagview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.example.yj.customview.R;

import java.util.HashMap;
import java.util.List;

/**
 * @author yj on  2018/5/17 23:50
 *         邮箱 yj@allong.net
 * @version 1.0.0
 */

public class TagView extends ViewGroup {

    private float mTagSize;//字体大小
    private int mTagColor;//字体颜色
    private int mSelectTagColor;//选中色
    private int mBackground;//背景色
    private int mViewBorder = 5;//边距
    private int mTagHorSpace = 5;//水平间距
    private int mTagVerSpace = 5;//垂直间距

    private int mTagResId;//标签布局
    private int mRightImageResId;
    private boolean mSingleLine;//是否单行显示
    private boolean mShowRightImage;//是否显示右侧箭头
    private boolean mShowEndText;//是否显示提示文本
    private boolean mCanTagClick;
    private String endTextString;//提示文本内容

    private List<String> tags;

    private LayoutInflater inflater;
    private ImageView rightImage;
    private int rightImageWidth;
    private int rightImageHeight;
    private TextView endText;
    private int endTextWidth;
    private int endTextHeight;

    private int sizeWidth;
    private int sizeHeight;
    public static final int TYPE_TEXT_NORMAL = 1;
    private static final int DEFAULT_TEXT_SIZE = 14;
    private static final int DEFAULT_TEXT_BACKGROUND = R.drawable.tag_background;
    private static final int DEFAULT_VIEW_BORDER = 14;
    private static final int DEFAULT_TEXT_BORDER_HORIZONTAL = 8;
    private static final int DEFAULT_TEXT_BORDER_VERTICAL = 5;
    private static final boolean DEFAULT_CAN_TAG_CLICK = true;
    private static final boolean DEFAULT_SINGLE_LINE = false;
    private static final boolean DEFAULT_SHOW_RIGHT_IMAGE = true;
    private static final boolean DEFAULT_SHOW_END_TEXT = true;
    private static final int DEFAULT_RIGHT_IMAGE = R.mipmap.arrow_right;
    private static final String DEFAULT_END_TEXT_STRING = "更多";
    private static final int DEFAULT_TAG_RESID = R.layout.item_tag;

    public interface OnTagClickListener {
        void onTagClickListener(int position);
    }

    private OnTagClickListener onTagClickListener;

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.onTagClickListener = onTagClickListener;
    }

    public TagView(Context context) {
        this(context, null);
    }

    public TagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        inflater = LayoutInflater.from(context);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TagView, defStyleAttr, defStyleAttr);

        mTagSize = ta.getDimension(R.styleable.TagView_tgTextSize, DEFAULT_TEXT_SIZE);
        mTagColor = ta.getColor(R.styleable.TagView_tgTextColor, getResources().getColor(R.color.white));
        mSelectTagColor = ta.getColor(R.styleable.TagView_tgSelectTextColor, getResources().getColor(R.color.yellow));

        mBackground = ta.getResourceId(R.styleable.TagView_tgBackground, DEFAULT_TEXT_BACKGROUND);
        mViewBorder = ta.getDimensionPixelSize(R.styleable.TagView_tgBorder, DEFAULT_VIEW_BORDER);
        mTagHorSpace = ta.getDimensionPixelSize(
                R.styleable.TagView_tgItemBorderHorizontal, DEFAULT_TEXT_BORDER_HORIZONTAL);
        mTagVerSpace = ta.getDimensionPixelSize(
                R.styleable.TagView_tgItemBorderVertical, DEFAULT_TEXT_BORDER_VERTICAL);
        mCanTagClick = ta.getBoolean(R.styleable.TagView_tgCanTagClick, DEFAULT_CAN_TAG_CLICK);

        mRightImageResId = ta.getResourceId(R.styleable.TagView_tgRightResId, DEFAULT_RIGHT_IMAGE);
        mSingleLine = ta.getBoolean(R.styleable.TagView_tgSingleLine, DEFAULT_SINGLE_LINE);
        mShowRightImage = ta.getBoolean(R.styleable.TagView_tgShowRightImg, DEFAULT_SHOW_RIGHT_IMAGE);
        mShowEndText = ta.getBoolean(R.styleable.TagView_tgShowEndText, DEFAULT_SHOW_END_TEXT);
        endTextString = ta.getString(R.styleable.TagView_tgEndText);

        mTagResId = ta.getResourceId(R.styleable.TagView_tgTagResId, DEFAULT_TAG_RESID);

        ta.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        sizeWidth = MeasureSpec.getSize(widthMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        initSingleLineView(widthMeasureSpec, heightMeasureSpec);

        int totalWidth = 0;
        int totalHeight = mTagVerSpace;
        if (mSingleLine) {
            totalHeight = getSingleTotalHeight(totalWidth, totalHeight);
        } else {
            totalHeight = getMultiTotalHeight(totalHeight);
        }
        setMeasuredDimension(sizeWidth, (heightMode == MeasureSpec.EXACTLY ? sizeHeight : totalHeight));
    }

    /**
     * 单行显示布局
     *
     * @param totalWidth  子view总宽度
     * @param totalHeight 子view总高度
     * @return
     */
    private int getSingleTotalHeight(int totalWidth, int totalHeight) {
        int childWidth;
        int childHeight;

        totalHeight += mViewBorder;
        int textTotalWidth = getTextTotalWidth();
        /*int width = sizeWidth;
        if (mShowEndText) {
            width -= rightImageWidth - mTagHorSpace;
        }
        if (mShowEndText) {
            width -= endTextWidth - mTagHorSpace;
        }*/

        if (textTotalWidth < sizeWidth - mViewBorder * 2 - (rightImageWidth + mTagHorSpace)) {
            endText = null;
            endTextWidth = 0;
        }

        for (int x = 0; x < getChildCount(); x++) {
            View child = getChildAt(x);
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();

            if (x == 0) {
                totalWidth += mViewBorder + childWidth;
                totalHeight = mViewBorder + childHeight;
            } else {
                totalWidth += mTagHorSpace + childWidth;
            }

            if (child.getTag() != null && (int) child.getTag() == TYPE_TEXT_NORMAL) {
                if (totalWidth + mViewBorder + endTextWidth + mTagHorSpace * 2 + rightImageWidth <= sizeWidth) {
                    child.layout(totalWidth - childWidth,
                            totalHeight - childHeight,
                            totalWidth,
                            totalHeight);
                } else {
                    totalWidth -= (childWidth + mTagHorSpace);
                    break;//剩余的就不显示
                }
            }
        }

        if (endText != null) {
//            Log.e("TAG", "s" + endTextWidth + "," + endTextHeight);
//            Log.e("TAG", endText.getText().toString());
            endText.layout(totalWidth + mTagHorSpace,
                    totalHeight - endTextHeight,
                    totalWidth + mTagHorSpace + endTextWidth,
                    totalHeight);
        }

        totalHeight += mViewBorder;

        if (rightImage != null) {
            rightImage.layout(sizeWidth - mViewBorder - rightImageWidth,
                    (totalHeight - rightImageHeight) / 2,
                    sizeWidth - mViewBorder,
                    (totalHeight - rightImageHeight) / 2 + rightImageHeight);
        }

        return totalHeight;
    }

    /**
     * layout multi 多行高度
     *
     * @param totalHeight 总高度
     * @return 总高度
     */
    public int getMultiTotalHeight(int totalHeight) {
        int childWidth;
        int childHeight;

        int totalWidth = mViewBorder;

        for (int x = 0; x < getChildCount(); x++) {

            View child = getChildAt(x);
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();

            totalWidth += childWidth + mTagHorSpace;
            if (x == 0) {
                totalHeight = childHeight + mViewBorder;
            }
            if (totalWidth + mViewBorder > sizeWidth) {
                totalHeight += childHeight + mTagVerSpace;
                totalWidth = mViewBorder;
                child.layout(totalWidth,
                        totalHeight - childHeight,
                        totalWidth + childWidth,
                        totalHeight);
                totalWidth += childWidth + mTagHorSpace;
            } else {
                child.layout(totalWidth - childWidth - mTagHorSpace,
                        totalHeight - childHeight,
                        totalWidth - mTagHorSpace,
                        totalHeight);
            }
        }
        return totalHeight + mViewBorder;
    }

    /**
     * 计算子view总宽度
     *
     * @return 子view总宽度
     */
    private int getTextTotalWidth() {
        if (getChildCount() == 0)
            return 0;
        int totalChildWidth = 0;
        for (int x = 0; x < getChildCount(); x++) {
            View child = getChildAt(x);
            if (child.getTag() != null && (int) child.getTag() == TYPE_TEXT_NORMAL) {
                totalChildWidth += child.getMeasuredWidth() + mTagHorSpace;
            }
        }
        return totalChildWidth;
    }

    /**
     * 根据属性决定是否初始化末尾文本和不骗
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    private void initSingleLineView(int widthMeasureSpec, int heightMeasureSpec) {
        if (!mSingleLine) {
            return;
        }
        //显示右侧图标
        if (mShowRightImage) {
            rightImage = new ImageView(getContext());
            rightImage.setImageResource(R.mipmap.arrow_right);
            rightImage.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            rightImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            measureChild(rightImage, widthMeasureSpec, heightMeasureSpec);
            rightImageWidth = rightImage.getMeasuredWidth();
            rightImageHeight = rightImage.getMeasuredHeight();

            addView(rightImage);
        }
        //显示末尾文字
        if (mShowEndText) {
            endText = (TextView) inflater.inflate(mTagResId, null);
            if (mTagResId == DEFAULT_TAG_RESID) {
                endText.setBackgroundResource(mBackground);
                endText.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTagSize);
                endText.setTextColor(Color.BLACK);
            }

            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            endText.setLayoutParams(params);
            endText.setText(endTextString == null || endTextString.equals("")
                    ? DEFAULT_END_TEXT_STRING : endTextString);
            measureChild(endText, widthMeasureSpec, heightMeasureSpec);
            endTextWidth = endText.getMeasuredWidth();
            endTextHeight = endText.getMeasuredHeight();

            addView(endText);

            endText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onTagClickListener != null) {
                        onTagClickListener.onTagClickListener(-1);
                    }
                }
            });
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }


    public void setTagsByPosition(HashMap<Integer, Boolean> positions, List<String> taglist) {
        this.tags = taglist;
        this.removeAllViews();
        if (tags != null && tags.size() > 0) {
            for (int i = 0; i < tags.size(); i++) {
                TextView tagView = (TextView) inflater.inflate(mTagResId, null);
                if (mTagResId == DEFAULT_TAG_RESID) {
                    tagView.setBackgroundResource(mBackground);
                    tagView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTagSize);
                    if (positions.get(i)) {
                        tagView.setTextColor(mSelectTagColor);
                    } else {
                        tagView.setTextColor(mTagColor);
                    }
                }
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                tagView.setLayoutParams(layoutParams);
                tagView.setText(tags.get(i));
                tagView.setTag(TYPE_TEXT_NORMAL);
                final int finalI = i;
                tagView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onTagClickListener != null) {
                            onTagClickListener.onTagClickListener(finalI);
                        }
                    }
                });
                addView(tagView);
            }
        }

        postInvalidate();
    }

    public void setTagsByLength(int length, List<String> tagList) {
        this.tags = tagList;
        this.removeAllViews();
        if (tags != null && tags.size() > 0) {

            for (int i = 0; i < tags.size(); i++) {
                TextView tagView = (TextView) inflater.inflate(mTagResId, null);
                if (mTagResId == DEFAULT_TAG_RESID) {
                    tagView.setBackgroundResource(mBackground);
                    tagView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTagSize);
                    if (i >= length) {
                        tagView.setTextColor(mTagColor);
                    } else {
                        tagView.setTextColor(mSelectTagColor);
                    }
                }
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                tagView.setLayoutParams(layoutParams);
                tagView.setText(tags.get(i));
                tagView.setTag(TYPE_TEXT_NORMAL);
                final int finalI = i;
                tagView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onTagClickListener != null) {
                            onTagClickListener.onTagClickListener(finalI);
                        }
                    }
                });
                addView(tagView);
            }
        }
        postInvalidate();
    }

    public void setTags(List<String> tagList) {
        this.tags = tagList;
        this.removeAllViews();
        if (tags != null && tags.size() > 0) {
            for (int i = 0; i < tags.size(); i++) {
                TextView tagView = (TextView) inflater.inflate(mTagResId, null);
                if (mTagResId == DEFAULT_TAG_RESID) {
                    tagView.setBackgroundResource(mBackground);
                    tagView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTagSize);
                    tagView.setTextColor(mTagColor);
                }
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                tagView.setLayoutParams(layoutParams);
                tagView.setText(tags.get(i));
                tagView.setTag(TYPE_TEXT_NORMAL);
                final int finalI = i;
                tagView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onTagClickListener != null) {
                            onTagClickListener.onTagClickListener(finalI);
                        }
                    }
                });
                addView(tagView);
            }
        }
        postInvalidate();
    }
}
