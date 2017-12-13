package dev.majes.app.adapter;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import dev.majes.app.R;
import dev.majes.app.ui.activity.StartActivity;

import java.util.ArrayList;


/**
 * Author：Marlborn
 * Email：marlborn@foxmail.com
 */
public class ViewPageAdapter extends PagerAdapter {
    private ArrayList<View> pageViews;
    private AppCompatActivity context;

    public ViewPageAdapter(AppCompatActivity context, ArrayList<View> pageViews) {
        this.pageViews = pageViews;
        this.context = context;
    }

    // 销毁position位置的界面
    @Override
    public void destroyItem(View v, int position, Object arg2) {
        ((ViewPager) v).removeView((View) arg2);
    }

    // 获取当前窗体界面数
    @Override
    public int getCount() {
        return pageViews.size();
    }

    // 初始化position位置的界面
    @Override
    public Object instantiateItem(View v, int position) {
        View contentView = pageViews.get(position);
        Button btn = (Button) contentView.findViewById(R.id.btn_enter);
        ImageView iv_cancel = (ImageView) contentView.findViewById(R.id.img_cancel);
        iv_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, StartActivity.class));
                context.finish();
            }
        });

        ImageView iv = (ImageView) contentView
                .findViewById(R.id.img_navigation_page);
        int resource = R.mipmap.viewpager_1;
        switch (position) {
            case 0:
                resource = R.mipmap.viewpager_1;
                break;
            case 1:
                resource = R.mipmap.viewpager_2;
                break;
            case 2:
                resource = R.mipmap.viewpager_3;
                break;
            case 3:
                resource = R.mipmap.viewpager_4;
                btn.setVisibility(View.INVISIBLE);
                break;
            case 4:
                resource = R.mipmap.viewpager_5;
                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, StartActivity.class));
                        context.finish();
                    }
                });
                break;
            default:
                break;
        }

        iv.setImageResource(resource);

        ((ViewPager) v).addView(contentView, 0);
        return pageViews.get(position);
    }

    @Override
    public boolean isViewFromObject(View v, Object arg1) {
        return v == arg1;
    }

    @Override
    public void startUpdate(View arg0) {
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    private void askPermTurnTo() {

    }
}
