package com.xy.kt.txt;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
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
 * Describe:
 */

public class MyLinearLayout3 extends LinearLayout {
    public MyLinearLayout3(Context context) {
        super(context);
    }

    public MyLinearLayout3(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private PointF mPtLastMove = new PointF();

    private float originalY;

    public float dip2px(Context context, float dpValue) {
        return  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public void onMove(float x, float y) {
        mOffsetX = x - mPtLastMove.x;
        mOffsetY = (y - mPtLastMove.y) / mResistance;
        mPtLastMove.set(x, y);
    }

    private float mOffsetX;
    private float mOffsetY;
    private float mResistance = 1.7f;


    private View topVew;
    private View conentView;

    public void setTopView(View topVew,View conentView){
        this.topVew = topVew;
        this.conentView = conentView;
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

    int topMargin;

    boolean IS_PULL_DOWN = false;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(conentView != null){
            MarginLayoutParams lp = (MarginLayoutParams) conentView.getLayoutParams();
            topMargin = lp.topMargin;
        }
    }

    private Scroller mScroller;

    private  void release(){
        finishAnim(topVew.getHeight());
//        if(mScroller == null){
//            mScroller = new Scroller(getContext());
//        }
//        mScroller.startScroll(0, 0, 0, topVew.getHeight() - 200, 300);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPtLastMove.set(ev.getX(),ev.getY());
                originalY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                onMove(ev.getX(), ev.getY());

//    Log.i("vvvv","IS_PULL_DOWN:"+conentView.getTop() + "       mOffsetY:"+mOffsetY +"  hei:"+conentView.getHeight());
                if(mOffsetY < 0){

                    if(IS_PULL_DOWN){

                        int reach = (int) mOffsetY;//(ev.getY() - originalY)
                        Log.i("vvv","reach:"+reach+"    getHeight:"+topVew.getHeight());

                        int topHeight = topVew.getHeight();
                        if(topHeight < 200){
                            topHeight = reach + topVew.getHeight();
                            if(topHeight < 0){
                                topHeight = 0;
                                IS_PULL_DOWN = false;
                            }
                            topVew.getLayoutParams().height = topHeight;
                            topVew.requestLayout();
                            return true;
                        }
                    }
                    if(show){
                        startAnim();
                    }
                    if(ANIM_IS_WORKING){
                        return true;
                    }
                }else{
                    if(!show && !canChildScrollUp(conentView)){
/*                        IS_PULL_DOWN = true;
//                        Log.i("vvv","lp.topMargin:"+conentView.getTop()+"  HEI:"+conentView.getHeight());
                        *//*if(conentView.getTop() >= dip2px(getContext(),80f)){
                            Log.i("vvv","conentView.getTop() >= dip2px(getContext(),80f)");
                            mOffsetY = 0f;
                            IS_PULL_DOWN = false;
                            show = !show;
                            return super.dispatchTouchEvent(ev);
                        }*//*
                        if(conentView.getTop() + mOffsetY >= 200){
                            mOffsetY = 200 - conentView.getTop();
                        }
                        Log.i("vvv","vvvvvv:"+mOffsetY);
                        conentView.offsetTopAndBottom((int) mOffsetY);
                        invalidate();
                        return true;*/
//                        startAnim();




                        IS_PULL_DOWN = true;
                        int reach = (int) mOffsetY;//(ev.getY() - originalY)
                        Log.i("vvv","reach:"+reach+"    getHeight:"+topVew.getHeight());

                        int topHeight = topVew.getHeight();
                        if(topHeight < 200){
                            topHeight = reach + topVew.getHeight();
                            if(topHeight > 200){
                                topHeight = 200;
                                show = !show;
                                IS_PULL_DOWN = false;
                            }
                            topVew.getLayoutParams().height = topHeight;
                            topVew.requestLayout();
                            return true;
                        }

                        /*if(topVew.getHeight() <= 200){
                            reach = reach > 200 ? 200 : reach;
                            topVew.getLayoutParams().height = reach;
                            topVew.requestLayout();
                            return true;
                        }else{
                            show = !show;
                            IS_PULL_DOWN = false;
                        }*/
                    }
                    if(ANIM_IS_WORKING){

                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.i("vvvv","topVew.getHeight()--  "+topVew.getHeight());
                if(topVew.getHeight() < 200 && topVew.getHeight() > 0){
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
            //显示view，高度从0变到height值
            va = ValueAnimator.ofInt(0,(int)dip2px(getContext(),100f));
        }else{
            //隐藏view，高度从height变为0
            va = ValueAnimator.ofInt((int)dip2px(getContext(),100f),0);
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
                Log.i("vvv","onAnimationEnd:onAnimationEnd");
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

    private boolean ANIM_IS_WORKING = false;
    private boolean show = true;


    private void finishAnim(int dest){
        if(!ANIM_IS_WORKING && topVew.getHeight() > 0){
            ValueAnimator  va = ValueAnimator.ofInt(dest,0);
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
                    Log.i("vvv","onAnimationEnd:onAnimationEnd");
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            va.setDuration(200);
            va.start();
        }
    }
}
