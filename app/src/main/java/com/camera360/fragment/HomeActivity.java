package com.camera360.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.camera360.fragment.day2.SimpleActivity;
import com.camera360.fragment.day3.FragmentPagerSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouwei on 14-9-18.
 */
public class HomeActivity extends Activity {
    private ListView listview = null;
    private MyAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        listview = (ListView) findViewById(R.id.honeListview);
        adapter = new MyAdapter();
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(HomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(HomeActivity.this, SecondActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(HomeActivity.this, CommunicationActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(HomeActivity.this, SimpleActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(HomeActivity.this, FragmentPagerSupport.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    public class MyAdapter extends BaseAdapter {
        private List<String> list = new ArrayList<String>();

        public MyAdapter() {
            loadData();
        }

        public void loadData() {
            list.add("xml布局文件中使用fragment");
            list.add("代码添加fragment");
            list.add("fragment与activity交互");
            list.add("fragment与Activity交互2");
            list.add("fragment+viewpager示例");
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Viewholder holder = null;
            if (convertView == null) {
                holder = new Viewholder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
                holder.tv = (TextView) convertView.findViewById(R.id.item_name);
                convertView.setTag(holder);
            }
            holder = (Viewholder) convertView.getTag();
            holder.tv.setText(list.get(position));
            return convertView;
        }

        class Viewholder {
            TextView tv;
        }
    }
}
