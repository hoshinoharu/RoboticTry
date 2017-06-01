package com.reharu.robotictry.components;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by hoshino on 2017/5/31.
 */

public class FollowTouchListener implements View.OnTouchListener {

    private RoboticSupWindow view ;
    private float oriX ;
    private float oriY ;
    private float oriLocX ;
    private float oriLocY ;
    private boolean hasMoved  ;
    public FollowTouchListener(RoboticSupWindow view) {
        this.view = view;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN :
                        hasMoved = false ;
                        oriX = event.getRawX() ;
                        oriY = event.getRawY() ;
                        oriLocX = view.getX() ;
                        oriLocY = view.getY() ;
                        break;
                    case MotionEvent.ACTION_MOVE :
                        hasMoved = true ;
                        view.setX(oriLocX + event.getRawX() - oriX);
                        view.setY(oriLocY + event.getRawY() - oriY);
                        view.updateViewPosition();
                        break;
                    case MotionEvent.ACTION_UP:
                        return hasMoved ;
                }
                return false;
    }
}
