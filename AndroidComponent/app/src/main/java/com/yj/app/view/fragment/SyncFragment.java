package com.yj.app.view.fragment;

import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.yj.app.R;

public class SyncFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.sync,rootKey);
        initView();
    }

    private void initView(){
        ListPreference listPreference = findPreference("alarm_type");
        if(listPreference!=null){
            listPreference.setSummaryProvider((Preference.SummaryProvider<ListPreference>) preference -> preference.getValue()+"--");
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }
}
