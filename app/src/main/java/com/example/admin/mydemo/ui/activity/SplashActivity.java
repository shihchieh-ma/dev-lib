package com.example.admin.mydemo.ui.activity;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.EdgeEffectCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.admin.mydemo.R;
import com.example.admin.mydemo.adapter.ViewPageAdapter;
import com.example.admin.mydemo.ui.base.BaseActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Marl_Jar on 2017/6/6.
 */

public class SplashActivity extends BaseActivity {
    private ViewPager mViewPager;
    /** 装分页显示的view的数组 */
    private ArrayList<View> pageViews;
    private ImageView imageView;

    /** 将小圆点的图片用数组表示 */
    private ImageView[] imageViews;

    // 包裹小圆点的LinearLayout
    private LinearLayout mViewPoints;

    private EdgeEffectCompat leftEdge;
    private EdgeEffectCompat rightEdge;
    @Override
    protected void simpleInit() {

    }

    @Override
    protected int setLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews() {
        // 将要分页显示的View装入数组中
        pageViews = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            pageViews.add(getLayoutInflater().inflate(R.layout.activity_splash_circle,
                    null));
        }
        // 实例化小圆点的linearLayout和viewpager
        mViewPoints = bindView(R.id.viewGroup);
        mViewPager = bindView(R.id.navigation_page);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initData() {
        try {
            Field leftEdgeField = mViewPager.getClass().getDeclaredField("mLeftEdge");
            Field rightEdgeField = mViewPager.getClass().getDeclaredField("mRightEdge");
            if (leftEdgeField != null && rightEdgeField != null) {
                leftEdgeField.setAccessible(true);
                rightEdgeField.setAccessible(true);
                leftEdge = (EdgeEffectCompat) leftEdgeField.get(mViewPager);
                rightEdge = (EdgeEffectCompat) rightEdgeField.get(mViewPager);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 创建imageviews数组，大小是要显示的图片的数量
        imageViews = new ImageView[pageViews.size()];
        // 添加小圆点的图片
        for (int i = 0; i < pageViews.size(); i++) {
            imageView = new ImageView(this);
            // 设置小圆点imageview的参数
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    10, 10);
            layoutParams.setMargins(15, 0, 15, 0);
            imageView.setLayoutParams(layoutParams);// 创建一个宽高均为20 的布局
            // 将小圆点layout添加到数组中
            imageViews[i] = imageView;
            // 默认选中的是第一张图片，此时第一个小圆点是选中状态，其他不是
            if (i == 0) {
                imageViews[i]
                        .setBackgroundResource(R.mipmap.greycricle);
            } else {
                imageViews[i]
                        .setBackgroundResource(R.mipmap.bluecircle);
            }

            // 将imageviews添加到小圆点视图组
            mViewPoints.addView(imageViews[i]);
        }
        // 设置viewpager的适配器和监听事件
        mViewPager.setAdapter(new ViewPageAdapter(this,pageViews));
        mViewPager.setOnPageChangeListener(new NavigationPageChangeListener());

    }

    private class NavigationPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            if (leftEdge != null && rightEdge != null) {
                leftEdge.finish();
                rightEdge.finish();
                leftEdge.setSize(0, 0);
                rightEdge.setSize(0, 0);
            }
        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[position]
                        .setBackgroundResource(R.mipmap.bluecircle);
                // 不是当前选中的page，其小圆点设置为未选中的状态
                if (position != i) {
                    imageViews[i]
                            .setBackgroundResource(R.mipmap.greycricle);
                }
            }
        }

    }
}
