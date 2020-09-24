package com.yj.app.view.layoutcomponent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.yj.app.R;
import com.yj.app.databinding.ActivitySettingBinding;
import com.yj.app.view.fragment.SettingFragment;

public class SettingActivity extends AppCompatActivity implements
        PreferenceFragmentCompat.OnPreferenceStartFragmentCallback{

    private ActivitySettingBinding binding;

    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        sp = PreferenceManager.getDefaultSharedPreferences(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, new SettingFragment())
                .commit();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, Preference pref) {
        final Bundle bundle = pref.getExtras();

        final Fragment fragment = getSupportFragmentManager().getFragmentFactory()
                .instantiate(getClassLoader(), pref.getFragment());
        fragment.setArguments(bundle);
        fragment.setTargetFragment(caller, 0);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment)
                .addToBackStack(null).commit();

        return true;
    }


}
