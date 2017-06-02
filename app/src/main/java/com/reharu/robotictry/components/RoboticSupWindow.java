package com.reharu.robotictry.components;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.reharu.harubase.tools.RootCMD;
import com.reharu.harubase.tools.ViewTool;
import com.reharu.robotictry.R;
import com.reharu.robotictry.test.FloatApplication;
import com.reharu.robotictry.tools.PinYinTool;

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
    private boolean toggle = true ;

    private Context appContext;

    private Handler handler = new Handler(Looper.getMainLooper()) ;

    private OnClickListener mClickListener;
    private WindowManager windowManager = (WindowManager) getContext()
            .getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    // 此windowManagerParams变量为获取的全局变量，用以保存悬浮窗口的属性
    private WindowManager.LayoutParams windowManagerParams = ((FloatApplication) getContext()
            .getApplicationContext()).getWindowParams();

    private int statusBarHeight;
    private Button btnToggle;
    private Button btnCap ;

    //选项容器
    private LinearLayout optionsContainer ;

    //所有截图类型
    private String[] kinds ;

    //所有角色名称
    private String[] characters ;


    private String selectCharacter ;
    private String selectKind;

    public RoboticSupWindow(Context context) {
        super(context);
        Rect frame = new Rect();
        getWindowVisibleDisplayFrame(frame);
        statusBarHeight = frame.top;
        appContext = getContext().getApplicationContext() ;
        init();
        initBtnToggle();
        initOptionsContainer();
        initBtnCap();
    }


    private void init() {
        this.setOrientation(HORIZONTAL);
        this.setGravity(Gravity.CENTER);
        this.setBackgroundColor(Color.parseColor("#aa000000"));
    }

    private void initBtnToggle() {
        btnToggle = new Button(appContext) ;
        btnToggle.setText("展开");
        btnToggle.setOnClickListener(this);
        btnToggle.setOnTouchListener(new FollowTouchListener( this));
        this.addView(btnToggle, new LinearLayoutCompat.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
    }


    private void initOptionsContainer() {
        optionsContainer = new LinearLayout(appContext) ;
        optionsContainer.setOrientation(VERTICAL);
        optionsContainer.setGravity(Gravity.CENTER);

        LinearLayout characterContainer = new LinearLayout(appContext) ;
        characterContainer.setOrientation(HORIZONTAL);

        TextView textView = new TextView(appContext) ;
        textView.setText("女武神");
        LinearLayout.LayoutParams layoutParams = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT) ;
        layoutParams.rightMargin = ViewTool.dip2px(16) ;
        textView.setLayoutParams(layoutParams);
        characterContainer.addView(textView, layoutParams);

        Spinner character = new Spinner(appContext) ;
        characters = getResources().getStringArray(R.array.characters);
        character.setAdapter(new ArrayAdapter<String>(appContext ,android.R.layout.simple_spinner_item,characters ));
        character.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setSelectCharacter(characters[i]) ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        characterContainer.addView(character);


        LinearLayout kindContainer = new LinearLayout(appContext) ;
        kindContainer.setOrientation(HORIZONTAL);

        TextView kindText = new TextView(appContext) ;
        kindText.setText("类型");
        LinearLayout.LayoutParams kindLayoutParams = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT) ;
        kindLayoutParams.rightMargin = ViewTool.dip2px(16) ;
        kindText.setLayoutParams(kindLayoutParams);
        kindContainer.addView(kindText, kindLayoutParams);

        Spinner kindSpinner = new Spinner(appContext) ;
        kinds = getResources().getStringArray(R.array.kinds);
        kindSpinner.setAdapter(new ArrayAdapter<String>(appContext ,android.R.layout.simple_spinner_item,kinds ));
        kindSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setSelectKind(kinds[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        kindContainer.addView(kindSpinner);


        optionsContainer.addView(kindContainer);
        optionsContainer.addView(characterContainer);
        this.addView(optionsContainer);
    }

    private void initBtnCap() {
        btnCap = new Button(appContext) ;
        btnCap.setText("截图");
        btnCap.setOnClickListener(this);
        optionsContainer.addView(btnCap);
    }

    private void setSelectCharacter(String character) {
        this.selectCharacter = character ;
    }

    public void updateViewPosition() {
// 更新浮动窗口位置参数
        windowManagerParams.x = (int) this.getX();
        windowManagerParams.y = (int) this.getY();
        windowManager.updateViewLayout(this, windowManagerParams); // 刷新显示
    }
    @Override
    public void onClick(View v) {
        if(v == btnToggle){
            toggle();
        }else if(v == btnCap){
            capScreen();
        }
    }

    private void toggle(){
        if(toggle){
            btnToggle.setText("收缩");
            optionsContainer.setVisibility(GONE);
        }else{
            btnToggle.setText("展开");
            optionsContainer.setVisibility(VISIBLE);
        }
        toggle = !toggle ;
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
                RootCMD.execute("screencap -p /mnt/shared/Image" + getKindPath() + "/"+ PinYinTool.toPinYin(getSelectCharacter(),"", PinYinTool.Type.LOWERCASE)+".png") ;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        RoboticSupWindow.this.setVisibility(VISIBLE);
                    }
                }) ;
            }
        }.start();
    }

    public String getSelectCharacter() {
        return selectCharacter;
    }

    public void setSelectKind(String selectKind) {
        this.selectKind = selectKind;
    }

    public String getSelectKind() {
        return selectKind;
    }

    public String getKindPath(){
        if(selectKind.equals("场景")){
            return "/scenes" ;
        }else if(selectKind.equals("控件")){
            return "/widgets" ;
        }
        return "" ;
    }
}
