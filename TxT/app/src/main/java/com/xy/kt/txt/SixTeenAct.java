package com.xy.kt.txt;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Author: Z.T on 2016/11/26.
 * Describe: 类似购物车 移除商品加动画
 */

public class SixTeenAct extends Activity {

    View button1;
    View button2;

    MyProgress myProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_16);
        myProgress = (MyProgress) findViewById(R.id.id_myprogress);
        button1  = findViewById(R.id.button1);
        button2  = findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myProgress.getStatus() != MyProgress.Status.LoadSuccess){
                    myProgress.setStatus(MyProgress.Status.LoadSuccess);
                    myProgress.startAnima();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myProgress.getStatus() != MyProgress.Status.LoadFail){
                    myProgress.setStatus(MyProgress.Status.LoadFail);
                    myProgress.failAnima();
                }
            }
        });
    }

    private void performAnim2(final View view){
        int height = view.getMeasuredHeight();
        ValueAnimator va = ValueAnimator.ofInt(height,0);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //获取当前的height值
                int h =(Integer)valueAnimator.getAnimatedValue();
                view.getLayoutParams().height = h;
                view.requestLayout();
            }
        });
        va.setDuration(400);
        //开始动画
        va.start();
    }
    int add = 1;
    private void performAnim(final View view){
        if(add > 1){
            return;
        }
        add++;
        int height = view.getMeasuredHeight();
        ValueAnimator va = ValueAnimator.ofInt(height,180);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //获取当前的height值
                int h =(Integer)valueAnimator.getAnimatedValue();
                view.getLayoutParams().height = h;
                view.requestLayout();
            }
        });
        va.setDuration(400);
        //开始动画
        va.start();
    }

}
