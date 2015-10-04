package com.example.mukesh.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class listviewFragment extends Fragment {

    public listviewFragment() {
    }

    public void onCreate( Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

//    public void oncreateoptionrefesh( Menu menu, MenuInflater inflator ){
//        inflator.inflate(R.menu.menu_main, menu);
//    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_listview, container, false);

        String list[] = {"Today", "Tomorrow", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Next Sunday" };
//        MainActivity activity= new MainActivity();

        ArrayAdapter<String> weekforecast;

        ArrayList<String> weeklist = new ArrayList<String>(Arrays.asList(list));

        weekforecast = new ArrayAdapter<String>(getActivity(), R.layout.fragment_listview, R.id.listhead, weeklist);

        ListView listview = (ListView) rootview.findViewById(R.id.daylist);
        listview.setAdapter(weekforecast);

        return rootview;
    }
}
