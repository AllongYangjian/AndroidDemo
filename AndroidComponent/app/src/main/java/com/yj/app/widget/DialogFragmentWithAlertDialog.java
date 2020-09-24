package com.yj.app.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogFragmentWithAlertDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Fire missiles?")
                .setPositiveButton("Fire", (view, id) -> {
                    Toast.makeText(requireContext(), "Fire", Toast.LENGTH_LONG).show();
                })
                .setNegativeButton("CANCEL", (view, id) -> {
                    Toast.makeText(requireContext(), "CANCEL", Toast.LENGTH_LONG).show();
                });
        return builder.create();
    }
}
