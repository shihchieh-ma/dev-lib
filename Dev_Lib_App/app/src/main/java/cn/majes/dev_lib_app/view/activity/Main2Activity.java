package cn.majes.dev_lib_app.view.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import cn.majes.dev_lib_app.R;
import cn.majes.dev_lib_app.entity.TestRxBusMsg;
import cn.majes.dev_lib_app.view.fragment.EverFragment;
import cn.majes.dev_lib_app.view.fragment.GankIOFragment;
import cn.majes.dev_lib_app.view.fragment.PicAllFragment;
import cn.majes.dev_lib_app.view.fragment.ProFragment;
import cn.majes.dev_lib_app.view.fragment.StoryFragment;
import dev.majes.base.log.Log;
import dev.majes.base.mvp.BaseActivity;
import dev.majes.base.rxbus.IRxMsg;
import dev.majes.base.rxbus.RxBus;
import dev.majes.base.utils.DPXUtils;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * @author majes
 * @date 12/18/17.
 */

public class Main2Activity extends BaseActivity implements
        RadioGroup.OnCheckedChangeListener {

    private RadioGroup groupTabs;
    private RxFragment gankIoFragment = null;
    private RxFragment picFragment = null;
    private RxFragment stroyFragment = null;
    private RxFragment everFragment = null;
    private RxFragment proFragment = null;
    private RadioButton gankIoFragmentBtn, picFragmentBtn, stroyFragmentBtn, everFragmentBtn, proFragmentBtn;
    private static FragmentManager manager;
    private static FragmentTransaction transaction;


    @Override
    public void initData(Bundle savedInstanceState) {
        groupTabs = findViewById(R.id.group_tabs);
        gankIoFragmentBtn = findViewById(R.id.home);
        picFragmentBtn = findViewById(R.id.pic);
        stroyFragmentBtn = findViewById(R.id.story);
        everFragmentBtn = findViewById(R.id.know);
        proFragmentBtn = findViewById(R.id.camera);
        groupTabs.setOnCheckedChangeListener(this);
        groupTabs.check(R.id.home);
        Drawable db = getResources().getDrawable(R.drawable.select_icon_home);
        Drawable db1 = getResources().getDrawable(R.drawable.select_pic);
        Drawable db2 = getResources().getDrawable(R.drawable.select_icon_story);
        Drawable db3 = getResources().getDrawable(R.drawable.select_icon_knowt);
        Drawable db4 = getResources().getDrawable(R.drawable.select_icon_camera);
        int i = DPXUtils.dip2px(this, 22);
        db.setBounds(0, 0, i, i);
        db1.setBounds(0, 0, i, i);
        db2.setBounds(0, 0, i, i);
        db3.setBounds(0, 0, i, i);
        db4.setBounds(0, 0, i, i);
        gankIoFragmentBtn.setCompoundDrawables(null, db, null, null);
        picFragmentBtn.setCompoundDrawables(null, db1, null, null);
        stroyFragmentBtn.setCompoundDrawables(null, db2, null, null);
        everFragmentBtn.setCompoundDrawables(null, db3, null, null);
        proFragmentBtn.setCompoundDrawables(null, db4, null, null);
        RxBus.getIntanceBus().post(new TestRxBusMsg("this is test msg form main2 activity."));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_second;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction().setCustomAnimations(
                R.anim.fade_in, R.anim.fade_out);
        gankIoFragment = (GankIOFragment) manager.findFragmentByTag("one");
        picFragment = (PicAllFragment) manager.findFragmentByTag("two");
        stroyFragment = (StoryFragment) manager.findFragmentByTag("three");
        everFragment = (EverFragment) manager.findFragmentByTag("four");
        proFragment = (ProFragment) manager.findFragmentByTag("five");
        switch (checkedId) {
            case R.id.home:
                if (gankIoFragment == null) {
                    gankIoFragment = new GankIOFragment();
                    transaction.add(R.id.relatecontent, gankIoFragment, "one");
                } else {
                    transaction.show(gankIoFragment);
                }
                if (picFragment != null) {
                    transaction.hide(picFragment);
                }
                if (stroyFragment != null) {
                    transaction.hide(stroyFragment);
                }
                if (everFragment != null) {
                    transaction.hide(everFragment);
                }
                if (proFragment != null) {
                    transaction.hide(proFragment);
                }
                break;
            case R.id.pic:
                if (picFragment == null) {
                    picFragment = new PicAllFragment();
                    transaction.add(R.id.relatecontent, picFragment, "two");
                } else {
                    transaction.show(picFragment);
                }
                if (gankIoFragment != null) {
                    transaction.hide(gankIoFragment);
                }
                if (stroyFragment != null) {
                    transaction.hide(stroyFragment);
                }
                if (everFragment != null) {
                    transaction.hide(everFragment);
                }
                if (proFragment != null) {
                    transaction.hide(proFragment);
                }
                break;
            case R.id.story:
                if (stroyFragment == null) {
                    stroyFragment = new StoryFragment();
                    transaction.add(R.id.relatecontent, stroyFragment, "three");
                } else {
                    transaction.show(stroyFragment);
                }
                if (gankIoFragment != null) {
                    transaction.hide(gankIoFragment);
                }
                if (picFragment != null) {
                    transaction.hide(picFragment);
                }
                if (everFragment != null) {
                    transaction.hide(everFragment);
                }
                if (proFragment != null) {
                    transaction.hide(proFragment);
                }
                break;
            case R.id.know:
                if (everFragment == null) {
                    everFragment = new EverFragment();
                    transaction.add(R.id.relatecontent, everFragment, "four");
                } else {
                    transaction.show(everFragment);
                }
                if (gankIoFragment != null) {
                    transaction.hide(gankIoFragment);
                }
                if (picFragment != null) {
                    transaction.hide(picFragment);
                }
                if (stroyFragment != null) {
                    transaction.hide(stroyFragment);
                }
                if (proFragment != null) {
                    transaction.hide(proFragment);
                }
                break;
            case R.id.camera:
                if (proFragment == null) {
                    proFragment = new ProFragment();
                    transaction.add(R.id.relatecontent, proFragment, "five");
                } else {
                    transaction.show(proFragment);
                }
                if (gankIoFragment != null) {
                    transaction.hide(gankIoFragment);
                }
                if (picFragment != null) {
                    transaction.hide(picFragment);
                }
                if (stroyFragment != null) {
                    transaction.hide(stroyFragment);
                }
                if (everFragment != null) {
                    transaction.hide(everFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }
}

