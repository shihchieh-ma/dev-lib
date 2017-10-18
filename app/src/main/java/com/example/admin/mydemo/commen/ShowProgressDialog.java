package com.example.admin.mydemo.commen;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Author：Marlborn
 * Email：marlborn@foxmail.com
 */
public class ShowProgressDialog {
    private static ProgressDialog progressDialog;
    private static Commen commen = new Commen();

    private ShowProgressDialog() {
    }

    public static synchronized ProgressDialog getInstance(Context context) {
        if (null != progressDialog) {
            return progressDialog;
        } else {
            ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("正在加载,请稍等...");
            if (commen.isNetworkConnected(context)) {
                progressDialog.setCancelable(false);
            } else {
                progressDialog.setCancelable(true);
            }

            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            return progressDialog;
        }


    }
}
