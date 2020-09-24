package com.yj.app.view.layoutcomponent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yj.app.R;
import com.yj.app.databinding.ActivityMenuBinding;

import java.util.Objects;

public class MenuActivity extends AppCompatActivity {

    private ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
        binding.btnContextMenu.setOnClickListener(onClickListener);
        binding.btnContextMode.setOnClickListener(onClickListener);
        binding.btnPopupMenu.setOnClickListener(onClickListener);
        binding.btnMenuIntent.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = view -> {
        switch (view.getId()) {
            case R.id.btn_context_menu:
                    gotoActivity(ContextMenuActivity.class);
                break;
            case R.id.btn_context_mode:
                gotoActivity(ContextModeActivity.class);
                break;
            case R.id.btn_popup_menu:
                gotoActivity(PopupMenuActivity.class);
                break;
            case R.id.btn_menu_intent:
                gotoActivity(MenuIntentActivity.class);
                break;
            default:
                break;
        }
    };

    private void gotoActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
