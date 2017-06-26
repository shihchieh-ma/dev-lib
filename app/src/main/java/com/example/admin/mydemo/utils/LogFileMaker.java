package com.example.admin.mydemo.utils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Marl_Jar on 2017/2/24.
 * log日志系列化本地
 */

public class LogFileMaker {
    private static LogFileMaker INSTANCE = null;
    private static String PATH_LOGCAT;
    private LogDumper mLogDumper = null;
    private int mPId;

    /**
     * 初始化目录
     */
    private final String tag = "LogFileMaker";

    public void init(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
            PATH_LOGCAT = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + File.separator;
        } else {// 如果SD卡不存在，就保存到本应用的目录下
            PATH_LOGCAT = context.getFilesDir().getAbsolutePath()
                    + File.separator;
        }
        File file = new File(PATH_LOGCAT);
        if (!file.exists()) {
            file.mkdirs();
            File f = new File(PATH_LOGCAT + File.separator + "LogInfo" + ".log");
            if (!f.exists()) {
                try {
                    f.createNewFile();
                    DateUtil.saveData(context, DateUtil.getToday());
                } catch (IOException e) {
                    Logs2File.logE(tag, e.toString());
                    e.printStackTrace();
                }
            }

        }
    }

    public static LogFileMaker getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LogFileMaker(context);
        }
        return INSTANCE;
    }

    private LogFileMaker(Context context) {
        init(context);
        mPId = android.os.Process.myPid();
    }

    public void start() {
        if (mLogDumper == null)
            mLogDumper = new LogDumper(String.valueOf(mPId), PATH_LOGCAT);
        mLogDumper.start();
    }

    public void stop() {
        if (mLogDumper != null) {
            mLogDumper.stopLogs();
            mLogDumper = null;
        }
    }

    private class LogDumper extends Thread {

        private Process logcatProc;
        private BufferedReader mReader  = null;
        private boolean        mRunning = true;
        String cmds = null;
        private String mPID;
        private FileOutputStream out = null;

        public LogDumper(String pid, String dir) {
            mPID = pid;
            if (mPID.length() < 4) {
                mPID = "0" + mPID;
            }
            try {
                out = new FileOutputStream(Environment.getExternalStorageDirectory()
                        + File.separator
                        + "LogInfo.log", true);
            } catch (FileNotFoundException e) {
                Logs2File.logE(tag, e.toString());
                e.printStackTrace();
            }

            /**
             *
             * 日志等级：*:v , *:d , *:w , *:e , *:f , *:s
             *
             * 显示当前mPID程序的 E和W等级的日志.
             *
             * */

            //             cmds = "logcat *:e  | grep \"(" + mPID + ")\"";
            //             cmds = "logcat  | grep \"(" + mPID + ")\"";//打印所有日志信息
            cmds = "logcat -s MyDemo";//打印标签过滤信息
            //             cmds = "logcat *:e *:i | grep \"(" + mPID + ")\"";
            //             cmds = "logcat *:e | grep \"(" + mPID + ")\"";

        }

        public void stopLogs() {
            mRunning = false;
        }

        @Override
        public void run() {
            try {
                logcatProc = Runtime.getRuntime().exec(cmds);
                mReader = new BufferedReader(new InputStreamReader(
                        logcatProc.getInputStream()), 1024);
                String line = null;
                while (mRunning && (line = mReader.readLine()) != null) {
                    if (!mRunning) {
                        break;
                    }
                    if (line.length() == 0) {
                        continue;
                    }
                    if (out != null && line.contains(mPID)) {
                        out.write((DateUtil.getDateEN() + "  " + line + "\n")
                                .getBytes());
                    }
                }

            } catch (IOException e) {
                Logs2File.logE(tag, e.toString());
                e.printStackTrace();
            } finally {
                if (logcatProc != null) {
                    logcatProc.destroy();
                    logcatProc = null;
                }
                if (mReader != null) {
                    try {
                        mReader.close();
                        mReader = null;
                    } catch (IOException e) {
                        Logs2File.logE(tag, e.toString());
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        Logs2File.logE(tag, e.toString());
                        e.printStackTrace();
                    }
                    out = null;
                }
            }
        }
    }
}
