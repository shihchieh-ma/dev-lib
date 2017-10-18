package com.example.admin.mydemo.ui.activity;

import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.admin.mydemo.R;
import com.example.admin.mydemo.ui.base.BaseActivity;
import com.example.admin.mydemo.ui.base.BaseFragment;
import com.example.admin.mydemo.ui.fragment.CallLogsFragment;
import com.example.admin.mydemo.ui.fragment.ContactsFragment;
import com.example.admin.mydemo.ui.fragment.NewsFragment;
import com.example.admin.mydemo.ui.fragment.SmsFragment;
import com.nineoldandroids.view.ViewHelper;

/**
 * Author：Marlborn
 * Email：marlborn@foxmail.com
 */
public class StartActivity extends BaseActivity implements
        RadioGroup.OnCheckedChangeListener {
    private RadioGroup groupTabs;
    private DrawerLayout drawerLayout;
    private NewsFragment newsFragment = null;
    private ContactsFragment contactsFragment = null;
    private CallLogsFragment callLogsFragment = null;
    private SmsFragment smsFragment = null;
    private RadioButton rb1, rb2, rb3, rb4;
    public static FragmentManager manager;
    public static FragmentTransaction transaction;
    @Override
    protected void simpleInit() {
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_start;
    }

    @Override
    protected void initViews() {
        groupTabs = bindView(R.id.group_tabs);
        rb1 = bindView(R.id.news);
        rb2 = bindView(R.id.contacts);
        rb3 = bindView(R.id.call_logs);
        rb4 = bindView(R.id.sms);
        //定义底部标签图片大小
        Drawable db = getResources().getDrawable(R.drawable.more_btn_btm_selector);
        Drawable db1 = getResources().getDrawable(R.drawable.recmd_btn_btm_selector);
        Drawable db2 = getResources().getDrawable(R.drawable.information_btn_btm_selector);
        Drawable db3 = getResources().getDrawable(R.drawable.btn_btm_selector);
        db.setBounds(0, 0, 70, 70);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        db1.setBounds(0, 0, 70, 70);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        db2.setBounds(0, 0, 70, 70);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        db3.setBounds(0, 0, 70, 70);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        rb1.setCompoundDrawables(null, db, null, null);//只放上面
        rb2.setCompoundDrawables(null, db1, null, null);//只放上面
        rb3.setCompoundDrawables(null, db2, null, null);//只放上面
        rb4.setCompoundDrawables(null, db3, null, null);//只放上面
        drawerLayout = bindView(R.id.activity_start);
    }

    @Override
    protected void setListeners() {
        groupTabs.setOnCheckedChangeListener(this);
        groupTabs.check(R.id.news);
    }

    @Override
    protected void initData() {
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {

            // 当我们打开菜单的时候，先执行onDrawerStateChanged，然后不断执行onDrawerSlide，第三步会执行onDrawerOpened，最后执行onDrawerStateChanged
            // 当我们关闭菜单的时候，先执行onDrawerStateChanged，然后不断执行onDrawerSlide，第三步会执行onDrawerClosed，最后执行onDrawerStateChanged
            @Override
            public void onDrawerStateChanged(int newState) {
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                slideAnim(drawerView, slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                Log.i("lenve", "onDrawerOpened");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Log.i("lenve", "onDrawerClosed");
            }
        });

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction().setCustomAnimations(
                android.R.anim.fade_in, R.anim.fade_out);
        newsFragment = (NewsFragment) manager.findFragmentByTag(BaseFragment.ONE);
        contactsFragment = (ContactsFragment) manager.findFragmentByTag(BaseFragment.TWO);
        callLogsFragment = (CallLogsFragment) manager.findFragmentByTag(BaseFragment.THREE);
        smsFragment = (SmsFragment) manager.findFragmentByTag(BaseFragment.FOUR);
        if (newsFragment != null) {
            transaction.hide(newsFragment);
        }
        if (contactsFragment != null) {
            transaction.hide(contactsFragment);
        }
        if (callLogsFragment != null) {
            transaction.hide(callLogsFragment);
        }
        if (smsFragment != null) {
            transaction.hide(smsFragment);
        }
        switch (i) {
            case R.id.news:
                if (newsFragment == null) {
                    newsFragment = new NewsFragment();
                    transaction.add(R.id.replace_view, newsFragment, BaseFragment.ONE);
                } else {
                    transaction.show(newsFragment);
                }
                break;
            case R.id.contacts:
                if (contactsFragment == null) {
                    contactsFragment = new ContactsFragment();
                    transaction.add(R.id.replace_view, contactsFragment, BaseFragment.TWO);
                } else {
                    transaction.show(contactsFragment);
                }
                break;
            case R.id.call_logs:
                if (callLogsFragment == null) {
                    callLogsFragment = new CallLogsFragment();
                    transaction.add(R.id.replace_view, callLogsFragment, BaseFragment.THREE);
                } else {
                    transaction.show(callLogsFragment);
                }
                break;
            case R.id.sms:
                if (smsFragment == null) {
                    smsFragment = new SmsFragment();
                    transaction.add(R.id.replace_view, smsFragment, BaseFragment.FOUR);
                } else {
                    transaction.show(smsFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void slideAnim(View drawerView, float slideOffset) {
        View contentView = drawerLayout.getChildAt(0);
        // slideOffset表示菜单项滑出来的比例，打开菜单时取值为0->1,关闭菜单时取值为1->0
        float scale = 1 - slideOffset;
        float rightScale = 0.8f + scale * 0.2f;
        float leftScale = 1 - 0.3f * scale;

        ViewHelper.setScaleX(drawerView, leftScale);
        ViewHelper.setScaleY(drawerView, leftScale);
        ViewHelper.setAlpha(drawerView, 0.6f + 0.4f * (1 - scale));
        ViewHelper.setTranslationX(contentView, drawerView.getMeasuredWidth()
                                                * (1 - scale));
        ViewHelper.setPivotX(contentView, 0);
        ViewHelper.setPivotY(contentView, contentView.getMeasuredHeight() / 2);
        contentView.invalidate();
        ViewHelper.setScaleX(contentView, rightScale);
        ViewHelper.setScaleY(contentView, rightScale);
    }

}
