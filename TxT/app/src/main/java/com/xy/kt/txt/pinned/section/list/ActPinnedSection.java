package com.xy.kt.txt.pinned.section.list;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xy.kt.txt.R;
import com.xy.kt.txt.pinned.section.list.PinnedSectionListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Z.T on 2016/12/9.
 * Describe:
 */

public class ActPinnedSection extends Activity {

    List<People> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_9);

        list = new ArrayList<>();
        for(int i = 0 ; i < 5 ; i++){
            People setion = new People(People.TYPE_SECTION,"小盖  "+i);
            list.add(setion);
            for(int k = 0 ; k < 5 ; k++){
                People item = new People(People.TYPE_ITEM,"小YIN  "+i);
                list.add(item);
            }
        }

        PeopleAdapter adapter = new PeopleAdapter();
        ((PinnedSectionListView)findViewById(R.id.list)).setShadowVisible(false);
        ((PinnedSectionListView)findViewById(R.id.list)).setAdapter(adapter);
    }

    class People{
        static final int TYPE_SECTION = 0;
        static final int TYPE_ITEM = 1;

        public int type = TYPE_ITEM;
        public String name;

        public People(int type, String name) {
            this.type = type;
            this.name = name;
        }
    }

    int id;

    class PeopleAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter{

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
            ViewHolder viewHolder;
            People people = (People) getItem(i);
            TextView textView = (TextView) view;
            if(textView == null){
                viewHolder = new ViewHolder();
                textView = (TextView) LayoutInflater.from(getApplicationContext()).inflate(android.R.layout.simple_list_item_1,viewGroup,false);
                textView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) view.getTag();
            }
            textView.setTextColor(Color.BLACK);
            textView.setText(people.name);
            switch (people.type){
                case People.TYPE_SECTION:
                    textView.setBackgroundColor(Color.LTGRAY);
                    break;
                case People.TYPE_ITEM:
                    textView.setBackgroundColor(Color.WHITE);
                    break;
            }
            return textView;
        }

        @Override
        public boolean isItemViewTypePinned(int viewType) {
            return viewType == People.TYPE_SECTION;
        }

        @Override
        public int getItemViewType(int position) {
            return list.get(position).type;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }
    }

    class ViewHolder{
        TextView name;
    }
}
