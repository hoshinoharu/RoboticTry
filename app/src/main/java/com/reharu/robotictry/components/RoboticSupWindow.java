package com.reharu.robotictry.components;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.reharu.harubase.tools.RootCMD;
import com.reharu.robotictry.test.FloatApplication;

/**
 * Created by 星野悠 on 2017/5/31.
 */

public class RoboticSupWindow extends LinearLayout implements View.OnClickListener {
    private float mTouchX;
    private float mTouchY;
    private float x;
    private float y;
    private float mStartX;
    private float mStartY;


    private Handler handler = new Handler(Looper.getMainLooper()) ;

    private OnClickListener mClickListener;
    private WindowManager windowManager = (WindowManager) getContext()
            .getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    // 此windowManagerParams变量为获取的全局变量，用以保存悬浮窗口的属性
    private WindowManager.LayoutParams windowManagerParams = ((FloatApplication) getContext()
            .getApplicationContext()).getWindowParams();

    private int statusBarHeight;
    private Button btnCap ;


    public RoboticSupWindow(Context context) {
        super(context);
        Rect frame = new Rect();
        getWindowVisibleDisplayFrame(frame);
        statusBarHeight = frame.top;
        initBtnCap();
    }

    private void initBtnCap() {
        btnCap = new Button(getContext().getApplicationContext()) ;
        btnCap.setText("截图");
        btnCap.setOnClickListener(this);
        btnCap.setOnTouchListener(new FollowTouchListener( this));
        this.addView(btnCap, new LinearLayoutCompat.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
    }
    public void updateViewPosition() {
// 更新浮动窗口位置参数
        windowManagerParams.x = (int) this.getX();
        windowManagerParams.y = (int) this.getY();
        windowManager.updateViewLayout(this, windowManagerParams); // 刷新显示
    }
    @Override
    public void onClick(View v) {
        if(v == btnCap){
            capScreen();
        }
    }

    private void capScreen() {
        this.setVisibility(GONE);
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                RootCMD.execute("screencap -p /sdcard/caps/"+System.currentTimeMillis()+".png") ;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        RoboticSupWindow.this.setVisibility(VISIBLE);
                    }
                }) ;
            }
        }.start();
    }
}
