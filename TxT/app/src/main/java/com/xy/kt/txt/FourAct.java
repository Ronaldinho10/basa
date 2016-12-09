package com.xy.kt.txt;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Z.T on 2016/11/26.
 * Describe:
 */

public class FourAct extends Activity {

    ListView myListView;

    View topVew;

    View empView;

    LinearLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_5);
        myListView = (ListView) findViewById(R.id.listview);
        rootView = (LinearLayout) findViewById(R.id.rootView);
        for(int i=0;i<6;i++){
            list.add("小盖  "+i);
        }
        topVew = findViewById(R.id.textView);
        myListView.setAdapter(new MAdapter());

        empView = findViewById(R.id.id_empty);

        myListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mPtLastMove.set(motionEvent.getX(),motionEvent.getY());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        onMove(motionEvent.getX(), motionEvent.getY());
                        if(topVew.getVisibility() == View.GONE){

                        }
                        if(mOffsetY > 1){
                            if(!canChildScrollUp(myListView) && topVew.getVisibility() == View.GONE){
                                topVew.setVisibility(View.VISIBLE);
                                myListView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        myListView.requestFocusFromTouch();//获取焦点
                                        myListView.setSelection(0);//10是你需要定位的位置
                                    }
                                });
                                IS_PULLING_DOWN = true;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
        myListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    IS_PULLING_DOWN = false;
                }
            }
            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int i1, int i2) {
                if(firstVisibleItem > 0 && !IS_PULLING_DOWN && topVew.getVisibility() == View.VISIBLE && !ANIM_IS_WORKING){
                    ANIM_IS_WORKING = true;
//                    topVew.setVisibility(View.GONE);
                    mPtLastMove.y += dip2px(FourAct.this,150);
//                    int h = (int) (rootView.getHeight()+dip2px(FourAct.this,100f));
//                    Log.i("vvv","height:"+h);
//                    ViewWrapper wrapper = new ViewWrapper(rootView);
//                    ObjectAnimator.ofInt(wrapper, "height", h).setDuration(2000).start();

                    performAnim2();
                }
            }
        });

    }

    private void performAnim2(){
        //View是否显示的标志
        show = !show;
        //属性动画对象
        ValueAnimator va ;
        if(show){
            //显示view，高度从0变到height值
            va = ValueAnimator.ofInt(0,(int)dip2px(FourAct.this,100f));
        }else{
            //隐藏view，高度从height变为0
            va = ValueAnimator.ofInt((int)dip2px(FourAct.this,100f),0);
        }
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //获取当前的height值
                int h =(Integer)valueAnimator.getAnimatedValue();
                Log.i("vvv","h:"+h+"      "+myListView.getLayoutParams().height);
//                //动态更新view的高度
                empView.getLayoutParams().height = h;
                empView.requestLayout();
            }
        });
        va.setDuration(1000);
        //开始动画
        va.start();
    }
    private boolean show = true;
    private boolean ANIM_IS_WORKING = false;

    private static class ViewWrapper {

        private View mTarget;

        public ViewWrapper(View target) {
            mTarget = target;
        }

        public int getWidth() {
            return mTarget.getLayoutParams().height;
        }

        public void setHeight(int height) {
            mTarget.getLayoutParams().height = height;
            mTarget.requestLayout();
        }
    }


    private boolean IS_PULLING_DOWN;

    public float dip2px(Context context, float dpValue) {
        return  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public void onMove(float x, float y) {
        float offsetX = x - mPtLastMove.x;
        float offsetY = (y - mPtLastMove.y);
        processOnMove(offsetX, offsetY);
        mPtLastMove.set(x, y);
    }

    protected void processOnMove(float offsetX, float offsetY) {
        setOffset(offsetX, offsetY / mResistance);
    }

    private float mOffsetX;
    private float mOffsetY;
    private float mResistance = 1.7f;

    protected void setOffset(float x, float y) {
        mOffsetX = x;
        mOffsetY = y;
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
            Log.i("gggggg","canScrollVertically(-1)=="+view.canScrollVertically(-1));
            return view.canScrollVertically(-1);
        }
    }


    private PointF mPtLastMove = new PointF();


    List<String> list = new ArrayList<>();

    class MAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view == null){
                view = LayoutInflater.from(FourAct.this).inflate(R.layout.item,null);
            }
            TextView tv = (TextView) view.findViewById(R.id.textView);
            tv.setText(list.get(i));
            return view;
        }
    }
}
