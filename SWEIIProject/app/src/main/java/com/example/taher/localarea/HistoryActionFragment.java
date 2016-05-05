package com.example.taher.localarea;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by taher on 26/04/16.
 */
public class HistoryActionFragment extends Fragment {

    View rootview;
    Home home = null;
    private ArrayAdapter<String> adapter;
    private ArrayList<CheckinModel> historyItems;
    private ArrayList<String> list;
    private ListView historyList;
    private ArrayList<String> types;

    private int userID = 0;

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setHome(Home home) { this.home = home;}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.historyactions_fragment, container, false);

        historyList = (ListView) rootview.findViewById(R.id.historyList);
        historyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                if (position >= 0) {
                    /*
                    ViewCheckin viewcheckin = new ViewCheckin();
                    viewcheckin.setCheckin(historyItems.get(position));
                    Fragment temp = viewcheckin;
                    home.replaceFragment("Home",temp);*/
                }
            }
        });


        list = new ArrayList<String>();
        historyItems = new ArrayList<CheckinModel>();
        types = new ArrayList<String>();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userID", userID + "");

        Connection conn = new Connection(params, new ConnectionPostListener() {
            @Override
            public void doSomething(String result) {
                try {
                    JSONObject reader = new JSONObject(result);
                    if (reader != null){
                        JSONArray jitems = reader.getJSONArray("Actions");
                        for (int i = 0; i < jitems.length(); i++)
                        {
                            JSONObject jCheckinData = jitems.getJSONObject(i);
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

                            historyItems.add(checkin);
                            types.add(jCheckinData.getString("type"));
                            list.add(jCheckinData.getString("type") + " on " + checkin.getPlace().getName());///user commented
                        }

                        adapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_list_item_1, list);
                        historyList.setAdapter(adapter);

                        adapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        conn.execute(Constants.getMyActions);

        try {
            conn.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        Button undo = (Button) rootview.findViewById(R.id.undo);
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (historyItems.size() > 0){
                HashMap<String, String> params = new HashMap<String, String>();

                params.put("type", types.get(0));
                params.put("userID", Home.user.getId() + "");
                params.put("chinID", historyItems.get(0).getId()+ "");


                Connection conn = new Connection(params, new ConnectionPostListener() {
                    @Override
                    public void doSomething(String result) {
                        try {
                            JSONObject reader = new JSONObject(result);
                            if (reader != null){
                                String str = reader.getString("status");
                                System.out.println(str);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
                conn.execute(Constants.UndoActions);


                //////////////////////////////////////////////////
                list.remove(0);
                adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1, list);
                historyList.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }
            }
        });



        return rootview;
    }


}

