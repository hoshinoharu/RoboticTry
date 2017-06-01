package com.reharu.robotictry.components;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.reharu.harubase.base.AutoInjecter;
import com.reharu.robotictry.test.FloatApplication;

/**
 * Created by 星野悠 on 2017/5/31.
 */

public class HaruSupWindow implements AutoInjecter.AutoInjectable {

    protected View rootView;
    private Activity activity;
    private Handler handler = new Handler(Looper.getMainLooper());
    private WindowManager.LayoutParams wmParams;
    private WindowManager wm;
    public HaruSupWindow(Activity activity, int layoutId) {
        this.activity = activity;
//        rootView = LayoutInflater.from(activity.getApplicationContext()).inflate(layoutId, ActivityTool.getRootView(activity), false);
        rootView = new View(activity.getApplicationContext()) ;
        initWindow();
        AutoInjecter.autoInjectAllField(this);
    }


    private void initWindow() {
        wm = (WindowManager) activity.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        wmParams = ((FloatApplication) activity.getApplication()).getWindowParams();
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE; // 设置window type
        wmParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.x = 0;
        wmParams.y = 0;
        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        wm.addView(rootView, wmParams);
    }

    @Override
    public View findInjectViewById(int resId) {
        return this.rootView.findViewById(resId);
    }

    public void runOnMainLooper(Runnable runnable){
        handler.post(runnable) ;
    }


}
