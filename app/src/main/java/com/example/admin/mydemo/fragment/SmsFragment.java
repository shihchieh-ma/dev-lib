package com.example.admin.mydemo.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.mydemo.R;
import com.example.admin.mydemo.base.BaseFragment;
import com.example.admin.mydemo.design.PoolInstance;
import com.example.admin.mydemo.design.ShowProgressDialog;
import com.example.admin.mydemo.main.UserActivity;
import com.example.admin.mydemo.utils.Logs2File;
import com.example.admin.mydemo.utils.SharedPreferencesUtils;


/**
 * Author：Marlborn
 * Email：marlborn@foxmail.com
 */
public class SmsFragment extends BaseFragment implements View.OnClickListener {
    private ProgressDialog progressDialog;
    private SurfaceView surfaceview;
    private Button btnGo;
    private MediaPlayer mediaPlayer;
    private EditText phoneNum;
    private int postion = 0;
    private String tempNum;
    @Override
    protected int setLayout() {
        progressDialog = ShowProgressDialog.getInstance(context);
        return R.layout.fragment_sms;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void initViews() {
        surfaceview = bindView(R.id.surfaceView);
        btnGo = bindView(R.id.btn_goto);
        phoneNum = bindView(R.id.phonenum);
    }

    @Override
    protected void initDatas() {
        progressDialog.show();
        PoolInstance.getPoolInstance().startThread(new Runnable() {
            @Override
            public void run() {
                getSmsInPhone();
            }
        });
        surfaceview.setBackgroundResource(R.mipmap.viewpager_2);
        btnGo.setOnClickListener(this);

    }
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 101) {
                progressDialog.dismiss();

            }
            return false;
        }
    });

    public void getSmsInPhone() {
            Message message = Message.obtain();
            message.what = 101;
            message.obj = "DONE";
            handler.sendMessage(message);
        }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_goto:
                tempNum = phoneNum.getText().toString();
                if (null != tempNum) {
                    SharedPreferencesUtils.setParam(getActivity(), "user", tempNum);
                    Logs2File.logE("SmsFragment", "用户名已存储");
                    getActivity().startActivity(
                            new Intent(getActivity(), UserActivity.class)
                    );
                }
                break;
            case R.id.phonenum:
                break;
            default:
                break;
        }
    }
}


