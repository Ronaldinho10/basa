package com.xy.kt.txt;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Author: Z.T on 2016/11/26.
 * Describe: 自定义圆形View
 */

public class SevAct extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_17);
        final View sc = findViewById(R.id.id_v);
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sc.scrollTo(0,100);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sc.scrollTo(0,-100);
            }
        });
    }
}
