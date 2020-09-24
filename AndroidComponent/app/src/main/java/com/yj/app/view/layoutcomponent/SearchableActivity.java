package com.yj.app.view.layoutcomponent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.widget.Toast;

import com.yj.app.R;
import com.yj.app.databinding.ActivitySearchBinding;
import com.yj.app.provider.MySuggestionProvider;

/**
 * 可搜索Activity，用来统一处理搜索请求
 */
public class SearchableActivity extends AppCompatActivity {
    private final String TAG = SearchableActivity.class.getSimpleName();
    private ActivitySearchBinding binding;

    public static final String JARGON = "JARGON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        setSupportActionBar(binding.toolbar);
        handleIntent(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            Bundle bundle = intent.getBundleExtra(SearchManager.APP_DATA);
            if (bundle != null) {
                boolean jargon = bundle.getBoolean(JARGON);
                Log.e(TAG, "jargon:" + jargon);
            }
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, MySuggestionProvider.AUTHORITY,
                    MySuggestionProvider.MODE);
            suggestions.saveRecentQuery(query,null);
            Toast.makeText(this,query,Toast.LENGTH_LONG).show();
        }
    }

}
