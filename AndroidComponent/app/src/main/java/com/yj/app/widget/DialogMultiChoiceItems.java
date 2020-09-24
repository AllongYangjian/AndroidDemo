package com.yj.app.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.yj.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 复选对话框
 */
public class DialogMultiChoiceItems extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        List<String> selectHobbits = new ArrayList<>();
        String[] hobbits = getResources().getStringArray(R.array.hobbit);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("复选对话框")
                .setMultiChoiceItems(hobbits, null, (dialog, which, isChecked) -> {
                    if (isChecked) {
                        if (!selectHobbits.contains(hobbits[which])) {
                            selectHobbits.add(hobbits[which]);
                        }
                    } else {
                        if (selectHobbits.contains(hobbits[which])) {
                            selectHobbits.remove(hobbits[which]);
                        }
                    }
                })
                .setPositiveButton("确定", (dialog, which) -> {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < selectHobbits.size(); i++) {
                        sb.append(selectHobbits.get(i)).append(",");
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    Toast.makeText(requireContext(), "您的爱好是：" + sb.toString(), Toast.LENGTH_LONG).show();
                })
                .setNegativeButton("曲线", (dialog, which) -> {
                });
        return builder.create();
    }
}
