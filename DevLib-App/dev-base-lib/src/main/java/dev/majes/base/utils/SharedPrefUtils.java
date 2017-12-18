package dev.majes.base.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * sp工具类
 * @author majes
 */
public class SharedPrefUtils {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    static String SP_NAME = "Dev_Lib_SP";
    ;

    private static SharedPrefUtils instance;

    private SharedPrefUtils(Context context, String spName) {
        sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static SharedPrefUtils getSharePref(Context context, String spName) {
        boolean aBoolean = (spName != null || (!"".equals(spName))) && !SP_NAME.equals(spName);
        if (aBoolean) {
            if (null != instance) {
                synchronized (SharedPrefUtils.class) {
                    instance = null;
                    SP_NAME = spName;
                    instance = new SharedPrefUtils(context.getApplicationContext(),SP_NAME);
                    return instance;
                }
            }
        }
        if (instance == null) {
            synchronized (SharedPrefUtils.class) {
                if (instance == null) {
                    instance = new SharedPrefUtils(context.getApplicationContext(), SP_NAME);
                }
            }
        }
        return instance;
    }

    public void remove(String key) {
        editor.remove(key);
        editor.apply();
    }


    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    public void clear() {
        editor.clear().apply();
    }


    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public void putLong(String key, Long value) {
        editor.putLong(key, value);
        editor.apply();
    }

    public long getLong(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }


    public void putBoolean(String key, Boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }


    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

}
