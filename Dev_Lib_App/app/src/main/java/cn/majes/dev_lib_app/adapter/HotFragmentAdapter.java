package cn.majes.dev_lib_app.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import cn.majes.dev_lib_app.R;
import cn.majes.dev_lib_app.entity.HotEntity;
import dev.majes.base.log.Log;

/**
 * @author majes
 * @date 12/15/17.
 */

public class HotFragmentAdapter extends RecyclerView.Adapter<HotFragmentAdapter.HotViewHolder> {

    private List<HotEntity.ResultsBean> list;
    private OnRecyclerViewItemClickListener onItemClick;

    public HotFragmentAdapter(List list) {
        this.list = list;
    }

    private Context context;

    public void setOnItemClick(OnRecyclerViewItemClickListener onItemClick) {
        this.onItemClick = onItemClick;
    }

    @Override
    public HotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot, parent, false);
        HotViewHolder vh = new HotViewHolder(view);
        context = parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(HotViewHolder holder, int position) {
//        final ObjectAnimator anim = ObjectAnimator.ofInt(holder.iv, "ImageLevel", 0, 10000);
//        anim.setDuration(800);
//        anim.setRepeatCount(ObjectAnimator.INFINITE);
//        anim.start();
        WeakReference<HotEntity.ResultsBean> weakReference = new WeakReference(list.get(position));
        HotEntity.ResultsBean resultsBean = weakReference.get();
        Glide.with(context).load(resultsBean.getUrl()).into(holder.iv);
        if (onItemClick != null) {
            holder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onItemClick(v,resultsBean.getUrl());
                }
            });
        }
//        final WeakReference<Bitmap> wrBitmap = null;
//        Glide.with(context).load(resultsBean.getUrl()).asBitmap().into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                WeakReference<Bitmap> wrBitmap = new WeakReference(resource);
//                Bitmap bitmap = wrBitmap.get();
//                holder.iv.setImageBitmap(bitmap);
//                if (onItemClick != null) {
//                    holder.iv.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            TransactionTooLargeException
//                            try {
//                                WeakReference<ByteArrayOutputStream> baos = new WeakReference(new ByteArrayOutputStream());
//                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos.get());
//                                byte[] bitmapByte = baos.get().toByteArray();
//                                onItemClick.onItemClick(v, bitmapByte);
//                                baos.get().close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//
//                }
//            }
//        });

//        Glide.with(context).load(resultsBean.getUrl()).asBitmap()

//                .placeholder(R.drawable.rotate_pro)
//                .crossFade()
//                .listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        anim.cancel();
//                        Log.e(e);
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        anim.cancel();
//                        return false;
//                    }
//                })
//                .into(holder.iv);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class HotViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;

        public HotViewHolder(View view) {
            super(view);
            iv = view.findViewById(R.id.item_pv);
        }

    }


}
