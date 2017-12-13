package dev.majes.app.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import dev.majes.app.R;
import dev.majes.app.ui.fragment.CallLogsFragment;
import dev.majes.app.ui.fragment.ContactsFragment;
import dev.majes.app.ui.fragment.NewsFragment;
import dev.majes.app.ui.fragment.SmsFragment;
import dev.majes.base.mvp.BaseActivity;
import dev.majes.base.mvp.IPrensenter;
import dev.majes.base.rxbus.IRxMsg;

/**
 * Author：Marlborn
 * Email：marlborn@foxmail.com
 */
public class StartActivity extends BaseActivity implements
        RadioGroup.OnCheckedChangeListener {
    private RadioGroup groupTabs;
    private DrawerLayout drawerLayout;
    private RxFragment newsFragment = null;
    private RxFragment contactsFragment = null;
    private RxFragment callLogsFragment = null;
    private RxFragment smsFragment = null;
    private RadioButton rb1, rb2, rb3, rb4;
    public static FragmentManager manager;
    public static FragmentTransaction transaction;

    @Override
    public int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        groupTabs = findViewById(R.id.group_tabs);
        rb1 = findViewById(R.id.news);
        rb2 = findViewById(R.id.contacts);
        rb3 = findViewById(R.id.call_logs);
        rb4 = findViewById(R.id.sms);
        drawerLayout = findViewById(R.id.activity_start);
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

        groupTabs.setOnCheckedChangeListener(this);
        groupTabs.check(R.id.news);
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {

            // 当我们打开菜单的时候，先执行onDrawerStateChanged，然后不断执行onDrawerSlide，第三步会执行onDrawerOpened，最后执行onDrawerStateChanged
            // 当我们关闭菜单的时候，先执行onDrawerStateChanged，然后不断执行onDrawerSlide，第三步会执行onDrawerClosed，最后执行onDrawerStateChanged
            @Override
            public void onDrawerStateChanged(int newState) {
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

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
                R.anim.fade_in, R.anim.fade_out);
        newsFragment = (NewsFragment) manager.findFragmentByTag("one");
        contactsFragment = (ContactsFragment) manager.findFragmentByTag("two");
        callLogsFragment = (CallLogsFragment) manager.findFragmentByTag("three");
        smsFragment = (SmsFragment) manager.findFragmentByTag("four");
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
                    transaction.add(R.id.replace_view, newsFragment, "one");
                } else {
                    transaction.show(newsFragment);
                }
                break;
            case R.id.contacts:
                if (contactsFragment == null) {
                    contactsFragment = new ContactsFragment();
                    transaction.add(R.id.replace_view, contactsFragment, "two");
                } else {
                    transaction.show(contactsFragment);
                }
                break;
            case R.id.call_logs:
                if (callLogsFragment == null) {
                    callLogsFragment = new CallLogsFragment();
                    transaction.add(R.id.replace_view, callLogsFragment, "three");
                } else {
                    transaction.show(callLogsFragment);
                }
                break;
            case R.id.sms:
                if (smsFragment == null) {
                    smsFragment = new SmsFragment();
                    transaction.add(R.id.replace_view, smsFragment, "four");
                } else {
                    transaction.show(smsFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }


    @Override
    public void onRxBusMsg(IRxMsg iRxMsg) {

    }

    @Override
    protected IPrensenter getCorrespondingP() {
        return null;
    }

    @Override
    public Object getP() {
        return null;
    }
}
