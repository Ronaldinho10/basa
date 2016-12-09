package com.xy.kt.txt;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Z.T on 2016/11/26.
 * Describe:
 */

public class SixAct extends Activity {

    VerticalScrollLayout verticalScrollLayout;

    HorizonalScrollLayout horizonalScrollLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_7);
        verticalScrollLayout = (VerticalScrollLayout) findViewById(R.id.id_vertical_layout);
        horizonalScrollLayout = (HorizonalScrollLayout) findViewById(R.id.id_horizonal);
//        View view = LayoutInflater.from(this).inflate(R.layout.item_view,null);
//        ((TextView)view.findViewById(R.id.textView2)).setText("小盖-1");
//        View view2 = LayoutInflater.from(this).inflate(R.layout.item_view,null);
//        ((TextView)view2.findViewById(R.id.textView2)).setText("小盖-2");
//        View view3 = LayoutInflater.from(this).inflate(R.layout.item_view,null);
//        ((TextView)view3.findViewById(R.id.textView2)).setText("小盖-3");
//        List<View> list = new ArrayList<>();
//        list.add(view);
//        list.add(view2);
//        list.add(view3);
//        verticalScrollLayout.setData(list,82);

        View view = LayoutInflater.from(this).inflate(R.layout.item_h,null);
        ((TextView)view.findViewById(R.id.textView2)).setText("小盖-1");
        View view2 = LayoutInflater.from(this).inflate(R.layout.item_h,null);
        ((TextView)view2.findViewById(R.id.textView2)).setText("小盖-2");
        View view3 = LayoutInflater.from(this).inflate(R.layout.item_h,null);
        ((TextView)view3.findViewById(R.id.textView2)).setText("小盖-3");
        List<View> list = new ArrayList<>();
        list.add(view);
        list.add(view2);
        list.add(view3);
        horizonalScrollLayout.setData(list,400);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        verticalScrollLayout.destroyScrollText();
        horizonalScrollLayout.destroyScrollText();
    }
}
