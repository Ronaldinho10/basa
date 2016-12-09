package com.xy.kt.txt;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.os.Debug;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Author: Z.T on 2016/11/26.
 * Describe: 头部可伸缩拖动的view
 */

public class ScrollLinearLayout extends LinearLayout {

    private static final String TAG = "MyLinearLayout";

    public ScrollLinearLayout(Context context) {
        super(context);
    }

    public ScrollLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private float mOffsetX;
    private float mOffsetY;
    private float mResistance = 1.7f;

    private View topVew;
    private View conentView;
    boolean READY_PULL_DOWN = false;
    private boolean ANIM_IS_WORKING = false;
    private boolean show = true;
    private boolean viewCanScroll = true;
    private int TOP_VIEW_ORIGIN_HEIGHT;


    private PointF mPtLastMove = new PointF();

    public void onMove(float x, float y) {
        mOffsetX = x - mPtLastMove.x;
        mOffsetY = (y - mPtLastMove.y) / mResistance;
        mPtLastMove.set(x, y);
    }

    public void setTopView(View topVew,int topViewHeight,View conentView){
        this.topVew = topVew;
        this.conentView = conentView;
        this.TOP_VIEW_ORIGIN_HEIGHT = topViewHeight;
        Log.i(TAG,"TOP_VIEW_ORIGIN_HEIGHT-:"+TOP_VIEW_ORIGIN_HEIGHT);
    }

    public void setViewCanScroll(boolean viewCanScroll){
        this.viewCanScroll = viewCanScroll;
    }

    public boolean canChildScrollUp(View view) {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (view instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) view;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return view.getScrollY() > 0;
            }
        } else {
            return view.canScrollVertically(-1);
        }
    }

    private  void release(){
        finishAnim(topVew.getHeight());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(!viewCanScroll){
            return super.dispatchTouchEvent(ev);
        }
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPtLastMove.set(ev.getX(),ev.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                onMove(ev.getX(), ev.getY());
                Log.i(TAG,"mOffsetY:"+mOffsetY+" READY_PULL_DOWN: "+READY_PULL_DOWN +"  topHeight < TOP_VIEW_ORIGIN_HEIGHT:"+(topVew.getHeight() < TOP_VIEW_ORIGIN_HEIGHT) +" !canChildScrollUp(conentView):"+!canChildScrollUp(conentView) +
                    " show:"+show);
                if(mOffsetY < 0){
                    if(READY_PULL_DOWN){
                        int reach = (int) mOffsetY;
                        int topHeight = topVew.getHeight();
                        if(topHeight < TOP_VIEW_ORIGIN_HEIGHT){
                            topHeight = reach + topVew.getHeight();
                            if(topHeight < 0){
                                topHeight = 0;
                                READY_PULL_DOWN = false;
                            }
                            topVew.getLayoutParams().height = topHeight;
                            topVew.requestLayout();
                            return true;
                        }
                    }else{
                        if(!canChildScrollUp(conentView)){
                            if(show){
                                startAnim();
                            }
                            if(ANIM_IS_WORKING){
                                return true;
                            }
                        }
                    }
                }else{
                    if(!show && !canChildScrollUp(conentView)){
                        READY_PULL_DOWN = true;
                        int reach = (int) mOffsetY;
                        int topHeight = topVew.getHeight();
                        if(topHeight < TOP_VIEW_ORIGIN_HEIGHT){
                            topHeight = reach + topVew.getHeight();
                            if(topHeight > TOP_VIEW_ORIGIN_HEIGHT){
                                topHeight = TOP_VIEW_ORIGIN_HEIGHT;
                                show = !show;
                                READY_PULL_DOWN = false;
                            }
                            topVew.getLayoutParams().height = topHeight;
                            topVew.requestLayout();
                            return true;
                        }
                        READY_PULL_DOWN = false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if(topVew.getHeight() < TOP_VIEW_ORIGIN_HEIGHT && topVew.getHeight() > 0){
                    release();
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void startAnim(){
        if(ANIM_IS_WORKING){
           return;
        }
        ANIM_IS_WORKING = true;
        //View是否显示的标志
        show = !show;
        //属性动画对象
        ValueAnimator va ;
        if(show){
            va = ValueAnimator.ofInt(0,TOP_VIEW_ORIGIN_HEIGHT);
        }else{
            va = ValueAnimator.ofInt(TOP_VIEW_ORIGIN_HEIGHT,0);
        }
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int h =(Integer)valueAnimator.getAnimatedValue();
                topVew.getLayoutParams().height = h;
                topVew.requestLayout();
            }
        });
        va.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                ANIM_IS_WORKING = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        va.setDuration(300);
        va.start();
    }

    private void finishAnim(int dest){
        if(!ANIM_IS_WORKING && topVew.getHeight() > 0){
            show = false;
            ValueAnimator  va = ValueAnimator.ofInt(dest,0);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int h =(Integer)valueAnimator.getAnimatedValue();
                    topVew.getLayoutParams().height = h;
                    topVew.requestLayout();
                    if(topVew.getHeight() == TOP_VIEW_ORIGIN_HEIGHT){
                        READY_PULL_DOWN = false;
                    }
                }
            });
            va.setDuration(200);
            va.start();
        }
    }
}
