package com.jiancode.bitmapcache;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * author：yangjian
 * email：yj@allong.net
 * data：2019-06-1520:40
 * desc：
 * version：1.0
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;

    private LayoutInflater mInflater;

    private Drawable mDefaultBitmapDrawable;

    private ImageLoader mImageLoader;

    private int mImageWidth;

    private boolean mIsGridViewIdle;

    public void setmIsGridViewIdle(boolean mIsGridViewIdle) {
        this.mIsGridViewIdle = mIsGridViewIdle;
    }

    public ImageAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        mDefaultBitmapDrawable = mContext.getResources().getDrawable(R.mipmap.ic_launcher_round);
        mImageLoader = ImageLoader.build(mContext);

        int screenWidth = Utils.getMetrics(mContext).widthPixels;
        int space = Utils.dp2px(mContext, 20);
        mImageWidth = (screenWidth - space) / 3;
    }

    private List<String> mUrlList;

    public void setmUrlList(List<String> mUrlList) {
        this.mUrlList = mUrlList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mUrlList != null)
            return mUrlList.size();
        return 0;
    }

    @Override
    public String getItem(int position) {
        return mUrlList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_image_list, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final String tag = (String) holder.imageView.getTag();
        final String url = getItem(position);
        if (!url.equals(tag)) {
            holder.imageView.setImageDrawable(mDefaultBitmapDrawable);
        }
        if (mIsGridViewIdle) {
            holder.imageView.setTag(url);
            mImageLoader.bindBitmap(url, holder.imageView, mImageWidth, mImageWidth);
        }
        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;

        public ViewHolder(View item) {
            imageView = item.findViewById(R.id.image);
            item.setTag(this);
        }
    }
}
