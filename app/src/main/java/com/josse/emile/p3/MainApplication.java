package com.josse.emile.p3;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

public class MainApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        AndroidThreeTen.init(this);
    }
}
