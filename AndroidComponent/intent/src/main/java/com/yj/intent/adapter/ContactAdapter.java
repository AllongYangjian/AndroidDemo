package com.yj.intent.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


abstract class BaseAdapter<T, F extends RecyclerView.ViewHolder> extends
        RecyclerView.Adapter<F> {

    protected Context mContext;

    protected ViewDataBinding binding;

    public BaseAdapter(Context mContext) {
        this.mContext = mContext;
    }

    private List<T> mData;

    public void setData(List<T> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public F onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), getLayoutResId(), parent, false);
        return onCreateViewHolder(binding.getRoot());
    }

    /**
     * 获取布局资源ID
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * 创建ViewHolder
     *
     * @param root
     * @return
     */
    protected abstract F onCreateViewHolder(View root);


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public void onBindViewHolder(@NonNull F holder, int position, @NonNull List<Object> payloads) {
        if (payloads.size() == 0) {
            onBindViewHolder(holder, position);
        } else {
            T data = (T) payloads.get(0);
            mData.set(position, data);
            onBindViewHolder(holder, position);
        }
    }
}
