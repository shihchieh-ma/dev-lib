package com.example.admin.mydemo.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.SystemClock;

import com.example.admin.mydemo.base.BaseManager;
import com.example.admin.mydemo.design.PoolInstance;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marl_Jar on 2017/2/24.
 * 当程序发生Uncaught异常的时候,有该类来接管程序,并记录错误日志
 */

public class CrashHandler implements UncaughtExceptionHandler {

    // 系统默认的UncaughtException处理类
    private UncaughtExceptionHandler mDefaultHandler;

    private static CrashHandler instance = new CrashHandler();
    private Context mContext;

    // 用来存储设备信息和异常信息
    private       Map<String, String> infos   = new HashMap<String, String>();
    public static String              logName = "LogInfo" + ".log";


    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        return instance;
    }

    /**
     * 初始化
     *
     * @param context
     * @param day 清除日志时间间隔
     *
     */
    public void init(Context context, int day) {
        mContext = context;
        BaseManager.initOpenHelper(context);
        checkDir(mContext);
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        // 收集设备参数信息
        collectDeviceInfo(mContext);
        autoClear(day);
        LogFileMaker.getInstance(mContext).start();
    }

    private void checkDir(Context context) {
        if (FileUtil.hasSdcard()) {
            String path = getGlobalpath();

            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();
            File filePath = new File(path + logName);
            if (!filePath.exists()) {
                try {
                    filePath.createNewFile();
                    DateUtil.saveData(context, DateUtil.getToday());
                } catch (IOException e) {
                    Logs2File.logE(tag, e.toString());
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            SystemClock.sleep(3000);
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息; 否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null)
            return false;
        try {
            // 使用Toast来显示异常信息
            PoolInstance.getPoolInstance().startThread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        Logs2File.logE(tag, e.toString());
                        e.printStackTrace();
                    }
                    Looper.loop();
                }
            });
            // 收集设备参数信息
             collectDeviceInfo(mContext);
            // 保存日志文件
            saveCrashInfoFile(ex);
            SystemClock.sleep(1000);
            //重启应用
//            Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            mContext.startActivity(intent);

        } catch (Exception e) {
            Logs2File.logE(tag, e.toString());
            e.printStackTrace();
        }

        return true;
    }


    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName + "";String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            Logs2File.logE("CrashHandler", e.toString());
            e.printStackTrace();

        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                Logs2File.logE("CrashHandler", e.toString());
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @throws Exception
     */
    private void saveCrashInfoFile(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        try {
            SimpleDateFormat sDateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            String date = sDateFormat.format(new Date());
            sb.append("\r\n" + date + "\n");
            for (Map.Entry<String, String> entry : infos.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                sb.append(key + "=" + value + "\n");
            }
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.flush();
            printWriter.close();
            String result = writer.toString();
            sb.append(result);
            writeFile(sb.toString());

        } catch (Exception e) {
            Logs2File.logE(tag, e.toString());
            e.printStackTrace();
            sb.append("an error occured while writing file...\r\n");
            try {
                writeFile(sb.toString());
            } catch (Exception e1) {
                Logs2File.logE(tag, e1.toString());
                e1.printStackTrace();
                sb.append("an error occured while writing file...\r\n");
            }
        }

    }

    private void writeFile(String sb) throws Exception {

        if (FileUtil.hasSdcard()) {
            String path = getGlobalpath();

            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();
            File filePath = new File(path + logName);
            if (!filePath.exists()) {
                filePath.createNewFile();
                DateUtil.saveData(mContext, DateUtil.getToday());
            }
            FileOutputStream fos = new FileOutputStream(path + logName, true);
            fos.write(sb.getBytes());
            fos.flush();
            fos.close();
        }
    }

    private static String getGlobalpath() {
        return Environment.getExternalStorageDirectory()
                + File.separator;
    }

    /**
     * 文件删除
     *
     * @param autoClearDay
     * 文件保存天数
     */
    private final String tag = "CrashHandler";

    public void autoClear(final int autoClearDay) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = DateUtil.getToday();
        String createDay = DateUtil.loadData(mContext);
        try {
            Date tDate = sdf.parse(today);
            Date cDate = sdf.parse(createDay);
            int days = (int) ((tDate.getTime() - cDate.getTime()) / (24 * 60 * 60 * 1000));
            if (Math.abs(days - autoClearDay) >= 0) {
                FileUtil.deleteFile(getGlobalpath() + logName);
                FileUtil.createFile(getGlobalpath() + logName);
                DateUtil.saveData(mContext, DateUtil.getToday());
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static String getLogPath() {
        File file = new File(getGlobalpath() + logName);
        if (file.exists()) {
            return getGlobalpath() + logName;
        }
        return null;
    }
}
