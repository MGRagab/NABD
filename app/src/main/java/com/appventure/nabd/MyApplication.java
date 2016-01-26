package com.appventure.nabd;


import android.app.Application;

import com.appventure.nabd.helpers.ParseUtils;

public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        ParseUtils.registerParse(this);
    }


    public static synchronized MyApplication getInstance() {
        return mInstance;
    }
}
