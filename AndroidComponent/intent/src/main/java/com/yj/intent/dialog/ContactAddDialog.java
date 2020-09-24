package com.yj.intent.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

import androidx.annotation.Nullable;

import com.yj.intent.R;
import com.yj.intent.databinding.DialogContactBinding;
import com.yj.intent.utils.DisplayUtils;

/**
 * 添加联系人对话框
 */
public class ContactAddDialog extends BaseDialog {

    private DialogContactBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mHeight = (int) (DisplayUtils.getScreenHeight(requireContext())*0.3);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_contact;
    }

    @Override
    protected void initView(View view) {
        binding = (DialogContactBinding) root;
        binding.setTitle("添加联系人");
        binding.setCancel("取消");
        binding.setConfirm("添加");

        binding.btnConfirm.setOnClickListener(onClickListener);
        binding.btnCancel.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = view -> {
        switch (view.getId()) {
            case R.id.btn_confirm:
                doAddContact();
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
        }
    };

    /**
     * 添加联系人代码
     */
    private void doAddContact() {
        String name = binding.etName.getText().toString().trim();
        String tel = binding.etTel.getText().toString().trim();

        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, tel);

        if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
            startActivity(intent);
            dismiss();
        }
    }
}
