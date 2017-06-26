package com.example.admin.mydemo.utils;

import android.util.Log;

/**
 * Created by Marl_Jar on 2017/2/27.
 */

public class Logs2File {
    private Logs2File() {
    }

    public static void logI(String tag, String string) {
        Log.i("MyDemo", tag + ":" + string);
    }

    public static void logD(String tag, String string) {
        Log.d("MyDemo", tag + ":" + string);
    }

    public static void logW(String tag, String string) {
        Log.w("MyDemo", tag + ":" + string);
    }

    public static void logE(String tag, String string) {
        Log.e("MyDemo", tag + ":" + string);

    }
}
