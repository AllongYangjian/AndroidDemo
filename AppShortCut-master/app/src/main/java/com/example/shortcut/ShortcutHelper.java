package com.example.shortcut;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.function.BooleanSupplier;

public class ShortcutHelper {

    private static final String TAG = "ShortcutHelper";

    private static final String EXTRA_LAST_REFRESH =
            "com.example.android.shortcutsample.EXTRA_LAST_REFRESH";

    private long REFRESH_INTERVAL_MS = 60 * 60 * 1000;

    private Context mContext;

    private ShortcutManager mShortcutManager;

    public ShortcutHelper(Context context) {
        this.mContext = context;
        mShortcutManager = (ShortcutManager) context.getSystemService(Context.SHORTCUT_SERVICE);
    }

    @SuppressLint("StaticFieldLeak")
    public void refreshShortcuts(final boolean force) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Log.i(TAG, "refreshingShortcuts...");

                final long now = System.currentTimeMillis();
                final long staleThreshold = force ? now : now - REFRESH_INTERVAL_MS;

                final List<ShortcutInfo> updateList = new ArrayList<>();

                for (ShortcutInfo info : getShortcuts()) {
                    if (info.isImmutable()) {
                        continue;
                    }

                    PersistableBundle bundle = info.getExtras();
                    if (bundle != null && bundle.getLong(EXTRA_LAST_REFRESH) >= staleThreshold) {
                        continue;
                    }

                    final ShortcutInfo.Builder b = new ShortcutInfo.Builder(
                            mContext, info.getId());

                    setSiteInformation(b, info.getIntent().getData());
                    setExtras(b);

                    updateList.add(b.build());
                }

                if (updateList.size() > 0) {
                    callShortcutManager(() -> mShortcutManager.updateShortcuts(updateList));
                }
                Log.e(TAG, getShortcuts().size() + ",");
                return null;
            }
        }.execute();
    }

    /**
     * Return all mutable shortcuts from this app self.
     */
    public List<ShortcutInfo> getShortcuts() {
        // Load mutable dynamic shortcuts and pinned shortcuts and put them into a single list
        // removing duplicates.

        final List<ShortcutInfo> ret = new ArrayList<>();
        final HashSet<String> seenKeys = new HashSet<>();

        // Check existing shortcuts shortcuts
        for (ShortcutInfo shortcut : mShortcutManager.getDynamicShortcuts()) {
            if (!shortcut.isImmutable()) {
                ret.add(shortcut);
                seenKeys.add(shortcut.getId());
            }
        }
        for (ShortcutInfo shortcut : mShortcutManager.getPinnedShortcuts()) {
            if (!shortcut.isImmutable() && !seenKeys.contains(shortcut.getId())) {
                ret.add(shortcut);
                seenKeys.add(shortcut.getId());
            }
        }
        return ret;
    }

    public void reportShortcutUsed(String idAddWebsite) {
        mShortcutManager.reportShortcutUsed(idAddWebsite);
    }

    public void addWebSiteShortcut(String url) {
        final String webUrl = url;
        callShortcutManager(() -> {
            final ShortcutInfo info = createShortcutForUrl(normalizeUrl(webUrl));
            return mShortcutManager.addDynamicShortcuts(Collections.singletonList(info));
        });
    }

    private ShortcutInfo createShortcutForUrl(String normalizeUrl) {
        final ShortcutInfo.Builder builder = new ShortcutInfo.Builder(mContext, normalizeUrl);

        //添加intent信息
        final Uri uri = Uri.parse(normalizeUrl);
        builder.setIntent(new Intent(Intent.ACTION_VIEW, uri));

        setSiteInformation(builder, uri);
        setExtras(builder);

        return builder.build();
    }

    /**
     * 设置上次刷新时间
     *
     * @param b
     * @return
     */
    private ShortcutInfo.Builder setExtras(ShortcutInfo.Builder b) {
        final PersistableBundle extras = new PersistableBundle();
        extras.putLong(EXTRA_LAST_REFRESH, System.currentTimeMillis());
        b.setExtras(extras);
        return b;
    }


    /**
     * 设置图标及说明
     *
     * @param builder
     * @param uri
     */
    private void setSiteInformation(ShortcutInfo.Builder builder, Uri uri) {
        builder.setShortLabel(uri.getHost());
        builder.setLongLabel(uri.toString());
        Bitmap bitmap = fetchFavicon(uri);
        if (bitmap == null) {
            builder.setIcon(Icon.createWithResource(mContext, R.drawable.link));
        } else {
            builder.setIcon(Icon.createWithBitmap(bitmap));
        }
    }

    /**
     * 根据url地址拉取icon
     *
     * @param uri
     * @return
     */
    private Bitmap fetchFavicon(Uri uri) {
        final Uri iconUri = uri.buildUpon().path("favicon.ico").build();
        Log.i(TAG, "Fetching favicon from: " + iconUri);

        InputStream is = null;
        BufferedInputStream bis = null;
        try
        {
            URLConnection conn = new URL(iconUri.toString()).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            return BitmapFactory.decodeStream(bis);
        } catch (IOException e) {
            Log.w(TAG, "Failed to fetch favicon from " + iconUri, e);
            return null;
        }
    }


    private String normalizeUrl(String urlAsString) {
        if (urlAsString.startsWith("http://") || urlAsString.startsWith("https://")) {
            return urlAsString;
        } else {
            return "http://" + urlAsString;
        }
    }

    /**
     * Use this when interacting with ShortcutManager to show consistent error messages.
     */
    private void callShortcutManager(BooleanSupplier r) {
        try {
            if (!r.getAsBoolean()) {
                Utils.showToast(mContext, "Call to ShortcutManager is rate-limited");
            }
        } catch (Exception e) {
            Log.e(TAG, "Caught Exception", e);
            Utils.showToast(mContext, "Error while calling ShortcutManager: " + e.toString());
        }
    }

    public void disableShortcut(ShortcutInfo info) {
        mShortcutManager.disableShortcuts(Collections.singletonList(info.getId()));
    }

    public void enableShortcut(ShortcutInfo info) {
        mShortcutManager.enableShortcuts(Collections.singletonList(info.getId()));
    }

    public void removeShortcut(ShortcutInfo info) {
        mShortcutManager.removeDynamicShortcuts(Collections.singletonList(info.getId()));
    }
}
