package com.example.admin.mydemo.db;

import com.example.admin.mydemo.base.BaseManager;
import com.example.admin.mydemo.bean.UserBean;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by Marl_Jar on 2017/6/8.
 */

public class UserDbManager extends BaseManager<UserBean,Long> {
    @Override
    public AbstractDao<UserBean, Long> getAbstractDao() {
        return daoSession.getUserBeanDao();
    }
}
