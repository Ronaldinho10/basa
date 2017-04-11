package com.xy.kt.txt.pullscale;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.xy.kt.txt.R;

/**
 * Created by Z.T on 2017/4/11.
 * desc:
 */

public class PullToZoomHeadListView extends ListView implements AbsListView.OnScrollListener{

    private static final float FRICTION = 2.0f;
    private static final String TAG = "PullToZoomHeadListView";
    protected ListView mRootView;
    protected View mHeaderView;//头部View
    protected View mZoomView;//缩放拉伸View

    FrameLayout mHeaderContainer;

    protected int mScreenHeight;
    protected int mScreenWidth;

    private boolean isZoomEnabled = true;
    private boolean isZooming = false;

    private int mTouchSlop;
    private boolean mIsDragging = false;

    private float mLastMotionY;
    private float mLastMotionX;

    private float mInitialMotionY;
    private float mInitialMotionX;

    private int mHeaderHeight;

    public PullToZoomHeadListView(Context context) {
        this(context,null);
    }

    public PullToZoomHeadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){
        ViewConfiguration config = ViewConfiguration.get(context);
        mTouchSlop = config.getScaledTouchSlop();

        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        mScreenHeight = localDisplayMetrics.heightPixels;
        mScreenWidth = localDisplayMetrics.widthPixels;

        // By passing the attrs, we can add ListView/GridView params via XML
        mRootView = this;
        if(attrs != null){
            LayoutInflater mLayoutInflater = LayoutInflater.from(getContext());
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PullToZoomView);
            int zoomViewResId = a.getResourceId(R.styleable.PullToZoomView_zoomView, 0);
            if (zoomViewResId > 0) {
                mZoomView = mLayoutInflater.inflate(zoomViewResId, null, false);
            }
            int headerViewResId = a.getResourceId(R.styleable.PullToZoomView_headerView,0);
            if(headerViewResId > 0){
                mHeaderView = mLayoutInflater.inflate(headerViewResId,null,false);
            }

            mHeaderContainer = new FrameLayout(getContext());
            if (mZoomView != null) {
                mHeaderContainer.addView(mZoomView);
            }
            if (mHeaderView != null) {
                mHeaderContainer.addView(mHeaderView);
            }
            mRootView.addHeaderView(mHeaderContainer);
            a.recycle();
        }
    }

    public boolean isPullToZoomEnabled() {
        return isZoomEnabled;
    }

    private boolean isFirstItemVisible() {
        final Adapter adapter = mRootView.getAdapter();
        if (null == adapter || adapter.isEmpty()) {
            return true;
        } else {
            /**
             * This check should really just be:
             * mRootView.getFirstVisiblePosition() == 0, but PtRListView
             * internally use a HeaderView which messes the positions up. For
             * now we'll just add one to account for it and rely on the inner
             * condition which checks getTop().
             */
            if (mRootView.getFirstVisiblePosition() <= 1) {
                final View firstVisibleChild = mRootView.getChildAt(0);
                if (firstVisibleChild != null) {
                    return firstVisibleChild.getTop() >= mRootView.getTop();
                }
            }
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isPullToZoomEnabled()) {
            return false;
        }
        final int action = ev.getAction();
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            Log.i("onInterceptTouchEvent  ","action == MotionEvent.ACTION_UP");
            mIsDragging = false;
            return false;
        }

        if (action != MotionEvent.ACTION_DOWN && mIsDragging) {
            Log.i("onInterceptTouchEvent  ","action != MotionEvent.ACTION_DOWN ");
            return true;
        }
        switch (action){
            case MotionEvent.ACTION_DOWN:
                if (isFirstItemVisible()) {
                    Log.i("onInterceptTouchEvent  ","ACTION_DOWN   isFirstItemVisible");
                    mLastMotionY = mInitialMotionY = ev.getY();
                    mLastMotionX = mInitialMotionX = ev.getX();
                    mIsDragging = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("onInterceptTouchEvent  ","ACTION_MOVE   isFirstItemVisible ");
                if (isFirstItemVisible()) {
                    Log.i("onInterceptTouchEvent  ","ACTION_MOVE   isFirstItemVisible ");
                    final float y = ev.getY(), x = ev.getX();
                    final float diff, oppositeDiff, absDiff;
                    // We need to use the correct values, based on scroll
                    // direction
                    diff = y - mLastMotionY;
                    oppositeDiff = x - mLastMotionX;
                    absDiff = Math.abs(diff);
                    if (absDiff > mTouchSlop && absDiff > Math.abs(oppositeDiff)) {
                        if (diff >= 1f && isFirstItemVisible()) {
                            mLastMotionY = y;
                            mLastMotionX = x;
                            mIsDragging = true;
                            Log.i("onInterceptTouchEvent  ","mIsDragging = true ");
                        }
                    }
                }
                break;
        }
        return mIsDragging;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isPullToZoomEnabled()) {
            return false;
        }
        if (ev.getAction() == MotionEvent.ACTION_DOWN && ev.getEdgeFlags() != 0) {
            return false;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                if (mIsDragging) {
                    Log.i("onTouchEvent  ","ACTION_MOVE   mIsBeingDragged ");
                    mLastMotionY = ev.getY();
                    mLastMotionX = ev.getX();
                    pullEvent();
                    isZooming = true;
                    return true;
                }
                break;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                if (mIsDragging) {
                    Log.i("onTouchEvent  ","ACTION_UP   mIsBeingDragged ");
                    mIsDragging = false;
                    // If we're already refreshing, just scroll back to the top
                    if (isZooming()) {
                        smoothScrollToTop();
                        isZooming = false;
                        return true;
                    }
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private void smoothScrollToTop() {
        resetView();
    }

    private void pullEvent() {
        final int newScrollValue;
        final float initialMotionValue, lastMotionValue;
        initialMotionValue = mInitialMotionY;
        lastMotionValue = mLastMotionY;
        newScrollValue = Math.round(Math.min(initialMotionValue - lastMotionValue, 0) / FRICTION);
        pullHeaderToZoom(newScrollValue);
    }

    protected void pullHeaderToZoom(int newScrollValue) {
        Log.d(TAG, "pullHeaderToZoom --> newScrollValue = " + newScrollValue);
        Log.d(TAG, "pullHeaderToZoom --> mHeaderHeight = " + mHeaderHeight);
        if (mHeaderContainer != null && mHeaderContainer.getAnimation() != null && !mHeaderContainer.getAnimation().hasStarted()) {
            mHeaderContainer.clearAnimation();
        }
        ViewGroup.LayoutParams localLayoutParams = mHeaderContainer.getLayoutParams();
        localLayoutParams.height = Math.abs(newScrollValue) + mHeaderHeight;
        mHeaderContainer.setLayoutParams(localLayoutParams);
    }

    public boolean isZooming() {
        return isZooming;
    }

    public void resetView(){
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(mHeaderContainer.getBottom(),mHeaderHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d(TAG, "resetView --> mHeaderContainer.getBottom() = " + mHeaderContainer.getBottom() + "getHeight="+mHeaderContainer.getHeight());
                int h =(Integer)valueAnimator.getAnimatedValue();
                mHeaderContainer.getLayoutParams().height = h;
                mHeaderContainer.requestLayout();
            }
        });
        valueAnimator.setDuration(200);
        valueAnimator.start();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mHeaderHeight == 0 && mHeaderContainer != null) {
            Log.d(TAG, "onLayout --> mHeaderHeight == " + mHeaderContainer.getHeight());
            mHeaderHeight = mHeaderContainer.getHeight();
        }
    }


    public void setHeaderLayoutParams(AbsListView.LayoutParams layoutParams) {
        if (mHeaderContainer != null) {
            mHeaderContainer.setLayoutParams(layoutParams);
            mHeaderHeight = layoutParams.height;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        if (mZoomView != null && isPullToZoomEnabled()) {
            float f = mHeaderHeight - mHeaderContainer.getBottom();
            Log.d(TAG, "onScroll --> f = " + f);
            if ((f > 0.0F) && (f < mHeaderHeight)) {
                int distance = (int) (0.65D * f);
                mHeaderContainer.scrollTo(0, -distance);
            } else if (mHeaderContainer.getScrollY() != 0) {
                mHeaderContainer.scrollTo(0, 0);
            }
        }
    }
}
