package dev.majes.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author majes
 * @date 12/17/17.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    View convertView;
    Context context;

    public BaseViewHolder(View itemView, Context context) {
        super(itemView);
        this.convertView = itemView;
        this.context = context;
    }

}