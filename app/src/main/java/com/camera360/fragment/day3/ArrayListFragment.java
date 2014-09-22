package com.camera360.fragment.day3;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.camera360.fragment.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouwei on 14-9-22.
 */
public class ArrayListFragment extends ListFragment {
    public List<String> list = new ArrayList<String>();
    int mNum;

    /**
     * Create a new instance of CountingFragment, providing "num"
     * as an argument.
     */
    static ArrayListFragment newInstance(int num) {
        ArrayListFragment f = new ArrayListFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        for (int i = 0; i < 10; i++) {
            list.add("List条目：" + i);
        }
    }

    /**
     * The Fragment's UI is just a simple text view showing its
     * instance number.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pager_list, container, false);
        View tv = v.findViewById(R.id.text);
        ((TextView) tv).setText("Fragment #" + mNum);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, list));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i("FragmentList", "Item clicked: " + id);
    }
}


