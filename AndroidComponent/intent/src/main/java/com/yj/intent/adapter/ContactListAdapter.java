package com.yj.intent.adapter;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.yj.intent.R;
import com.yj.intent.databinding.ItemContactListBinding;
import com.yj.intent.domain.ContactInfo;

/**
 * 联系人适配器
 */
public class ContactListAdapter extends BaseAdapter<ContactInfo, ContactListAdapter.ViewHolder> {

    public ContactListAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.item_contact_list;
    }

    @Override
    protected ViewHolder onCreateViewHolder(View root) {
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ItemContactListBinding viewBinding;

        ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            viewBinding = (ItemContactListBinding) binding;
        }

    }
}
