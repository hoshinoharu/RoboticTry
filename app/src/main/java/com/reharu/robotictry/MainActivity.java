package com.reharu.robotictry;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.reharu.robotictry.components.RoboticSupWindow;
import com.reharu.robotictry.test.FloatApplication;

public class MainActivity extends Activity implements View.OnClickListener {
    private WindowManager windowManager = null;
    private WindowManager.LayoutParams windowManagerParams = null;
    private static RoboticSupWindow floatView = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题栏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);//全屏
        setContentView(R.layout.activity_main);
        createView();
        finish();
    }
    public void onDestroy() {
        super.onDestroy();
    }
    private void createView() {
        floatView = new RoboticSupWindow(getApplicationContext()) ;
// 获取WindowManager
        windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
// 设置LayoutParams(全局变量）相关参数
        windowManagerParams = ((FloatApplication) getApplication()).getWindowParams();
        windowManagerParams.type = WindowManager.LayoutParams.TYPE_PHONE; // 设置window type
        windowManagerParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
// 设置Window flag
        windowManagerParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
/*
* 注意，flag的值可以为：
* LayoutParams.FLAG_NOT_TOUCH_MODAL 不影响后面的事件
* LayoutParams.FLAG_NOT_FOCUSABLE 不可聚焦
* LayoutParams.FLAG_NOT_TOUCHABLE 不可触摸
*/
// 调整悬浮窗口至左上角，便于调整坐标
        windowManagerParams.gravity = Gravity.LEFT | Gravity.TOP;
// 以屏幕左上角为原点，设置x、y初始值
        windowManagerParams.x = 0;
        windowManagerParams.y = 0;
// 设置悬浮窗口长宽数据
        windowManagerParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        windowManagerParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
// 显示myFloatView图像
        windowManager.addView(floatView, windowManagerParams);
    }
    public void onClick(View v) {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
    }
}
