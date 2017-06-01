package com.reharu.robotictry.test;

import android.app.Application;
import android.view.WindowManager;

/**
 * Created by hoshino on 2017/5/31.
 */
public class FloatApplication extends Application {

    private WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();

    public WindowManager.LayoutParams getWindowParams() {
        return windowParams;
    }
}