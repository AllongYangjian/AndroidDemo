package com.yj.app.view.layoutcomponent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.yj.app.R;
import com.yj.app.databinding.ActivitySearchResponseBinding;

/**
 * 搜索对话框界面
 */
public class SearchDialogActivity extends AppCompatActivity {

    private ActivitySearchResponseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_response);
        setSupportActionBar(binding.toolbar);

        binding.btnSearch.setOnClickListener(view -> onSearchRequested());
    }

    @Override
    public boolean onSearchRequested() {
        Bundle appData = new Bundle();
        appData.putBoolean(SearchableActivity.JARGON, true);
        startSearch(null, false, appData, false);
        return true;
    }
}
