package com.roi.audio2video.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

/**
 * Created by changquan on 2018/7/24.
 */
public class MyLinearlayout extends LinearLayout {
    private String Tag = "rrrrrrrrr";
    private int scaledTouchSlop;

    public MyLinearlayout(Context context) {
        this(context,null);
    }

    public MyLinearlayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyLinearlayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(Tag,"onTouchEvent_linear");
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                Log.e(Tag,"onTouchEvent_linear_down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(Tag,"onTouchEvent_linear_move");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(Tag,"onTouchEvent_linear_up");
                break;
        }
        return super.onTouchEvent(event);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e(Tag,"dispatchTouchEvent_linear");
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                Log.e(Tag,"dispatchTouchEvent_linear_down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(Tag,"dispatchTouchEvent_linear_move");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(Tag,"dispatchTouchEvent_linear_up");
                break;
        }
        return super.dispatchTouchEvent(event);
    }


    private float startX,startY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(Tag,"onInterceptTouchEvent_linear");
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                Log.e(Tag,"onInterceptTouchEvent_linear_down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(Tag,"onInterceptTouchEvent_linear_move");
                float dexX = Math.abs(ev.getX()-startX);
                float dexY = Math.abs(ev.getY()-startY);

                if (dexY > dexX && dexY > scaledTouchSlop)
                {
                    Log.e(Tag,"拦截纵向滑动");
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.e(Tag,"onInterceptTouchEvent_linear_up");
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }



  /*  @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(Tag,"onInterceptTouchEvent_linear");

        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float dexX = Math.abs(ev.getX()-startX);
                float dexY = Math.abs(ev.getY()-startY);

                if (dexY > dexX && dexY > scaledTouchSlop)
                {
                    Log.e(Tag,"拦截纵向滑动");
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }*/
}
