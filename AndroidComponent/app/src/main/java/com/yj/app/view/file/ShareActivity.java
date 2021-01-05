package com.yj.app.view.file;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.print.PrintHelper;

import com.yj.app.R;
import com.yj.app.databinding.ActivityShareBinding;
import com.yj.app.databinding.ActivityTemplateBinding;

import java.util.Objects;

public class ShareActivity extends AppCompatActivity {

    private ActivityShareBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_share);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView(){
        binding.btnShare.setOnClickListener(v->{
            String txt = binding.etContent.getText().toString().trim();
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT,txt);
            intent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(intent,"分享");
            startActivity(shareIntent);
        });

        binding.btnShareImg.setOnClickListener(v->{
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
//            intent.putExtra(Intent.EXTRA_STREAM,)
        });

        binding.btnPrint.setOnClickListener(v->{
            PrintHelper photoPrinter = new PrintHelper(this);
            photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                    R.mipmap.ic_launcher);
            photoPrinter.printBitmap("droids.jpg - test print", bitmap);
        });
    }


}
