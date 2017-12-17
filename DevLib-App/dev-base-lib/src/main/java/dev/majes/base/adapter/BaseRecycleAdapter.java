package dev.majes.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author majes
 * @date 12/17/17.
 */

public abstract class BaseRecycleAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private int layoutId;
    private List<? extends BaseBean> data;
    private Context context;
    //单击事件
    private OnItemClickListner onItemClickListner;
    //长按单击事件
    private OnItemLongClickListner onItemLongClickListner;
    //单击事件和长单击事件的屏蔽标识
    private boolean clickFlag = true;

    /**
     *
     * @param context //上下文
     * @param layoutId  //布局id
     * @param data  //数据源
     */
    public BaseRecycleAdapter(Context context, int layoutId, List<? extends BaseBean> data) {
        this.layoutId = layoutId;
        this.data = data;
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(layoutId, parent, false);
        final BaseViewHolder holder = new BaseViewHolder(v, context);
        //单击事件回调
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickFlag) {
                    onItemClickListner.onItemClickListner(v,holder.getLayoutPosition());
                }
                clickFlag = true;
            }
        });
        //单击长按事件回调
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemLongClickListner.onItemLongClickListner(v,holder.getLayoutPosition());
                clickFlag = false;
                return false;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        convert(holder, data.get(position));
    }

    protected abstract <T extends BaseBean> void convert(BaseViewHolder holder, T bean);

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public void setOnItemLongClickListner(OnItemLongClickListner onItemLongClickListner) {
        this.onItemLongClickListner = onItemLongClickListner;
    }

    public interface OnItemClickListner {
        void onItemClickListner(View v,int position);
    }

    public interface OnItemLongClickListner {
        void onItemLongClickListner(View v,int position);
    }
}
