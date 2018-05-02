package com.ieds.gis.base.widget;

import android.app.Activity;

import java.lang.ref.WeakReference;

/**
 * Created by abc on 2017/3/21.
 */

public class ActivityManager {

    private static ActivityManager inst = new ActivityManager();

    private WeakReference<Activity> currentActivityWeakReference;

    private ActivityManager(){
    }

    public static ActivityManager getInstance(){
        return inst;
    }

    public Activity getCurrentActivity(){
        Activity currentActicity = null;
        if (currentActivityWeakReference != null){
            currentActicity = currentActivityWeakReference.get();
        }
        return currentActicity;
    }

    public void setCurrentActivity(Activity activity){
        currentActivityWeakReference = new WeakReference<Activity>(activity);
    }
}

