package com.yj.app.view.layoutcomponent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.yj.app.R;
import com.yj.app.databinding.ActivityToastBinding;

import java.util.Objects;

/**
 * 吐司及自定义吐司
 */
public class ToastActivity extends AppCompatActivity {

    private ActivityToastBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_toast);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        binding.btnToast.setOnClickListener(onClickListener);
        binding.btnCustomToast.setOnClickListener(onClickListener);
        binding.btnSnackBar.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = view -> {
        switch (view.getId()) {
            case R.id.btn_toast:
                toast("普通的Toast");
                break;
            case R.id.btn_custom_toast:
                customToast();
                break;
            case R.id.btn_snack_bar:
                showSnackBar();
                break;
            default:
                break;
        }
    };

    private void showSnackBar(){
        Snackbar snackbar = Snackbar.make(binding.coordinatorLayout,"SnackBar",Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.add, v->{
            toast("添加成功");
        });
        snackbar.show();
    }

    private void toast(String tips) {
        Toast.makeText(this, tips, Toast.LENGTH_LONG).show();
    }

    private void customToast() {
        View view = View.inflate(this, R.layout.view_toast_layout, null);
        TextView tv = view.findViewById(R.id.tv_toast_tips);
        tv.setText("自定义Toast");
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }
}
