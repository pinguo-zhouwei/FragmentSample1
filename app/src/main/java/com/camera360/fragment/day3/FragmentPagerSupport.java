package com.camera360.fragment.day3;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.camera360.fragment.R;

/**
 * Created by zhouwei on 14-9-22.
 */
public class FragmentPagerSupport extends ActionBarActivity {
    final int NUM = 10;
    MyAdapter mAdapter;

    ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pager);

        mAdapter = new MyAdapter(getSupportFragmentManager());

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        // Watch for button clicks.
        Button button = (Button) findViewById(R.id.goto_first);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mPager.setCurrentItem(0);
            }
        });
        button = (Button) findViewById(R.id.goto_last);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mPager.setCurrentItem(NUM - 1);
            }
        });


    }
}
