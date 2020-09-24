package com.yj.app.view.layoutcomponent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.yj.app.R;
import com.yj.app.databinding.ActivityScrollRefreshBinding;

import java.util.Objects;

public class ScrollRefreshActivity extends AppCompatActivity {

    private ActivityScrollRefreshBinding binding;
    private ArrayAdapter<CharSequence> mAdapter;
    private final int MSG_REFRESH = 10010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scroll_refresh);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initView();
    }

    private void initView() {
        //只适用于数据固定的
//        mAdapter = ArrayAdapter.createFromResource(this, R.array.item_test_list_data,
//                android.R.layout.simple_list_item_1);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        mAdapter.addAll(getResources().getStringArray(R.array.item_test_list_data));
        binding.listView.setAdapter(mAdapter);

        binding.swipeLayout.setOnRefreshListener(this::loadDemoData);
    }

    private void loadDemoData() {
        handler.sendEmptyMessageDelayed(MSG_REFRESH, 2000);
    }

    private void addDemoData() {
        mAdapter.addAll(getResources().getStringArray(R.array.item_test_list_data));
        binding.swipeLayout.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                binding.swipeLayout.setRefreshing(true);
                loadDemoData();
                break;
            case R.id.search:
            case R.id.add:
            case R.id.edit:
            case R.id.delete:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case MSG_REFRESH:
                    addDemoData();
                    break;
            }
        }
    };
}
