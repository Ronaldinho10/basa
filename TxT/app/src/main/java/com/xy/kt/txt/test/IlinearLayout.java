package com.xy.kt.txt.test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Z.T on 2017/4/11.
 * desc:
 */

public class IlinearLayout extends LinearLayout {

    private static final String TAG = "IlinearLayout";

    public IlinearLayout(Context context) {
        super(context);
    }

    public IlinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        boolean mIsBeingDragged = false;
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_MOVE: {
//                Log.i(TAG,"dispatchTouchEvent"+"--ACTION_MOVE");
//                break;
//            }
//            case MotionEvent.ACTION_DOWN: {
//                mIsBeingDragged = false;
//                Log.i(TAG,"dispatchTouchEvent"+"--ACTION_DOWN");
//                break;
//            }
//            case MotionEvent.ACTION_UP: {
//                mIsBeingDragged = false;
//                Log.i(TAG,"dispatchTouchEvent"+"--ACTION_UP");
//                break;
//            }
//        }
//        return mIsBeingDragged;
//    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean mIsBeingDragged = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                Log.i(TAG,"onInterceptTouchEvent"+"--ACTION_MOVE");
                break;
            }
            case MotionEvent.ACTION_DOWN: {
                mIsBeingDragged = false;
                Log.i(TAG,"onInterceptTouchEvent"+"--ACTION_DOWN");
                break;
            }
            case MotionEvent.ACTION_UP: {
                mIsBeingDragged = false;
                Log.i(TAG,"onInterceptTouchEvent"+"--ACTION_UP");
                break;
            }
        }
        return mIsBeingDragged;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean mIsBeingDragged = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                Log.i(TAG,"onTouchEvent"+"--ACTION_MOVE");
                break;
            }
            case MotionEvent.ACTION_DOWN: {
                mIsBeingDragged = false;
                Log.i(TAG,"onTouchEvent"+"--ACTION_DOWN");
                break;
            }
            case MotionEvent.ACTION_UP: {
                mIsBeingDragged = false;
                Log.i(TAG,"onTouchEvent"+"--ACTION_UP");
                break;
            }
        }
        return mIsBeingDragged;
    }
}
