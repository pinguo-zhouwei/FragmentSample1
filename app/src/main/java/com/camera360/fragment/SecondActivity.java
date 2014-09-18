package com.camera360.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by zhouwei on 14-9-18.
 */
public class SecondActivity extends ActionBarActivity {
    private FragmentManager fm = null;
    private FragmentTransaction ft = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ArticleReaderFragment f = new ArticleReaderFragment();
        ft.add(R.id.layout_content, f);
        ft.commit();
    }
}
