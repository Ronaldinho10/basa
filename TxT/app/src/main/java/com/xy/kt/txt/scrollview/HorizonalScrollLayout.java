package com.xy.kt.txt.scrollview;

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
 * Describe: 左进右出滚动的textview
 */

public class HorizonalScrollLayout extends LinearLayout {

    private static final String TAG = "VerticalScrollLayout";

    private List<View> viewList = new ArrayList<>();

    private HorizonalScrollLayout verticalScrollLayout;

    public HorizonalScrollLayout(Context context) {
        super(context);
        initView();
    }

    public HorizonalScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public HorizonalScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private Timer mTimer;

    private int viewWidth;

    private LinearLayout linearLayout;

    public void setData(List<View> views,int viewWidth){
        this.viewWidth = viewWidth;

        linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(HORIZONTAL);
        LayoutParams layoutParams = new LayoutParams(viewWidth * views.size(),ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(-viewWidth * (views.size() - 1),0,0,0);

        linearLayout.setLayoutParams(layoutParams);
        this.addView(linearLayout);

        viewList.clear();
        for(int i = views.size()-1 ; i > -1 ; i--){
            viewList.add(views.get(i));
        }
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
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(linearLayout, "translationX", 0,viewWidth).setDuration(500);
            objectAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    View view = viewList.get(viewList.size() - 1);
                    viewList.remove(viewList.size() - 1);
                    viewList.add(0,view);
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
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(linearLayout, "translationX", viewWidth,0).setDuration(0);
            objectAnimator.start();
        }
    }
}
