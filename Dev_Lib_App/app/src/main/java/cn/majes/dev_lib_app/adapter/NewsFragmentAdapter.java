package cn.majes.dev_lib_app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;
import java.util.List;

import cn.majes.dev_lib_app.R;
import cn.majes.dev_lib_app.entity.NewsEntity;
import dev.majes.base.log.Log;

/**
 * @author majes
 * @date 12/15/17.
 */

public class NewsFragmentAdapter extends RecyclerView.Adapter<NewsFragmentAdapter.ViewHolder>{

    private List<NewsEntity.ResultsBean> list;
    private OnRecyclerViewItemClickListener onItemClick;

    public NewsFragmentAdapter(List list) {
        this.list = list;
    }
    private Context context;

    public void setOnItemClick(OnRecyclerViewItemClickListener onItemClick){
        this.onItemClick = onItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ft, parent, false);
        ViewHolder vh = new ViewHolder(view);
        context = parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WeakReference<NewsEntity.ResultsBean> weakReference = new WeakReference(list.get(position));
        NewsEntity.ResultsBean resultsBean = weakReference.get();
        holder.des.setText(resultsBean.getDesc());
        holder.author.setText(resultsBean.getWho());
        holder.publishTime.setText(resultsBean.getPublishedAt());
        if (null != resultsBean.getImages()){
            Glide.with(context).load(resultsBean.getImages().get(0)).into(holder.iv);
        }
        holder.des.setText(resultsBean.getDesc());
        if (onItemClick != null) {
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onItemClick(v,resultsBean.getUrl());
                }
            });

        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
            {
        private TextView author, publishTime, des;
        private ImageView iv;
        private LinearLayout linearLayout;
        public ViewHolder(View view) {
            super(view);
            linearLayout = view.findViewById(R.id.item_layout);
            author = view.findViewById(R.id.tv_author);
            publishTime = view.findViewById(R.id.publish_time);
            des = view.findViewById(R.id.des);
            iv = view.findViewById(R.id.maybe_haveimg);
        }

    }


}
