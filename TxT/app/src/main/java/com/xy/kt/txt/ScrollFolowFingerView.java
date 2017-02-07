package com.xy.kt.txt;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * Author: Z.T on 2017/2/6.
 * Describe:
 */

public class ScrollFolowFingerView extends View {
    public ScrollFolowFingerView(Context context) {
        super(context);
    }

    public ScrollFolowFingerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollFolowFingerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int mLastX;
    int mLastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                int translationX = (int) (ViewHelper.getTranslationX(this) + deltaX);
                int translationY = (int) (ViewHelper.getTranslationY(this) + deltaY);
                Log.v("vvv","deltaX "+deltaX+"  deltaY "+deltaY);
                Log.v("vvv","translationX "+translationX+"  translationY "+translationY);
                ViewHelper.setTranslationX(this,translationX);
                ViewHelper.setTranslationY(this,translationY);
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }
}
