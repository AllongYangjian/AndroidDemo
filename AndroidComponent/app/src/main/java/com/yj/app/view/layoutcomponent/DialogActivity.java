package com.yj.app.view.layoutcomponent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.yj.app.R;
import com.yj.app.databinding.ActivityDialogBinding;
import com.yj.app.widget.DialogCustom;
import com.yj.app.widget.DialogCustom2;
import com.yj.app.widget.DialogFragmentWithAlertDialog;
import com.yj.app.widget.DialogMultiChoiceItems;
import com.yj.app.widget.DialogSingleSelect;

import java.util.Objects;

public class DialogActivity extends AppCompatActivity {

    private ActivityDialogBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dialog);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
        binding.btnDialog1.setOnClickListener(onClickListener);
        binding.btnDialog2.setOnClickListener(onClickListener);
        binding.btnDialog3.setOnClickListener(onClickListener);
        binding.btnDialog4.setOnClickListener(onClickListener);
        binding.btnDialog5.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = view -> {
        switch (view.getId()) {
            case R.id.btn_dialog_1:
                showDialogWithAlertDialog();
                break;
            case R.id.btn_dialog_2:
                showSingleSelectDialog();
                break;
            case R.id.btn_dialog_3:
                showMultiplyDialog();
                break;
            case R.id.btn_dialog_4:
                showCustomDialog();
                break;
            case R.id.btn_dialog_5:
                showCustomDialog2();
                break;
            default:
                break;
        }
    };

    private void showDialogWithAlertDialog() {
        DialogFragmentWithAlertDialog dialog = new DialogFragmentWithAlertDialog();
        dialog.show(getSupportFragmentManager(), dialog.getTag());
    }

    private void showSingleSelectDialog() {
        DialogSingleSelect dialog = new DialogSingleSelect();
        dialog.show(getSupportFragmentManager(), dialog.getTag());
    }

    private void showMultiplyDialog() {
        DialogMultiChoiceItems dialog = new DialogMultiChoiceItems();
        dialog.show(getSupportFragmentManager(), dialog.getTag());
    }

    private void showCustomDialog() {
        DialogCustom dialog = new DialogCustom();
        dialog.setListener((username, password) -> Toast.makeText(this, "用户名：" + username + "，密码:" + password,
                Toast.LENGTH_LONG).show());
        dialog.show(getSupportFragmentManager(), dialog.getTag());
    }

    private void showCustomDialog2() {
        DialogCustom2 dialog = new DialogCustom2();
        if (true) {
            FragmentTransaction ta = getSupportFragmentManager().beginTransaction();
            ta.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ta.add(android.R.id.content, dialog)
                    .addToBackStack(null).commit();
        } else {
            dialog.show(getSupportFragmentManager(), dialog.getTag());
        }
    }


}
