package com.yj.actionbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.yj.actionbar.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //调暗系统栏
        View decorView = getWindow().getDecorView();
//        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
//
        //隐藏系统栏
//        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //设置toolbar为actionbar
        setSupportActionBar(binding.toolbar);

        binding.btn1.setOnClickListener(onClickListener);
        binding.btn2.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = view -> {
        switch (view.getId()) {
            case R.id.btn_1:
                gotoActivity(FirstActivity.class);
                break;
            case R.id.btn2:
                gotoActivity(SecondActivity.class);
                break;
            default:
                break;
        }
    };

    private void gotoActivity(Class<?> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("haha");

        MenuItem shareItem = menu.findItem(R.id.share);
        ShareActionProvider provider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM,"");
        provider.setShareIntent(intent);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorite:
                Snackbar.make(binding.toolbar, "Favorite", Snackbar.LENGTH_LONG).show();
                return true;
            case R.id.settings:
                Snackbar.make(binding.toolbar, "Settings", Snackbar.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
