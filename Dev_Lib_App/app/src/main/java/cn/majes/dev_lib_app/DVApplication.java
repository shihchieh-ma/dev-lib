package cn.majes.dev_lib_app;


import java.util.List;

import dev.majes.base.DevLibApplication;
import dev.majes.base.database.DaoManager;
import dev.majes.base.database.entity.UserBean;
import dev.majes.base.log.Log;

/**
 * @author majes
 * @date 12/13/17.
 */

public class DVApplication extends DevLibApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        testDatabaseSafeUpdate();
    }

    private void testDatabaseSafeUpdate() {
        DaoManager.getDaoManager().insertOrReplace(new UserBean(1l, "2", "3"));
        List<UserBean> list = DaoManager.getDaoManager().queryAll(UserBean.class);
        Log.e("list.size():" + list.size());
    }

}
