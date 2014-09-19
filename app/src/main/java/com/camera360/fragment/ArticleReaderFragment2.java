package com.camera360.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by zhouwei on 14-9-17.
 */
public class ArticleReaderFragment2 extends Fragment {

    public ArticleReaderFragment2() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reader, container, false);
        Bundle bundle = getArguments();
        String content = bundle.getString("content");
        TextView article = (TextView) view.findViewById(R.id.article);

        article.setText(content);
        return view;
    }
}
