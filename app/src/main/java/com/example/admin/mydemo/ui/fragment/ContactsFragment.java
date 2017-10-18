package com.example.admin.mydemo.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mydemo.R;
import com.example.admin.mydemo.ui.base.BaseFragment;
import com.example.admin.mydemo.bean.User;
import com.example.admin.mydemo.commen.PoolInstance;
import com.example.admin.mydemo.commen.ShowProgressDialog;
import com.example.admin.mydemo.http.ApiFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author：Marlborn
 * Email：marlborn@foxmail.com
 */

public class ContactsFragment extends BaseFragment implements View.OnClickListener {

    private ProgressDialog progressDialog;
    private Button jsonBtn, uploadBtn, downloadBtn;
    private TextView tv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int setLayout() {
        progressDialog = ShowProgressDialog.getInstance(context);
        return R.layout.find_fragment;
    }

    @Override
    protected void initViews() {
        jsonBtn = bindView(R.id.btn_goto);
        uploadBtn = bindView(R.id.showdata_btn);
        downloadBtn = bindView(R.id.deletedata_btn);
        tv = bindView(R.id.json_tv);
        jsonBtn.setOnClickListener(this);
        uploadBtn.setOnClickListener(this);
        downloadBtn.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        //progressDialog.show();
        PoolInstance.getPoolInstance().startThread(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_goto: {
                progressDialog.show();
                Call<User> userCall = ApiFactory.gitHubAPI().getInfo("");
                userCall.enqueue(new Callback<User>() {

                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.e("ContactsFragment", "success");
                        tv.setText("response.body().Authorizations_url:" +
                                   response.body().getAuthorizations_url() +
                                   "\n" + "response.body().Code_search_url" +
                                   response.body().getCode_search_url() +
                                   "\n" + "response.body().Commit_search_url" +
                                   response.body().getCommit_search_url() +
                                   "\n" + "response.body().Current_user_authorizations_html_url" +
                                   response.body().getCurrent_user_authorizations_html_url() +
                                   "\n" + "response.body().Current_user_repositories_url" +
                                   response.body().getCurrent_user_repositories_url() +
                                   "\n" + "response.body().Hub_url" + response.body().getHub_url() +
                                   "\n" + "response.body().getEmails_url()" +
                                   response.body().getEmails_url()
                        );
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("ContactsFragment", "fail");
                    }

                });
                break;
            }
            case R.id.showdata_btn: {
                Toast.makeText(context, "暂未实现该功能", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.deletedata_btn: {
                Toast.makeText(context, "暂未实现该功能", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}


