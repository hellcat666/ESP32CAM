package com.catsoft.esp32cam;

import android.app.Application;
import android.content.Context;

/**
 * Project: ESP32CAM
 * Package: com.catsoft.esp32cam
 * File:
 * Created by HellCat on 15.06.2021.
 */
public class ESP32Camera extends Application {
    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

}
