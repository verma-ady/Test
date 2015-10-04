package com.example.mukesh.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_listview, container, false);

        final String list[] = {"1 Jan - Clear - 36/14", "1 Jan - Clear - 36/14", "1 Jan - Clear - 36/14", "1 Jan - Clear - 36/14",
                        "1 Jan - Clear - 36/14", "1 Jan - Clear - 36/14", "1 Jan - Clear - 36/14",};
        //String list[] = {"Today", "Tomorrow", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Next Sunday" };
//        MainActivity activity= new MainActivity();
//        MainActivity.fetchweather fetch=new MainActivity.fetchweather();
//        fetch.execute("110085");

        ArrayAdapter<String> weekforecast;

        ArrayList<String> weeklist = new ArrayList<String>(Arrays.asList(list));

        weekforecast = new ArrayAdapter<String>(getActivity(), R.layout.fragment_listview, R.id.listhead, weeklist);

        final ListView listview = (ListView) rootview.findViewById(R.id.daylist);
        listview.setAdapter(weekforecast);

        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                String value;
                ArrayAdapter<String> week = (ArrayAdapter<String>) listview.getAdapter();
                value = week.getItem(position);
                Intent intent = new Intent( getActivity(), weatherActivity.class ).putExtra(Intent.EXTRA_TEXT, value );
                startActivity(intent);
            }
        });

        return rootview;
    }
}
