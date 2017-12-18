package cn.majes.dev_lib_app.view.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;

import cn.majes.dev_lib_app.R;
import dev.majes.base.log.Log;
import dev.majes.base.mvp.BaseActivity;
import dev.majes.base.rxbus.IRxMsg;
import dev.majes.widget.photoview.PhotoView;

/**
 * @author majes
 * @date 12/16/17.
 */

public class BigPicActivity extends BaseActivity {

    public static final String BIGPIC_KEY = "BIGPIC_KEY";
    private PhotoView photoView;


    @Override
    public int getLayoutId() {
        return R.layout.activity_bigpic;
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        photoView = findViewById(R.id.photoview);
        Log.e(getIntent().getStringExtra(BIGPIC_KEY));
//        byte[] bis = getIntent().getByteArrayExtra(BIGPIC_KEY);
//        Glide.with(this).load(BitmapFactory.decodeByteArray(bis, 0, bis.length)).into(photoView);
        Glide.with(this).load(getIntent().getStringExtra(BIGPIC_KEY)).into(photoView);
    }

}
