package com.yj.layout.customui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.autofill.AutofillValue;

import androidx.annotation.Nullable;

public class TimeEditText extends androidx.appcompat.widget.AppCompatEditText {
    public TimeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getAutofillType() {
        return AUTOFILL_TYPE_DATE;
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public AutofillValue getAutofillValue() {
        return AutofillValue.forDate(System.currentTimeMillis());
    }

    @Override
    public void autofill(AutofillValue value) {
        super.autofill(value);
    }
}


