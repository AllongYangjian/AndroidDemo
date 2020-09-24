package com.yj.app.view.layoutcomponent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yj.app.R;
import com.yj.app.databinding.ActivityMenuIntentActivityBinding;

import java.util.Objects;

/**
 * Menu Intent
 * 如果相应的Intent不存在，则menu不会显示出来
 */
public class MenuIntentActivity extends AppCompatActivity {

    private ActivityMenuIntentActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_menu_intent_activity);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        addSendSmsIntent(menu);
        sendCallIntent(menu);
        return super.onCreateOptionsMenu(menu);
    }
    @SuppressLint("IntentReset")
    private void addSendSmsIntent(Menu menu){
        Intent intent =new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.setData(Uri.parse("smsto:18667006178"));
        intent.putExtra("sms_body", "hh");
        menu.addIntentOptions(R.id.intent_group,R.id.menu_add,0,this.getComponentName(),
                null,intent,0,null);
    }


    private void sendCallIntent(Menu menu){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:18667006178"));
        menu.addIntentOptions(R.id.intent_call_phone,R.id.menu_call_phone,1,
                this.getComponentName(), null,intent,0,null);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return super.onContextItemSelected(item);
    }
}
