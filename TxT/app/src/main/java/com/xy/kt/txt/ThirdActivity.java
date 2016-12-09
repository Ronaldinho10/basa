package com.xy.kt.txt;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ThirdActivity extends AppCompatActivity {

    protected Button button;

    protected final static int EMOJI_COUNT = 60;

    protected RelativeLayout.LayoutParams layoutParams;

    protected int screenWidth;

    protected int screenHeight;

    protected RelativeLayout relativeLayout;

    protected ImageView view;

    protected List<ImageView> views = new ArrayList<>();

    protected List<Float> viewsY = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_third);
        relativeLayout = (RelativeLayout) findViewById(R.id.baselayout);
        DisplayMetrics dm = getResources().getDisplayMetrics();

        screenWidth = dm.widthPixels;

        screenHeight = dm.heightPixels;

        button = new Button(ThirdActivity.this);
        button.setText("Emoji Rain");
        layoutParams = new RelativeLayout.LayoutParams(dip2px(ThirdActivity.this, 320), dip2px(ThirdActivity.this, 60));
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        relativeLayout.addView(button, 0, layoutParams);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < EMOJI_COUNT; i++) {

                    addView();
                }

                for (int i = 0; i < EMOJI_COUNT; i++) {

                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(views.get(i), "translationY", viewsY.get(i), (float) (screenHeight)).setDuration(new Random().nextInt(2000) + 2500);
                    objectAnimator.start();
                }
            }
        });
    }

    public static int dip2px(Context context, float dpValue) {

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    int i = 0;

    public void addView() {
        view = new ImageView(ThirdActivity.this);
        view.setImageResource(R.mipmap.ic_gift);
        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        relativeLayout.addView(view, 1, layoutParams);
        view.setY(-(new Random().nextInt(440) + 90));
        view.setX(new Random().nextInt(screenWidth - dip2px(ThirdActivity.this, 100) - 10) + 10);
        view.setTag(i);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
                Toast.makeText(getApplication(), "红包：" + pos, Toast.LENGTH_SHORT).show();
            }
        });
        views.add(view);
        viewsY.add(view.getY());
        i++;
    }
}

