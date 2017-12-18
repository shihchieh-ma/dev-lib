package cn.majes.dev_lib_app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;
import java.util.List;

import cn.majes.dev_lib_app.R;
import cn.majes.dev_lib_app.entity.StoryEntity;
import dev.majes.widget.CircleImageView;

/**
 * @author majes
 * @date 12/15/17.
 */

public class StoryFragmentAdapter extends RecyclerView.Adapter<StoryFragmentAdapter.ViewHolder> {

    private List<StoryEntity.DataBean> list;
    private OnRecyclerViewItemClickListener onItemClick;

    public StoryFragmentAdapter(List list) {
        this.list = list;
    }

    private Context context;

    public void setOnItemClick(OnRecyclerViewItemClickListener onItemClick) {
        this.onItemClick = onItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_idea, parent, false);
        ViewHolder vh = new ViewHolder(view);
        context = parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WeakReference<StoryEntity.DataBean> weakReference = new WeakReference(list.get(position));
        StoryEntity.DataBean dataBean = weakReference.get();
        if (!TextUtils.isEmpty(dataBean.getMedia_avatar_url())) {
            Glide.with(context).load(dataBean.getMedia_avatar_url()).into(holder.iv_media);
        }
        if (null != dataBean.getImage_list()) {
            int size = dataBean.getImage_list().size();
            String[] ivs = new String[size];
            for (int i = 0; i < dataBean.getImage_list().size(); i++) {
                ivs[i] = "https:" + dataBean.getImage_list().get(i).getUrl();
            }
            switch (ivs.length) {
                case 1:
                    Glide.with(context).load(ivs[0]).into(holder.iv_0);
                    break;
                case 2:
                    Glide.with(context).load(ivs[0]).into(holder.iv_0);
                    Glide.with(context).load(ivs[1]).into(holder.iv_1);
                    break;
                case 3:
                    Glide.with(context).load(ivs[0]).into(holder.iv_0);
                    Glide.with(context).load(ivs[1]).into(holder.iv_1);
                    Glide.with(context).load(ivs[2]).into(holder.iv_2);
                    break;
                default:
                    break;
            }
            holder.tv_title.setTextSize(16);
            holder.tv_extra.setText(dataBean.getSource() + " - " +
                    dataBean.getComments_count() + "评论" + " - " +
                    dataBean.getBehot_time());
        }
//        if (onItemClick != null) {
//            holder.relativelayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onItemClick.onItemClick(v,dataBean.getSource_open_url());
//                }
//            });
//
//        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout relativelayout;
        private CircleImageView iv_media;
        private TextView tv_extra;
        private TextView tv_title;
        private ImageView iv_0;
        private ImageView iv_1;
        private ImageView iv_2;
        private ImageView iv_dots;

        public ViewHolder(View view) {
            super(view);
            this.relativelayout = view.findViewById(R.id.relativelayout);
            this.iv_media = view.findViewById(R.id.iv_media);
            this.tv_extra = view.findViewById(R.id.tv_extra);
            this.tv_title = view.findViewById(R.id.tv_title);
            this.iv_0 = view.findViewById(R.id.iv_0);
            this.iv_1 = view.findViewById(R.id.iv_1);
            this.iv_2 = view.findViewById(R.id.iv_2);
            this.iv_dots = view.findViewById(R.id.iv_dots);
        }

    }


}
