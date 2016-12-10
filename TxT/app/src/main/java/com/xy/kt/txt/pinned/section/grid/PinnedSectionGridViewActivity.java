package com.xy.kt.txt.pinned.section.grid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xy.kt.txt.R;
import com.xy.kt.txt.pinned.section.grid.PinnedSectionGridView;
import com.xy.kt.txt.pinned.section.grid.SimpleSectionedGridAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Z.T on 2016/12/9.
 * Describe:
 */

public class PinnedSectionGridViewActivity extends Activity {

    List<People> list2;

    PeopleAdapter mAdapter;
    PinnedSectionGridView gridView;

    private ArrayList<SimpleSectionedGridAdapter.Section> sections = new ArrayList<SimpleSectionedGridAdapter.Section>();

    private String[] mHeaderNames = { "Cute Cats", "Few Cats", "Some Cats", "Some More Cats", "Some More More Cats"};
    private Integer[] mHeaderPositions = { 0, 6, 11, 37, 38 };

    class People{
        public String name;
        public People(String name) {
            this.name = name;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_11);
        gridView = (PinnedSectionGridView) findViewById(R.id.grid);

        WindowManager wm = this.getWindowManager();
        Display display = wm.getDefaultDisplay();

        list2 = new ArrayList<>();
        for(int i = 0 ; i < 50 ; i++){
            People setion = new People("小盖  "+i);
            list2.add(setion);
        }
        mAdapter = new PeopleAdapter();


        for (int i = 0; i < mHeaderPositions.length; i++) {
            sections.add(new SimpleSectionedGridAdapter.Section(mHeaderPositions[i], mHeaderNames[i]));
        }

        gridView.setNumColumns(2);

        SimpleSectionedGridAdapter simpleSectionedGridAdapter = new SimpleSectionedGridAdapter(this, R.layout.grid_item_new, mAdapter);
        simpleSectionedGridAdapter.setGridView(gridView);
        simpleSectionedGridAdapter.setScreenWidth(display.getWidth());
        simpleSectionedGridAdapter.setSections(sections.toArray(new SimpleSectionedGridAdapter.Section[0]));
        gridView.setAdapter(simpleSectionedGridAdapter);
    }

    class PeopleAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list2.size();
        }

        @Override
        public Object getItem(int i) {
            return list2.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            People people = (People) getItem(i);
            if(view == null){
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.grid_item,viewGroup,false);
                viewHolder.name = (TextView) view.findViewById(R.id.id_name);
                view.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.name.setText(people.name);
            return view;
        }
    }

    class ViewHolder{
        TextView name;
    }
}
