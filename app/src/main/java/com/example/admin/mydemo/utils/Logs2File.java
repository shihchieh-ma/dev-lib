package com.example.admin.mydemo.utils;

import android.util.Log;

/**
 * Created by Marl_Jar on 2017/2/27.
 */

public class Logs2File {
    private Logs2File() {
    }


    public static void logE(String tag, String string) {
        Log.e("MyDemo", tag + ":" + string);
    }
}
