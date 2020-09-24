package com.yj.app.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.yj.app.R;

/**
 * 自定义对话框
 */
public class DialogCustom extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View view = inflater.inflate(R.layout.dialog_login, null);

        EditText etUsername = view.findViewById(R.id.et_username);
        EditText etPassword = view.findViewById(R.id.et_password);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("登录")
                .setView(view)
                .setPositiveButton("登录", (dialog, which) -> {
                    String username = etUsername.getText().toString().trim();
                    String password = etPassword.getText().toString().trim();
                    if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                        etUsername.setError("请输入用户名");
                        etPassword.setError("请输入密码");
                        return;
                    }
                    if (listener != null) {
                        listener.onDialogDismiss(username, password);
                    }
                })
                .setNegativeButton("取消", (dialog, which) -> {
                    dismiss();
                });
        return builder.create();
    }

    public interface DialogDismissListener {
        void onDialogDismiss(String username, String password);
    }

    private DialogDismissListener listener;

    public void setListener(DialogDismissListener listener) {
        this.listener = listener;
    }
}
