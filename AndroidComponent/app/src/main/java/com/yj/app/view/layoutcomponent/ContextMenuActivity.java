package com.yj.app.view.layoutcomponent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.yj.app.R;
import com.yj.app.databinding.ActivityContextMenuBinding;

import java.util.Objects;

public class ContextMenuActivity extends AppCompatActivity {
    private ActivityContextMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_context_menu);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.item_test_list_data, android.R.layout.simple_list_item_1);
        binding.list.setAdapter(adapter);
        registerForContextMenu(binding.list);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.help:
                Toast.makeText(this, "Help", Toast.LENGTH_LONG).show();
                break;
            case R.id.search:
                Toast.makeText(this, "Search", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }
}
