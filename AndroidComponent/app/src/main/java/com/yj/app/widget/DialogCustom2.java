package com.yj.app.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.yj.app.R;


/**
 * 自定义对话框
 */
public class DialogCustom2 extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_login, null);
    }

    @Override
    public void onStart() {
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            assert window != null;
            window.setBackgroundDrawableResource(android.R.color.white);
        }
        super.onStart();
    }

    public interface DialogDismissListener {
        void onDialogDismiss(String username, String password);
    }

    private DialogDismissListener listener;

    public void setListener(DialogDismissListener listener) {
        this.listener = listener;
    }
}
