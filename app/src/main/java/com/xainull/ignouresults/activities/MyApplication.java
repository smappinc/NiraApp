package com.xainull.ignouresults.activities;

import android.app.Application;

import com.onesignal.OneSignal;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId("4963ea44-3f31-44ad-b0b5-ce98435f0992");

    }


}
