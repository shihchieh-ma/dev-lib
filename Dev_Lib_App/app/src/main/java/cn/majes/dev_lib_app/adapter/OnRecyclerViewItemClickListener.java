package cn.majes.dev_lib_app.adapter;

import android.view.View;

/**
 * @author majes
 * @date 12/16/17.
 */

public interface OnRecyclerViewItemClickListener<T> {
    /**
     * item点击事件
     * @param view 点击的view
     */
    void onItemClick(View view ,T data);
}
