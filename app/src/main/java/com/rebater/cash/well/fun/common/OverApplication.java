package com.rebater.cash.well.fun.common;

import android.app.Activity;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.multidex.MultiDex;

import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.google.firebase.FirebaseApp;
import com.rebater.cash.well.fun.activity.SkipActivity;
import com.rebater.cash.well.fun.bean.LogEvent;
import com.rebater.cash.well.fun.green.GreenDaoUtils;
import com.rebater.cash.well.fun.green.LogEventDao;
import com.rebater.cash.well.fun.run.ActivityManager;
import com.rebater.cash.well.fun.util.LogInfo;
import com.rebater.cash.well.fun.util.ProjectTools;
import com.rebater.cash.well.fun.util.ViewTools;
import com.rebater.cash.well.fun.util.WorksUtils;
import com.rebater.cash.well.fun.util.pre.SelfPrefrence;
import com.rebater.cash.well.fun.util.pre.SelfValue;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/*得到一个Application对象
封装一些全局性的操作
初始化全局性的数据*/
public class OverApplication extends Application {
    Timer timer; //Timer是Java提供的原生Scheduler(任务调度)工具类，用来在一个后台线程计划执行指定任务
    private ActivityManager activityManager;

    public ActivityManager getActivityManager() {
        if (activityManager == null) {
            activityManager = ActivityManager.getInstance();
        }
        return activityManager;
    }

    private static OverApplication theApplication;

    @Override
    protected void attachBaseContext(Context base) {
        MultiDex.install(this);
        super.attachBaseContext(base);
    }

    public static OverApplication getInstance() {
        return theApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            SelfPrefrence.INSTANCE.init(this);
            ProjectTools.getAAID(this);
            activityManager = ActivityManager.getInstance();
            theApplication = this;
            registerActivityLifecycleCallbacks(new RegisterActivityListener(activityManager));

            FirebaseApp.initializeApp(this);

            ViewTools.initAPPflyer(this);

            SelfPrefrence.INSTANCE.setInt(SelfValue.FIRSTdAY, SelfPrefrence.INSTANCE.getInt(SelfValue.FIRSTdAY, 0) + 1);

            GreenDaoUtils.getSingleTon(this).getDaoSession();
            AppLovinSdk.getInstance(this).setMediationProvider("max");
//            AppLovinSdk.getInstance( this ).getSettings().setVerboseLogging( true );

            AppLovinSdk.initializeSdk(this, new AppLovinSdk.SdkInitializationListener() {
                @Override
                public void onSdkInitialized(final AppLovinSdkConfiguration configuration) {
                    // AppLovin SDK is initialized, start loading ads
                }
            });
            if (timer == null) {
                timer = new Timer();
            }
            uplog(this);
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    private void uplog(OverApplication upperAPPlication) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                LogInfo.e("timer");
                LogEventDao logEventDao = GreenDaoUtils.getSingleTon(upperAPPlication).getDaoSession().getLogEventDao();
                if (logEventDao != null) {
                    List<LogEvent> list = logEventDao.loadAll();
                    if (list != null && list.size() > 0) {
                        LogInfo.e("size-->" + list.size());
                        List<LogEvent> logEventList = ProjectTools.getLog(list);
                        WorksUtils.upLog(logEventList, logEventDao);
//                        logEventDao.deleteInTx(list);
                    }
                }
            }
        }, 0, 30 * 1000);
    }

    public class RegisterActivityListener implements Application.ActivityLifecycleCallbacks {
        private ActivityManager activityManager;

        public RegisterActivityListener(ActivityManager ac) {
            activityManager = ac;
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            try {
                String name = activity.getClass().getName();
                LogInfo.e(name);
                if (!name.equals(SkipActivity.class.getName())) {
                    activityManager.addActivity(activity);
                }
            } catch (Exception e) {
                LogInfo.e(e.toString());
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            activityManager.removeActivity(activity);
        }
    }

}
