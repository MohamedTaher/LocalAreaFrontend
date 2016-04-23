package com.example.taher.localarea;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.taher.localarea.R;

/**
 * Created by taher on 21/03/16.
 */
public class HomeFragment extends Fragment {
    View rootview;

    // XML node keys
    //static final String KEY_SONG = "song"; // parent node
    //static final String KEY_ID = "id";
    static final String DESCRIPTIN = "title";
    static final String USER_NAME= "artist";
    static final String Time = "duration";
    static final String numLikes = "numLikes";
    //static final String KEY_THUMB_URL = "thumb_url";

    ListView list;
    LazyAdapter adapter;
    private RadioButton nearest, rate, checkin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.home_fragment, container, false);

        ArrayList<HashMap<String, String>> checkinsList = new ArrayList<HashMap<String, String>>();

        



        for (int i = 0; i < 10; i++) {


            // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();
            map.put(DESCRIPTIN, "i'm happy !!!!!!!!!!!!!!!!!!!!!!!!!!!!!" +
                    "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            map.put(USER_NAME, "Taher");
            map.put(Time, "2013/4/5\n12:00");
            map.put(numLikes, "0");

            checkinsList.add(map);
        }


        list=(ListView)rootview.findViewById(R.id.list);
        // Getting adapter by passing xml data ArrayList
        adapter=new LazyAdapter(this, checkinsList);
        list.setAdapter(adapter);
        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getContext(), "row " + position, Toast.LENGTH_LONG).show();
                //HERE
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ViewCheckin viewcheckin = new ViewCheckin();
                ft.replace(R.id.mainFrame, viewcheckin);
                ft.commit();
                System.out.println("DONE");
            }
        });

//        nearest = (RadioButton) rootview.findViewById(R.id.nameRadio);
//        nearest.setChecked(true);
//        nearest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                rate.setChecked(false);
//                checkin.setChecked(false);
//            }
//        });

//        rate = (RadioButton) rootview.findViewById(R.id.placeRadio);
//        rate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                nearest.setChecked(false);
//                checkin.setChecked(false);
//            }
//        });

//        checkin = (RadioButton) rootview.findViewById(R.id.brandRadio);
//        checkin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                nearest.setChecked(false);
//                rate.setChecked(false);
//            }
//        });

        return rootview;
    }
}