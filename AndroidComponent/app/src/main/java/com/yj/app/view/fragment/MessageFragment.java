package com.yj.app.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.yj.app.R;

import java.util.Objects;

/**
 * 针对单个 Preference.OnPreferenceChangeListener 使用
 */
public class MessageFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.message, rootKey);
        initView();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Log.e("preference", preference.getKey() + " Pending Preference value is: " + newValue);
        return true;
    }

    private void initView() {
        SwitchPreferenceCompat preference = findPreference("enable_message");
        assert preference != null;
        preference.setOnPreferenceChangeListener(this);

        EditTextPreference editTextPreference = findPreference("count");
        if(editTextPreference!=null){
            editTextPreference.setOnBindEditTextListener(editText -> {
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
            });
        }
        Objects.requireNonNull(editTextPreference).setOnPreferenceChangeListener(this);
    }
}
