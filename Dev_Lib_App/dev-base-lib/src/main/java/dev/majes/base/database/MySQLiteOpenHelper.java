package dev.majes.base.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import org.greenrobot.greendao.database.Database;

import dev.majes.base.database.db.DaoMaster;
import dev.majes.base.database.db.UserBeanDao;

/**
 * @author majes
 * greendao升级帮助类
 */
public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }


    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        // 数据库的安全升级。
        MigrationHelper.migrate(db, UserBeanDao.class);
    }
}