package org.example.yj.customview.widget.customview.pictureview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import org.example.yj.customview.R;

/**
 * @author yj on  2018/5/30 15:07
 * 邮箱 yj@allong.net
 * @version 1.0.0
 */
public class PictureTagView extends RelativeLayout implements OnEditorActionListener {

    private Context context;
    private TextView tvPictureTagLabel;
    private EditText etPictureTagLabel;
    private View loTag;

    public enum Status {Normal, Edit}

    public enum Direction {Left, Right}

    private Direction direction = Direction.Left;
    private InputMethodManager imm;

    private static final int viewWidth = 80;
    private static final int viewHeight = 50;

    public PictureTagView(Context context, Direction direction) {
        super(context);
        this.context = context;
        this.direction = direction;
        initViews();
        init();
        initEvents();
    }

    public PictureTagView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PictureTagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        setStatus(Status.Normal);
        return true;
    }

    public void setStatus(Status status) {
        switch (status) {
            case Normal:
                tvPictureTagLabel.setVisibility(View.VISIBLE);
                etPictureTagLabel.clearFocus();
                tvPictureTagLabel.setText(etPictureTagLabel.getText());
                etPictureTagLabel.setVisibility(View.GONE);
                imm.hideSoftInputFromInputMethod(etPictureTagLabel.getWindowToken(), 0);
                break;
            case Edit:
                tvPictureTagLabel.setVisibility(View.GONE);
                etPictureTagLabel.setVisibility(View.VISIBLE);
                etPictureTagLabel.requestFocus();
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                break;
        }
    }

    private void initViews() {
        LayoutInflater.from(context).inflate(R.layout.item_picturetagview, this, true);
        tvPictureTagLabel = (TextView) findViewById(R.id.tvPictureTagLabel);
        etPictureTagLabel = (EditText) findViewById(R.id.etPictureTagLabel);
        loTag = findViewById(R.id.loTag);
    }

    private void init() {
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        directionChange();
    }

    private void initEvents() {
        etPictureTagLabel.setOnEditorActionListener(this);
    }

    private void directionChange() {
        switch (direction) {
            case Left:
                loTag.setBackgroundResource(R.drawable.bg_picturetagview_tagview_left);
                break;
            case Right:
                loTag.setBackgroundResource(R.drawable.bg_picturetagview_tagview_right);
                break;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        View parent = (View) getParent();
        int haltParentW = (int) (parent.getWidth() * 0.5);
        int center = l + (int) (this.getWidth() * 0.5);
        if (center <= haltParentW) {
            direction = Direction.Left;
        } else {
            direction = Direction.Right;
        }
        directionChange();
    }

    public static int getViewWidth() {
        return viewWidth;
    }

    public static int getViewHeight() {
        return viewHeight;
    }
}
