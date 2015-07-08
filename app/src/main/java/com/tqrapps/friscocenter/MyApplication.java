package com.tqrapps.friscocenter;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by Windows on 30-01-2015.
 */
public class MyApplication extends Application {

    public static final String API_KEY_ROTTEN_TOMATOES="54wzfswsa4qmjg8hjwa64d4c";
    private static MyApplication sInstance;


    public static MyApplication getInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }



    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "bn0T406dN7UYew1zXZKzM7bJsHn4Kqr9BNTIFrJF", "5Ql0Rt4uGarSZGfxjCtopSzwNxozH94cLdnP1NK9");
        JodaTimeAndroid.init(this);
        sInstance=this;
    }

}
