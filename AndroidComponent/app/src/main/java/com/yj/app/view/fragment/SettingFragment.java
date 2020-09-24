package com.yj.app.view.fragment;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.yj.app.R;

public class SettingFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings,rootKey);
    }
}
