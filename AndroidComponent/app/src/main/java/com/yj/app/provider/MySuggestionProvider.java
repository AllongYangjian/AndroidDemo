package com.yj.app.provider;

import android.content.SearchRecentSuggestionsProvider;

/**
 * 最近搜索激记录内容提供者
 */
public class MySuggestionProvider extends SearchRecentSuggestionsProvider {

    public static final String AUTHORITY = "com.yj.app.provider.MySuggestionProvider";

    public static final int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY,MODE);
    }
}
