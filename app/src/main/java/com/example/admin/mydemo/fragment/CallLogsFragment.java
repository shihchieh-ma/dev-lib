package com.example.admin.mydemo.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.mydemo.R;
import com.example.admin.mydemo.base.BaseFragment;
import com.example.admin.mydemo.bean.UserBean;
import com.example.admin.mydemo.db.UserDbManager;
import com.example.admin.mydemo.design.ShowProgressDialog;

import java.util.Iterator;
import java.util.List;


/**
 * Author：Marlborn
 * Email：marlborn@foxmail.com
 */
public class CallLogsFragment extends BaseFragment implements View.OnClickListener {
    private ProgressDialog progressDialog;
    private Button showdata_btn, btn_goto, deletedata_btn;
    private EditText userCounty, userName;
    private List<UserBean> userList;
    UserDbManager userDbManager = new UserDbManager();

    @Override
    protected int setLayout() {
        progressDialog = ShowProgressDialog.getInstance(context);
        return R.layout.personal_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void initViews() {
        showdata_btn = bindView(R.id.showdata_btn);
        deletedata_btn = bindView(R.id.deletedata_btn);
        btn_goto = bindView(R.id.btn_goto);
        userCounty = bindView(R.id.usercounty);
        userName = bindView(R.id.username);
    }

    @Override
    protected void initDatas() {
        showdata_btn.setOnClickListener(this);
        btn_goto.setOnClickListener(this);
        deletedata_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.showdata_btn: {
                userList = userDbManager.loadAll();
                String strs = "";
                Iterator iter = userList.iterator();
                while (iter.hasNext()) {
                    UserBean ub = (UserBean) iter.next();
                    strs = strs + "UserCounty" + ub.getUserCounty() + "UserName" +
                           ub.getUserName();
                }

                Toast.makeText(context, "数据:" + strs, Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.btn_goto: {
                String uc = userCounty.getText().toString();
                String un = userName.getText().toString();
                UserBean userBean = new UserBean();
                userBean.setUserCounty(uc);
                userBean.setUserName(un);
                userDbManager.insertOrReplace(userBean);
                Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.deletedata_btn: {
                userDbManager.deleteAll();
                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}
