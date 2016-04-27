package com.zzia.graduation.common.log;

import android.content.Context;
import android.os.Looper;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 在Application中统一捕获异常，保存到文件中下次再打开时上传
 */
public class ApsCrashHandler implements UncaughtExceptionHandler {
    /** 是否开启日志输出,在Debug状态下开启,
     * 在Release状态下关闭以提示程序性能
     * */
    /**
     * 系统默认的UncaughtException处理类
     */
    private UncaughtExceptionHandler mDefaultHandler;
    /**
     * CrashHandler实例
     */
    private static ApsCrashHandler INSTANCE;

    /**
     * 保证只有一个CrashHandler实例
     */
    private ApsCrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static ApsCrashHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ApsCrashHandler();
        }
        return INSTANCE;
    }

    private CaughtTask caughtTask = null;

    private ApsLogInterface apsLogInterface;

    /**
     * 异常后的程序处理
     *
     * @author xiongfei.miao@gmail.com
     */
    public interface CaughtTask {
        void caugthAfterDo(Throwable throwable);
    }


    /**
     * 初始化,注册Context对象,
     * 获取系统默认的UncaughtException处理器,
     * 设置该CrashHandler为程序的默认处理器
     *
     * @param ctx
     */
    public void regist(Context ctx, ApsLogInterface apsLogInterface, CaughtTask caughtTask) {
        this.apsLogInterface = apsLogInterface;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.caughtTask = caughtTask;
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            if (null != caughtTask) {
                caughtTask.caugthAfterDo(ex);
            }
            mDefaultHandler.uncaughtException(thread, ex);
        } else {  //如果自己处理了异常，则不会弹出错误对话框，则需要手动退出app
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
            if (null != caughtTask) {
                caughtTask.caugthAfterDo(ex);
            } else {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(10);
            }
        }
    }

    /**
     * 自定义错误处理,收集错误信息
     * 发送错误报告等操作均在此完成.
     *
     * @return true代表处理该异常，不再向上抛异常，
     * false代表不处理该异常(可以将该log信息存储起来)然后交给上层(这里就到了系统的异常处理)去处理，
     */
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }
        //        final String msg = ex.getLocalizedMessage();
        final StackTraceElement[] stack = ex.getStackTrace();
        final String message = ex.getMessage();
        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                StringBuffer buffer = new StringBuffer(String.valueOf(message)).append("\n");
                if (null != stack) {
                    for (StackTraceElement stackTraceElement : stack) {
                        buffer.append(String.valueOf(stackTraceElement)).append("\n");
                    }
                }
                //                MyApplication.showToastCenter("程序出错啦:" + message);
                //                Toast.makeText(mContext,"程序出错啦:" + message , Toast.LENGTH_LONG).show();
                //                可以只创建一个文件，以后全部往里面append然后发送，这样就会有重复的信息，个人不推荐
                //                String fileName = "crash-" + System.currentTimeMillis()  + ".log";
                //                File file = new File(Environment.getExternalStorageDirectory(), fileName);
                //                try {
                //                    FileOutputStream fos = new FileOutputStream(file,true);
                //                    fos.write(message.getBytes());
                //                    for (int i = 0; i < stack.length; i++) {
                //                        fos.write(stack[i].toString().getBytes());
                //                    }
                //                    fos.flush();
                //                    fos.close();
                //                } catch (Exception e) {
                //                }
                if (null != apsLogInterface) {
//				    LogUtils.i("ApsCrashHandler", "save ex log");
                    apsLogInterface.save(buffer.toString());
                }
//				android.os.Process.killProcess(android.os.Process.myPid());  
//				System.exit(10);  
                Looper.loop();
            }
        }.start();
        return false;
    }

    public void sendLog() {
        if (null != apsLogInterface) {
            apsLogInterface.sendLog();
        }
    }

    public void save(String message) {
        if (null != apsLogInterface) {
            apsLogInterface.save(message);
        }
    }

}