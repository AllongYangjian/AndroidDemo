package com.example.shortcut;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.pm.ShortcutInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.List;

public class MainActivity extends ListActivity implements View.OnClickListener {

    private static final String ACTION = "com.example.shortcut.ADD_ACTION";

    private static final String ID_ADD_WEBSITE = "add_website";

    private ShortcutHelper shortcutHelper;

    private MyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shortcutHelper = new ShortcutHelper(getApplicationContext());
        shortcutHelper.refreshShortcuts(false);

        if (ACTION.equals(getIntent().getAction())) {
            addWebSite();
        }

        adapter = new MyAdapter(getApplicationContext());
        setListAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    private void refreshList() {
        adapter.setShortcutInfos(shortcutHelper.getShortcuts());
    }

    /**
     * 添加新的站点
     * 按钮指定的方法
     */
    public void onAddPressed(View view) {
        addWebSite();
    }

    @Override
    public void onClick(View v) {
        ShortcutInfo info = (ShortcutInfo) ((View) v.getParent()).getTag();
        if (info != null) {
            System.err.println(info.toString());
        }
        switch (v.getId()) {
            case R.id.disable:
                if (info.isEnabled()) {
                    shortcutHelper.disableShortcut(info);
                } else {
                    shortcutHelper.enableShortcut(info);
                }
                refreshList();
                break;
            case R.id.remove:
                shortcutHelper.removeShortcut(info);
                refreshList();
                break;
        }
    }

    private void addWebSite() {
        shortcutHelper.reportShortcutUsed(ID_ADD_WEBSITE);

        final EditText editText = new EditText(this);
        editText.setHint("http://www.android.com");
        editText.setInputType(EditorInfo.TYPE_TEXT_VARIATION_URI);

        new AlertDialog.Builder(this)
                .setTitle("Add New Website")
                .setMessage("Type Url Of a Website")
                .setView(editText)
                .setPositiveButton("Add", (dialog, which) -> {
                    final String url = editText.getText().toString().trim();
                    if (url.length() > 0) {
                        addUriAsync(url);
                    }
                })
                .show();
    }

    private void addUriAsync(String url) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                shortcutHelper.addWebSiteShortcut(url);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                refreshList();
            }
        }.execute();
    }


    public class MyAdapter extends BaseAdapter {

        private Context context;

        private LayoutInflater inflater;

        private List<ShortcutInfo> shortcutInfos;

        public MyAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        public void setShortcutInfos(List<ShortcutInfo> shortcutInfos) {
            this.shortcutInfos = shortcutInfos;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if (shortcutInfos == null)
                return 0;
            return shortcutInfos.size();
        }

        @Override
        public ShortcutInfo getItem(int position) {
            if (shortcutInfos == null)
                return null;
            return shortcutInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

//            ShortcutInfo info = shortcutInfos.get(position);
//            ViewHolder holder;
//
//            if (convertView == null) {
//                convertView = inflater.inflate(R.layout.item_shortinfo_view, null);
//                holder = new ViewHolder(convertView);
//                convertView.setTag(R.id.tag, info);
//            } else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//
//            holder.line1.setText(info.getLongLabel());
//            holder.line2.setText(getShortcutInfoType(info));
//            holder.btnDisable.setText(
//                    info.isEnabled() ? R.string.disable_shortcut : R.string.enable_shortcut);
//
//            holder.btnDisable.setOnClickListener(MainActivity.this);
//            holder.btnRemove.setOnClickListener(MainActivity.this);
//
//            return convertView;

            final View view;
            if (convertView != null) {
                view = convertView;
            } else {
                view = inflater.inflate(R.layout.item_shortinfo_view, null);
            }

            bindView(view, position, shortcutInfos.get(position));

            return view;
        }

        public void bindView(View view, int position, ShortcutInfo shortcut) {
            view.setTag(shortcut);

            final TextView line1 = (TextView) view.findViewById(R.id.line1);
            final TextView line2 = (TextView) view.findViewById(R.id.line2);

            line1.setText(shortcut.getLongLabel());

            line2.setText(getShortcutInfoType(shortcut));

            final Button remove = (Button) view.findViewById(R.id.remove);
            final Button disable = (Button) view.findViewById(R.id.disable);

            disable.setText(
                    shortcut.isEnabled() ? R.string.disable_shortcut : R.string.enable_shortcut);

            remove.setOnClickListener(MainActivity.this);
            disable.setOnClickListener(MainActivity.this);
        }

//        class ViewHolder {
//            TextView line1;
//            TextView line2;
//            Button btnRemove;
//            Button btnDisable;
//
//            public ViewHolder(View convertView) {
//                line1 = convertView.findViewById(R.id.line1);
//                line2 = convertView.findViewById(R.id.line2);
//
//                btnRemove = (Button) convertView.findViewById(R.id.remove);
//                btnDisable = (Button) convertView.findViewById(R.id.disable);
//
//                convertView.setTag(this);
//            }
//        }
    }

    private String getShortcutInfoType(ShortcutInfo info) {
        StringBuilder sb = new StringBuilder();
        String sep = "";

        if (info.isDynamic()) {
            sb.append(sep);
            sb.append("Dynamic");
            sep = ", ";
        }

        if (info.isPinned()) {
            sb.append(sep);
            sb.append("Pinned");
            sep = ", ";
        }

        if (!info.isEnabled()) {
            sb.append(sep);
            sb.append("Disabled");
            sep = ", ";
        }

        return sb.toString();
    }
}
