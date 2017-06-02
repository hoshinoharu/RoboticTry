package com.reharu.robotictry.test;

import android.app.Application;
import android.view.WindowManager;

import com.reharu.harubase.base.HaruApp;

/**
 * Created by hoshino on 2017/5/31.
 */
public class FloatApplication extends Application {


    private WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();

    public WindowManager.LayoutParams getWindowParams() {
        return windowParams;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HaruApp.setContext(getApplicationContext());
    }
}