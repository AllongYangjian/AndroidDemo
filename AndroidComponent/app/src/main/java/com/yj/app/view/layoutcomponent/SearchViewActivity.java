package com.yj.app.view.layoutcomponent;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;

import com.yj.app.R;
import com.yj.app.databinding.ActivitySearchViewBinding;
import com.yj.app.provider.MySuggestionProvider;

/**
 * 可搜索Activity
 */
public class SearchViewActivity extends AppCompatActivity {
    private final String TAG = SearchViewActivity.class.getSimpleName();
    private ActivitySearchViewBinding binding;

    public static final String JARGON = "JARGON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_view);
        setSupportActionBar(binding.toolbar);
        binding.btnClear.setOnClickListener(view -> {
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
            suggestions.clearHistory();
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_seachr, menu);
        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSubmitButtonEnabled(true);
        //指定要处理的组件名称
        ComponentName componentName = new ComponentName(this.getPackageName(), SearchableActivity.class.getName());
        searchView.setSearchableInfo(manager.getSearchableInfo(componentName));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
