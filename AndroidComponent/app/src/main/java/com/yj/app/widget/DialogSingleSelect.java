package com.yj.app.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.yj.app.R;

/**
 * 单选对话框
 */
public class DialogSingleSelect extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        String[] sexes = getResources().getStringArray(R.array.item_sex);
        builder.setTitle("请选择")
                .setItems(sexes, (view, index) ->
                        Toast.makeText(requireContext(), sexes[index], Toast.LENGTH_LONG).show());
        return builder.create();
    }
}
