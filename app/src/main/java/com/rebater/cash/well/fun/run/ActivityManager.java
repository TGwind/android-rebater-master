package com.rebater.cash.well.fun.run;

import android.app.Activity;

import com.rebater.cash.well.fun.util.LogInfo;

import java.lang.ref.WeakReference;
import java.util.Stack;

public class ActivityManager {
    private Stack<WeakReference<Activity>> mActivityStack;
    private static volatile ActivityManager mInstance = new ActivityManager();

    private ActivityManager() {

    }

    public static ActivityManager getInstance() {
        if (mInstance == null) {
            synchronized (ActivityManager.class) {
                if (mInstance == null) {
                    mInstance = new ActivityManager();
                }
            }
        }
        return mInstance;
    }

    public void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(new WeakReference<>(activity));
    }

    public void removeActivity(Activity activity) {
        if (mActivityStack != null) {
            mActivityStack.remove(new WeakReference<>(activity));
        }
    }

    public Activity currentActivity() {
        try {
            if (mActivityStack != null && !mActivityStack.isEmpty()) {
                return mActivityStack.lastElement().get();
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
        return null;
    }

    public Activity last2Activity() {
        try {
            if (mActivityStack != null && !mActivityStack.isEmpty()) {
                if (mActivityStack.size() >= 2) {
                    return mActivityStack.get(mActivityStack.size() - 2).get();
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
        return null;
    }
}

