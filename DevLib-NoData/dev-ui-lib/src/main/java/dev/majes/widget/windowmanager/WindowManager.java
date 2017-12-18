package dev.majes.widget.windowmanager;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author majes
 * @date 11/15/17.
 */

public class WindowManager implements View.OnTouchListener {
    private Context mContext;


    /**
     * WindowManager
     */
    private final android.view.WindowManager mWindowManager;

    /**
     * 悬浮窗集合
     */
    private List<WindowView> mWindowViewList;

    /**
     * 构造方法
     *
     * @param context 上下文
     */
    public WindowManager(Context context) {
        this.mContext = context;
        this.mWindowManager = (android.view.WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        this.mWindowViewList = new ArrayList<>();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    /**
     * 添加悬浮窗
     *
     * @param view    悬浮窗视图组件
     * @param configs 悬浮窗的配置信息
     */
    public void andWindowView(View view, Configs configs) {
        // 创建悬浮窗
        WindowView windowView = new WindowView(this.mContext, configs.floatingViewX, configs.floatingViewY);
        windowView.setOnTouchListener(this);
        windowView.setOverMargin(configs.overMargin);
        windowView.setMoveDirection(configs.moveDirection);
        windowView.setAnimateInitialMove(configs.animateInitialMove);

        // 设置悬浮窗的大小
        FrameLayout.LayoutParams targetParams = new FrameLayout.LayoutParams(configs.floatingViewWidth, configs.floatingViewHeight);
        view.setLayoutParams(targetParams);
        windowView.addView(view);

        // 添加悬浮窗到集合
        this.mWindowViewList.add(windowView);

        // 添加悬浮窗
        this.mWindowManager.addView(windowView, windowView.getWindowLayoutParams());
    }


    /**
     * 移除悬浮窗
     */
    public void removeAllWindowView() {
        if (this.mWindowViewList != null) {
            for (WindowView windowView : mWindowViewList) {
                this.mWindowManager.removeViewImmediate(windowView);
            }
            this.mWindowViewList.clear();
        }
    }

    /**
     * 悬浮窗的配置信息
     */
    static class Configs {
        /**
         * 悬浮窗的x坐标
         */
        public int floatingViewX;

        /**
         * 悬浮窗的y坐标
         */
        public int floatingViewY;

        /**
         * 悬浮窗的宽度（单位：px）
         */
        public int floatingViewWidth;

        /**
         * 悬浮窗的高度（单位：px）
         */
        public int floatingViewHeight;

        /**
         * 悬浮窗边缘的外边距
         */
        public int overMargin;

        /**
         * 悬浮窗移动方向
         */
        @WindowView.MoveDirection
        public int moveDirection;

        /**
         * 悬浮窗移动时是否带动画
         */
        public boolean animateInitialMove;

        public Configs() {
            this.floatingViewX = WindowView.DEFAULT_X;
            this.floatingViewY = WindowView.DEFAULT_Y;
            this.floatingViewWidth = WindowView.DEFAULT_WIDTH;
            this.floatingViewHeight = WindowView.DEFAULT_HEIGHT;
            this.overMargin = 0;
            this.moveDirection = WindowView.MOVE_DIRECTION_DEFAULT;
            this.animateInitialMove = true;
        }
    }
}
