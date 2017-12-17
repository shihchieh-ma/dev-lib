package dev.majes.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import dev.majes.R;

/**
 * @author majes
 * @date 12/11/17.
 */

public class WaitShowUtils {
    private volatile static Dialog dialog;

    public static Dialog getDialog(Context context) {
        if (null != dialog) {
            if (dialog.isShowing()){
                dialog.dismiss();
            }
            dialog = null;
        }
        if (null == dialog) {
            synchronized (WaitShowUtils.class) {
                dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                View root = View.inflate(context, R.layout.wait_dialog, null);
                View llBg = (View) root.findViewById(R.id.dialogui_ll_bg);
                ProgressBar pbBg = (ProgressBar) root.findViewById(R.id.pb_bg);
                TextView tvMsg = (TextView) root.findViewById(R.id.dialogui_tv_msg);
                tvMsg.setText(context.getResources().getString(R.string.wait));
                llBg.setBackgroundResource(R.drawable.dialogui_shape_wihte_round_corner);
                pbBg.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.dialogui_shape_progress));
                tvMsg.setTextColor(context.getResources().getColor(R.color.text_black));
                dialog.setContentView(root);
                dialog.setCancelable(true);
                //dialog.setCancelable(!NetUtils.isNetworkConnected(context));
            }
        }
        return dialog;
    }



    public static void release() {
        if (null != dialog) {
            if (dialog.isShowing()){
                dialog.dismiss();
            }
            dialog = null;
        }
    }
}
