package org.example.yj.customview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.example.yj.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author yj on  2018/5/16 17:58
 *         邮箱 yj@allong.net
 * @version 1.0.0
 */

public class CommonStringAdapter extends SimpleRecAdapter<String, CommonStringAdapter.ViewHolder> {


    public CommonStringAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView, int viewTpe) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_customview_list;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String text = data.get(position);

        holder.tvTitle.setText(text);

        if (position % 2 == 0) {
            holder.itemView.setBackgroundResource(R.color.color_item_even_color);
        } else {
            holder.itemView.setBackgroundResource(R.color.color_item_odds_color);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRecItemClick() != null) {
                    getRecItemClick().onItemClick(position, text, 0);
                }
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(this);
        }
    }
}
