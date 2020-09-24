package com.yj.app.view.layoutcomponent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.yj.app.R;
import com.yj.app.databinding.ActivityCopyAndPasteBinding;

import java.util.Objects;

/**
 * 复制和粘贴
 */
public class CopyAndPasteActivity extends AppCompatActivity {

    private ActivityCopyAndPasteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_copy_and_paste);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        binding.btnCopy.setOnClickListener(onClickListener);
        binding.btnPaste.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = view -> {
        switch (view.getId()) {
            case R.id.btn_copy: {
                ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data = ClipData.newPlainText("Copy", binding.etSource.getText().toString());
                assert manager != null;
                manager.setPrimaryClip(data);
            }
            break;
            case R.id.btn_paste: {
                ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                assert manager != null;
//                if(manager.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)){
                    ClipData data = manager.getPrimaryClip();
                    ClipData.Item item = data.getItemAt(0);
                    binding.etTarget.setText(item.getText());
//                }

            }
            break;
        }
    };
}
