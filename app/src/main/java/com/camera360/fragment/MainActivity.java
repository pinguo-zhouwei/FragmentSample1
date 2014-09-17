package com.camera360.fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends ActionBarActivity {
private FragmentManager fm = null;
private FragmentTransaction ft = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);


        /*
        * the second method add fragment，第二种添加fragment的
        */

    /*     setContentView(R.layout.test_layout);
     fm = getSupportFragmentManager();
     ft = fm.beginTransaction();
        ArticleReaderFragment f = new ArticleReaderFragment();
     ft.add(R.id.layout_content,f);
     ft.commit();
*/
     }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
