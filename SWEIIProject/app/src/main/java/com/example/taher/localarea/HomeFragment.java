package com.example.taher.localarea;


import android.content.Intent;
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
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
public class HomeFragment extends Fragment implements View.OnClickListener{
    View rootview;
    Home home = null;
    // XML node keys
    //static final String KEY_SONG = "song"; // parent node
    //static final String KEY_ID = "id";
    static final String DESCRIPTIN = "title";
    static final String USER_NAME= "artist";
    static final String Time = "duration";
    static final String numLikes = "numLikes";
    static final String uID = "uID";
    static final String checkinID = "checkinID";
    //static final String KEY_THUMB_URL = "thumb_url";

    ListView list;
    LazyAdapter adapter;
    private RadioButton nearest, rate, checkin;
    ArrayList<HashMap<String, String>> checkinsList = new ArrayList<HashMap<String, String>>();
    ArrayList<CheckinModel> listOfCheckins = new ArrayList<CheckinModel>();

    private void getList(String type)
    {
        adapter=new LazyAdapter(this, checkinsList);
        HashMap<String, String> params = new HashMap<String, String>();

        params.put("userID", home.user.getId() + "");
        params.put("type", type);

        Connection conn = new Connection(params, new ConnectionPostListener() {
            @Override
            public void doSomething(String result) {
                listOfCheckins = new ArrayList<CheckinModel>();
                checkinsList = new ArrayList<HashMap<String, String>>();

                try {
                    JSONObject reader = new JSONObject(result);
                    if (reader != null){

                        JSONArray jCheckins = reader.getJSONArray("checkins");
                        for (int i = 0; i < jCheckins.length(); i++) {
                            JSONObject jCheckinData = jCheckins.getJSONObject(i);
                            CheckinModel checkin = new CheckinModel(
                                    jCheckinData.getString("description"),
                                    jCheckinData.getString("date"),
                                    Integer.parseInt(jCheckinData.getString("placeID")),
                                    Integer.parseInt(jCheckinData.getString("userID")),
                                    Integer.parseInt(jCheckinData.getString("id")),
                                    Integer.parseInt(jCheckinData.getString("likes")),
                                    Integer.parseInt(jCheckinData.getString("comments")),
                                    new PlaceModel(
                                            Integer.parseInt(jCheckinData.getString("pid")),
                                            jCheckinData.getString("pname"),
                                            jCheckinData.getString("pdescription"),
                                            Double.parseDouble(jCheckinData.getString("plng")),
                                            Double.parseDouble(jCheckinData.getString("plat")),
                                            Integer.parseInt(jCheckinData.getString("puserid")),
                                            Integer.parseInt(jCheckinData.getString("pnumberofcheckins")),
                                            Integer.parseInt(jCheckinData.getString("prateSum")),
                                            Integer.parseInt(jCheckinData.getString("pusernum"))
                                    )
                                    ,jCheckinData.getString("uname")
                            );
                            listOfCheckins.add(checkin);
                            String date = checkin.getDate();
                            String temp = date.substring(0,10) + "\n" + date.substring(12);

                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put(DESCRIPTIN, checkin.getDescription());
                            map.put(USER_NAME,  checkin.getuName());
                            map.put(Time, temp);
                            map.put(numLikes, checkin.getLikes() + "");
                            map.put(uID, checkin.getUserID() + "");
                            map.put(checkinID, checkin.getId()+"");

                            checkinsList.add(map);
                        }
                    }
                    else
                        Toast.makeText(getContext(), "Empty List", Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter.setData(checkinsList);
                list.setAdapter(adapter);

            }
        });
        conn.execute(Constants.listOfCheckins);

        try {
            conn.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.home_fragment, container, false);

        String type = "";
        nearest = (RadioButton) rootview.findViewById(R.id.sortByNearest);
        rate = (RadioButton) rootview.findViewById(R.id.sortByRate);
        checkin = (RadioButton) rootview.findViewById(R.id.sortByNumCheckins);

        nearest.setChecked(true);



        nearest.setOnClickListener(this);
        rate.setOnClickListener(this);
        checkin.setOnClickListener(this);

        list=(ListView)rootview.findViewById(R.id.list);
        getList("3");
        // Click event for single list row/*
        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getContext(), "row " + position, Toast.LENGTH_LONG).show();
                //HERE

                ViewCheckin viewcheckin = new ViewCheckin();
                viewcheckin.setCheckin(listOfCheckins.get(position));
                Fragment temp = viewcheckin;
                home.replaceFragment("Home",temp);

                System.out.println("DONE");
            }
        });

        return rootview;

    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    @Override
    public void onClick(View v) {
        String type = "";
        switch(v.getId()){
            case R.id.sortByNearest:
                type = "3";
                rate.setChecked(false);
                checkin.setChecked(false);
                break;

            case R.id.sortByNumCheckins:
                type = "1";
                nearest.setChecked(false);
                rate.setChecked(false);
                break;

            case R.id.sortByRate:
                type = "2";
                nearest.setChecked(false);
                checkin.setChecked(false);
                break;

            default:
                return;
        }

        getList(type);
        //adapter=new LazyAdapter(this, checkinsList);
    }
}
