package com.yj.intent.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;

import com.yj.intent.R;
import com.yj.intent.utils.DisplayUtils;

import java.util.Objects;

/**
 * 对话框基类
 */
public abstract class BaseDialog extends DialogFragment {

    protected ViewDataBinding root;

    protected int mWidth;
    protected int mHeight;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWidth = (int) (DisplayUtils.getScreenWidth(requireContext())*0.7);
        mHeight = (int) (DisplayUtils.getScreenHeight(requireContext())*0.3);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return root.getRoot();
    }

    protected abstract int getLayoutId();

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = Objects.requireNonNull(getDialog());
        Window window = dialog.getWindow();
        assert window != null;
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = mWidth;
        params.height = mHeight;

        window.setAttributes(params);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
    }

    protected abstract void initView(View view);


}
