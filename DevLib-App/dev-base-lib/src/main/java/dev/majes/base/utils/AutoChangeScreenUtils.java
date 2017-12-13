package dev.majes.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;


/**
 * @author majes
 * @date 11/29/17.
 */

public class AutoChangeScreenUtils {

    private volatile static AutoChangeScreenUtils mInstance;

    private Activity mActivity;

    // 是否是竖屏
    private boolean isPortrait = true;

    private SensorManager sm;
    private OrientationSensorListener listener;
    private Sensor sensor;

    private SensorManager sm1;
    private OrientationSensorListener1 listener1;
    private Handler mHandler = getHandler();

    /**
     * 停止监听
     */
    public void stop() {
        sm.unregisterListener(listener);
        sm1.unregisterListener(listener1);
        ThreadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                if (null != mHandler) {
                    mHandler = null;
                }
                if (null != listener1) {
                    listener1 = null;
                }
                if (null != sm1) {
                    sm1 = null;
                }
                if (null != sensor) {
                    sensor = null;
                }
                if (null != listener) {
                    listener = null;
                }
                if (null != sm) {
                    sm = null;
                }
                if (null != mActivity) {
                    mActivity = null;
                }
                if (null != mInstance) {
                    mInstance = null;
                }
            }
        });
    }


    @SuppressWarnings("all")
    private Handler getHandler() {
        if (null == mHandler) {
            synchronized (AutoChangeScreenUtils.class) {
                if (null == mHandler) {
                    mHandler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            switch (msg.what) {
                                case 888:
                                    int orientation = msg.arg1;
                                    if (orientation > 75 && orientation < 105) {
                                        //切换成横屏反向：ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                                        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                                        isPortrait = false;
                                    } else if (orientation > 165 && orientation < 225) {

                                        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                                        isPortrait = true;
                                    } else if ((orientation > 255 && orientation < 315) || (orientation > -315 && orientation < -225)) {
                                        if (isPortrait) {
                                            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                                            isPortrait = false;
                                        }
                                    } else if ((orientation > 345 && orientation < 360) || (orientation > 0 && orientation < 45)) {
                                        if (!isPortrait) {
                                            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                                            isPortrait = true;
                                        }
                                    }
                                    break;
                                default:
                                    break;
                            }

                        }

                        ;
                    };
                }
            }
        }
        return mHandler;
    }


    /**
     * 返回ScreenSwitchUtils单例
     **/
    public static AutoChangeScreenUtils init(Context context) {
        if (mInstance == null) {
            synchronized (AutoChangeScreenUtils.class) {
                if (mInstance == null) {
                    mInstance = new AutoChangeScreenUtils(context);
                }
            }
        }
        return mInstance;
    }

    private AutoChangeScreenUtils(Context context) {
        // 注册重力感应器,监听屏幕旋转
        sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        listener = new OrientationSensorListener(mHandler);

        // 根据 旋转之后/点击全屏之后 两者方向一致,激活sm.
        sm1 = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor1 = sm1.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        listener1 = new OrientationSensorListener1();
    }

    /**
     * 开始监听
     */
    public void start(Activity activity) {
        mActivity = activity;
        sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
    }


    /**
     * 重力感应监听者
     */
    public class OrientationSensorListener implements SensorEventListener {
        private static final int _DATA_X = 0;
        private static final int _DATA_Y = 1;
        private static final int _DATA_Z = 2;

        private static final int ORIENTATION_UNKNOWN = -1;

        private Handler rotateHandler;

        private OrientationSensorListener(Handler handler) {
            rotateHandler = handler;
        }

        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            int orientation = ORIENTATION_UNKNOWN;
            float X = -values[_DATA_X];
            float Y = -values[_DATA_Y];
            float Z = -values[_DATA_Z];
            float magnitude = X * X + Y * Y;
            // Don't trust the angle if the magnitude is small compared to the y
            // value
            if (magnitude * 4 >= Z * Z) {
                // 屏幕旋转时
                float OneEightyOverPi = 57.29577957855f;
                float angle = (float) Math.atan2(-Y, X) * OneEightyOverPi;
                orientation = 90 - (int) Math.round(angle);
                // normalize to 0 - 359 range
                while (orientation >= 360) {
                    orientation -= 360;
                }
                while (orientation < 0) {
                    orientation += 360;
                }
            }
            if (rotateHandler != null) {
                rotateHandler.obtainMessage(888, orientation, 0).sendToTarget();
            }
        }
    }

    public class OrientationSensorListener1 implements SensorEventListener {
        private static final int _DATA_X = 0;
        private static final int _DATA_Y = 1;
        private static final int _DATA_Z = 2;

        private static final int ORIENTATION_UNKNOWN = -1;

        private OrientationSensorListener1() {
        }

        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            int orientation = ORIENTATION_UNKNOWN;
            float X = -values[_DATA_X];
            float Y = -values[_DATA_Y];
            float Z = -values[_DATA_Z];
            float magnitude = X * X + Y * Y;
            // Don't trust the angle if the magnitude is small compared to the y
            // value
            if (magnitude * 4 >= Z * Z) {
                // 屏幕旋转时
                float OneEightyOverPi = 57.29577957855f;
                float angle = (float) Math.atan2(-Y, X) * OneEightyOverPi;
                orientation = 90 - (int) Math.round(angle);
                // normalize to 0 - 359 range
                while (orientation >= 360) {
                    orientation -= 360;
                }
                while (orientation < 0) {
                    orientation += 360;
                }
            }
            boolean b1 = (orientation > 225 && orientation < 315) || (orientation > -315 && orientation < -225);
            boolean b2 = (orientation > 315 && orientation < 360) || (orientation > 0 && orientation < 45);
            // 检测到当前实际是横屏
            if (b1) {
                if (!isPortrait) {
                    sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
                    sm1.unregisterListener(listener1);
                }
                // 检测到当前实际是竖屏
            } else if (b2) {
                if (isPortrait) {
                    sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
                    sm1.unregisterListener(listener1);
                }
            }
        }
    }
}
