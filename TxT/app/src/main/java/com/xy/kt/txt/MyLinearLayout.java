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

/**
 * Author: Z.T on 2016/11/26.
 * Describe:
 */

public class MyLinearLayout extends LinearLayout {
    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private PointF mPtLastMove = new PointF();

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


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPtLastMove.set(ev.getX(),ev.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                onMove(ev.getX(), ev.getY());
                if(mOffsetY < 0){
                    if(show){
                        startAnim();
                    }
                    if(ANIM_IS_WORKING){
                        return true;
                    }
                }else{
                    if(!show && !canChildScrollUp(conentView)){
                        startAnim();
                    }
                    if(ANIM_IS_WORKING){
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
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
}
