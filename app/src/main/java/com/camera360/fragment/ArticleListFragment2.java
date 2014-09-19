package com.camera360.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouwei on 14-9-17.
 */
public class ArticleListFragment2 extends ListFragment {
    private ArrayAdapter<String> adapter = null;
    private List<String> list = new ArrayList<String>();
    private OnArtcileSelectedListener mListener;

    public ArticleListFragment2() {
    }

    /**
     * 自定义接口
     */
    public interface OnArtcileSelectedListener {
        public void onArticleSelected(Bundle bundle);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnArtcileSelectedListener) activity;
        } catch (ClassCastException e) {
            new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (int i = 0; i < 20; i++) {
            list.add("Article" + i);
        }
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, list);
        setListAdapter(adapter);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String content = adapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putString("content", content);

        ArticleReaderFragment2 fragment = new ArticleReaderFragment2();
        fragment.setArguments(bundle);
        //// ft.add(R.id.layout_content, fragment);
        mListener.onArticleSelected(bundle);
    }
}
