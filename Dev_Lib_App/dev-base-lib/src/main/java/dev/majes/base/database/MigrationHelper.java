package dev.majes.base.database;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.internal.DaoConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数据库的安全升级。
 * @author 网上的
 * @date 12/11/17.
 */

public class MigrationHelper {
    public static boolean DEBUG = false;
    private static String TAG = "MigrationHelper";
    private static final String SQLITE_MASTER = "sqlite_master";
    private static final String SQLITE_TEMP_MASTER = "sqlite_temp_master";

    public MigrationHelper() {
    }

    public static void migrate(SQLiteDatabase db, Class... daoClasses) {
        printLog("【The Old Database Version】" + db.getVersion());
        Database database = new StandardDatabase(db);
        migrate((Database)database, daoClasses);
    }

    public static void migrate(Database database, Class... daoClasses) {
        printLog("【Generate temp table】start");
        generateTempTables(database, daoClasses);
        printLog("【Generate temp table】complete");
        dropAllTables(database, true, daoClasses);
        createAllTables(database, false, daoClasses);
        printLog("【Restore data】start");
        restoreData(database, daoClasses);
        printLog("【Restore data】complete");
    }

    private static void generateTempTables(Database db, Class... daoClasses) {
        for(int i = 0; i < daoClasses.length; ++i) {
            String tempTableName = null;
            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
            String tableName = daoConfig.tablename;
            if(!isTableExists(db, false, tableName)) {
                printLog("【New Table】" + tableName);
            } else {
                try {
                    tempTableName = daoConfig.tablename.concat("_TEMP");
                    StringBuilder dropTableStringBuilder = new StringBuilder();
                    dropTableStringBuilder.append("DROP TABLE IF EXISTS ").append(tempTableName).append(";");
                    db.execSQL(dropTableStringBuilder.toString());
                    StringBuilder insertTableStringBuilder = new StringBuilder();
                    insertTableStringBuilder.append("CREATE TEMPORARY TABLE ").append(tempTableName);
                    insertTableStringBuilder.append(" AS SELECT * FROM ").append(tableName).append(";");
                    db.execSQL(insertTableStringBuilder.toString());
                    printLog("【Table】" + tableName + "\n ---Columns-->" + getColumnsStr(daoConfig));
                    printLog("【Generate temp table】" + tempTableName);
                } catch (SQLException var8) {
                    Log.e(TAG, "【Failed to generate temp table】" + tempTableName, var8);
                }
            }
        }

    }

    private static boolean isTableExists(Database db, boolean isTemp, String tableName) {
        if(db != null && !TextUtils.isEmpty(tableName)) {
            String dbName = isTemp?"sqlite_temp_master":"sqlite_master";
            String sql = "SELECT COUNT(*) FROM " + dbName + " WHERE type = ? AND name = ?";
            Cursor cursor = null;
            int count = 0;

            try {
                cursor = db.rawQuery(sql, new String[]{"table", tableName});
                if(cursor == null || !cursor.moveToFirst()) {
                    boolean var7 = false;
                    return var7;
                }

                count = cursor.getInt(0);
            } catch (Exception var11) {
                var11.printStackTrace();
            } finally {
                if(cursor != null) {
                    cursor.close();
                }

            }

            return count > 0;
        } else {
            return false;
        }
    }

    private static String getColumnsStr(DaoConfig daoConfig) {
        if(daoConfig == null) {
            return "no columns";
        } else {
            StringBuilder builder = new StringBuilder();

            for(int i = 0; i < daoConfig.allColumns.length; ++i) {
                builder.append(daoConfig.allColumns[i]);
                builder.append(",");
            }

            if(builder.length() > 0) {
                builder.deleteCharAt(builder.length() - 1);
            }

            return builder.toString();
        }
    }

    private static void dropAllTables(Database db, boolean ifExists, @NonNull Class... daoClasses) {
        reflectMethod(db, "dropTable", ifExists, daoClasses);
        printLog("【Drop all table】");
    }

    private static void createAllTables(Database db, boolean ifNotExists, @NonNull Class... daoClasses) {
        reflectMethod(db, "createTable", ifNotExists, daoClasses);
        printLog("【Create all table】");
    }

    private static void reflectMethod(Database db, String methodName, boolean isExists, @NonNull Class... daoClasses) {
        if(daoClasses.length >= 1) {
            try {
                Class[] var4 = daoClasses;
                int var5 = daoClasses.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    Class cls = var4[var6];
                    Method method = cls.getDeclaredMethod(methodName, new Class[]{Database.class, Boolean.TYPE});
                    method.invoke((Object)null, new Object[]{db, Boolean.valueOf(isExists)});
                }
            } catch (NoSuchMethodException var9) {
                var9.printStackTrace();
            } catch (InvocationTargetException var10) {
                var10.printStackTrace();
            } catch (IllegalAccessException var11) {
                var11.printStackTrace();
            }

        }
    }

    private static void restoreData(Database db, Class... daoClasses) {
        for(int i = 0; i < daoClasses.length; ++i) {
            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
            String tableName = daoConfig.tablename;
            String tempTableName = daoConfig.tablename.concat("_TEMP");
            if(isTableExists(db, true, tempTableName)) {
                try {
                    List<String> columns = getColumns(db, tempTableName);
                    ArrayList<String> properties = new ArrayList(columns.size());

                    for(int j = 0; j < daoConfig.properties.length; ++j) {
                        String columnName = daoConfig.properties[j].columnName;
                        if(columns.contains(columnName)) {
                            properties.add(columnName);
                        }
                    }

                    if(properties.size() > 0) {
                        String columnSQL = TextUtils.join(",", properties);
                        StringBuilder insertTableStringBuilder = new StringBuilder();
                        insertTableStringBuilder.append("INSERT INTO ").append(tableName).append(" (");
                        insertTableStringBuilder.append(columnSQL);
                        insertTableStringBuilder.append(") SELECT ");
                        insertTableStringBuilder.append(columnSQL);
                        insertTableStringBuilder.append(" FROM ").append(tempTableName).append(";");
                        db.execSQL(insertTableStringBuilder.toString());
                        printLog("【Restore data】 to " + tableName);
                    }

                    StringBuilder dropTableStringBuilder = new StringBuilder();
                    dropTableStringBuilder.append("DROP TABLE ").append(tempTableName);
                    db.execSQL(dropTableStringBuilder.toString());
                    printLog("【Drop temp table】" + tempTableName);
                } catch (SQLException var10) {
                    Log.e(TAG, "【Failed to restore data from temp table 】" + tempTableName, var10);
                }
            }
        }

    }

    private static List<String> getColumns(Database db, String tableName) {
        List<String> columns = null;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 0", (String[])null);
            if(null != cursor && cursor.getColumnCount() > 0) {
                columns = Arrays.asList(cursor.getColumnNames());
            }
        } catch (Exception var8) {
            var8.printStackTrace();
        } finally {
            if(cursor != null) {
                cursor.close();
            }

            if(null == columns) {
                columns = new ArrayList();
            }

        }

        return (List)columns;
    }

    private static void printLog(String info) {
        if(DEBUG) {
            Log.d(TAG, info);
        }

    }
}
