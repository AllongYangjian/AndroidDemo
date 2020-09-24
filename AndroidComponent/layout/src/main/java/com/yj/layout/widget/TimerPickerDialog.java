package com.yj.layout.widget;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Locale;

/**
 * 时间选择对话框
 */
public class TimerPickerDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private final String TAG ="TimerPickerDialog";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(requireActivity(),this,hour,minute,true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = String.format(Locale.getDefault(),"%02d:%02d",hourOfDay,minute);
    }
}
