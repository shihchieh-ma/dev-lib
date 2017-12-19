package dev.majes.widget.windowmanager;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import dev.majes.R;
import dev.majes.base.utils.WindowUtils;
import ren.yale.android.cachewebviewlib.CacheWebView;


/**
 * @author majes
 * @date 11/23/17.
 */

public class WindowManagerCtroller implements View.OnClickListener {

    private Context context;
    private WindowManager windowManager;
    private volatile static WindowManagerCtroller windowManagerCtroller;
    private static android.view.WindowManager androidWindowManager;
    private static DisplayMetrics dm;
    private boolean cantCreate = false;
    private FrameLayout frameLayout;

    private WindowManagerCtroller(Context context) {
        this.context = context;
    }

    public static WindowManagerCtroller getWindowManagerCtroller(Context context) {
        if (null == windowManagerCtroller) {
            synchronized (WindowManagerCtroller.class) {
                if (null == windowManagerCtroller) {
                    dm = new DisplayMetrics();
                    androidWindowManager = (android.view.WindowManager)
                            context.getSystemService(Context.WINDOW_SERVICE);
                    androidWindowManager.getDefaultDisplay().getMetrics(dm);
                    windowManagerCtroller = new WindowManagerCtroller(context);
                }
            }
        }
        return windowManagerCtroller;
    }


    public void createWindowView() {
        if (cantCreate) {
            return;
        }
        if (!WindowUtils.checkFloatWindowPermission(context)) {
            WindowUtils.showDialogTipUserRequestPermission(context);
            return;
        }
        View windowView = LayoutInflater.from(context).inflate(R.layout.window_view, null, false);
        frameLayout = windowView.findViewById(R.id.float_root_view);
        windowView.setOnClickListener(this);
//        FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(
//                DPXUtils.dip2px(context,240),
//                DPXUtils.dip2px(context,320),
//                Gravity.CENTER
//        );
        CacheWebView cacheWebView = windowView.findViewById(R.id.cache_wv);
        cacheWebView.loadUrl("http://www.baidu.com");
        this.windowManager = new WindowManager(context);
        WindowManager.Configs configs = new WindowManager.Configs();
        configs.floatingViewX = dm.widthPixels / 2;
        configs.floatingViewY = dm.heightPixels / 4;
        configs.overMargin = -(int) (8 * dm.density);
        this.windowManager.andWindowView(windowView, configs);
        cantCreate = !cantCreate;
    }

    @Override
    public void onClick(View v) {
        destoryWindowView();
        // Intent i = new Intent(context,Activity.class);
        // i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // context.startActivity(i);
    }

    private void destoryWindowView() {
        if (null != windowManager) {
            windowManager.removeAllWindowView();
            windowManager = null;
        }
        if (null != dm) {
            dm = null;
        }
        if (null != androidWindowManager) {
            androidWindowManager = null;
        }
        if (null != windowManagerCtroller) {
            windowManagerCtroller = null;
        }
        if (null != context){
            context = null;
        }
        cantCreate = false;
    }

}
