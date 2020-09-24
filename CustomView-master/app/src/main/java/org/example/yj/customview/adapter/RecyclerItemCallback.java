package org.example.yj.customview.adapter;

/**
 * @author yj on  2017/5/4 15:47
 *         邮箱 yj@allong.net
 * @version 1.0.0
 */

public abstract class RecyclerItemCallback<T,F> {
    /**
     * 单击事件
     *
     * @param position 位置
     * @param model    实体
     * @param tag      标签
     */
    public void onItemClick( int position, T model, int tag) {
    }

    /**
     * 长按事件
     *
     * @param position 位置
     * @param model    实体
     * @param tag      标签
     */
    public void onItemLongClick(int position, T model, int tag) {
    }
}
