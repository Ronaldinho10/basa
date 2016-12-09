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

public class FiveAct extends Activity {

    ListView myListView;

    View topVew;

    ScrollLinearLayout myLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_6);
        myListView = (ListView) findViewById(R.id.listview);
        for(int i=0;i<10;i++){
            list.add("小盖  "+i);
        }
        topVew = findViewById(R.id.id_empty);
        myLinearLayout = (ScrollLinearLayout) findViewById(R.id.rootView);
        myLinearLayout.setTopView(topVew, (int) dip2px(FiveAct.this,100f),myListView);
        myListView.setAdapter(new MAdapter());
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(FiveAct.this,"pos "+i,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public float dip2px(Context context, float dpValue) {
        return  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

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
                view = LayoutInflater.from(FiveAct.this).inflate(R.layout.item,null);
            }
            TextView tv = (TextView) view.findViewById(R.id.textView);
            tv.setText(list.get(i));
            view.findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(FiveAct.this,"button ",Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }
    }
}
