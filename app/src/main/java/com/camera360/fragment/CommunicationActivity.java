package com.camera360.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

/**
 * Created by zhouwei on 14-9-18.
 */
public class CommunicationActivity extends ActionBarActivity implements ArticleListFragment2.OnArtcileSelectedListener {
    private FragmentManager fm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        fm = getSupportFragmentManager();
    }

    @Override
    public void onArticleSelected(Bundle bundle) {
        Toast.makeText(this, "selecet" + bundle.getString("content"), 1).show();
        //实现fragment中的接口方法，实现通信
        //   Bundle b = bundle;
        //   String content = bundle.getString("content");
        FragmentTransaction ft = fm.beginTransaction();
        ArticleReaderFragment2 fr2 = new ArticleReaderFragment2();
        fr2.setArguments(bundle);
        ft.replace(R.id.reader, fr2);
        ft.commit();
    }
}
