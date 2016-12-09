package com.xy.kt.txt;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Author: Z.T on 2016/12/6.
 * Describe: 上下滚动的textview
 */

public class VerticalScrollLayout extends LinearLayout {

    private static final String TAG = "VerticalScrollLayout";

    private List<View> viewList = new ArrayList<>();

    private VerticalScrollLayout verticalScrollLayout;

    public VerticalScrollLayout(Context context) {
        super(context);
        initView();
    }

    public VerticalScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public VerticalScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private Timer mTimer;

    private int viewHeight;

    private LinearLayout linearLayout;

    public void setData(List<View> views,int viewHeight){
        this.viewHeight = viewHeight;

        linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,viewHeight * 2);
        linearLayout.setLayoutParams(layoutParams);
        this.addView(linearLayout);

        viewList.clear();
        viewList.addAll(views);
        for(View view : viewList){
            linearLayout.addView(view);
        }

        if(mTimer != null){
            mTimer.cancel();
        }
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        },1000,1000);
    }

    public void destroyScrollText(){
        if(mTimer != null){
            mTimer.cancel();
        }
    }

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startAnim();
            Log.i(TAG,"START-SCROLL");
        }
    };

    private void initView(){
        verticalScrollLayout = this;
        this.setOrientation(VERTICAL);
    }
    private void startAnim(){
        if(viewList.size() > 1){
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(linearLayout, "translationY", 0,-viewHeight).setDuration(500);
            objectAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    View view = viewList.get(0);
                    viewList.remove(0);
                    viewList.add(view);
                    linearLayout.removeAllViews();
                    for(View itemView : viewList){
                        linearLayout.addView(itemView);
                    }
                    resetLayout();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            objectAnimator.start();
        }
    }

    private void resetLayout(){
        if(viewList.size() > 1){
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(linearLayout, "translationY", -viewHeight,0).setDuration(0);
            objectAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    int[] pos0 = new int[2];
                    linearLayout.getLocationOnScreen(pos0);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            objectAnimator.start();
        }
    }
}
