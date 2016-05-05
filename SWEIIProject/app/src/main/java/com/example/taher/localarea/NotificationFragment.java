package com.example.taher.localarea;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by taher on 25/04/16.
 */
public class NotificationFragment extends Fragment {
    View rootview;
    Home home = null;
    private ArrayAdapter<String> adapter;
    private ArrayList<CheckinModel> notifyItem;
    private ArrayList<String> list;
    private ListView notifyList;

    private int userID = 0;

    public void setUserID(int userID) {
        this.userID = userID;
    }
    public void setHome(Home home){ this.home = home; }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.notification_fragment, container, false);

        notifyList = (ListView) rootview.findViewById(R.id.notifyList);
        notifyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                if (position >= 0) {
                    ViewCheckin viewcheckin = new ViewCheckin();
                    viewcheckin.setCheckin(notifyItem.get(position));
                    Fragment temp = viewcheckin;
                    home.replaceFragment("Home",temp);
                }
            }
        });


        list = new ArrayList<String>();
        notifyItem = new ArrayList<CheckinModel>();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userID", userID + "");

        Connection conn = new Connection(params, new ConnectionPostListener() {
            @Override
            public void doSomething(String result) {
                try {
                    JSONObject reader = new JSONObject(result);
                    if (reader != null){
                        JSONArray jitems = reader.getJSONArray("checkins");
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

                            notifyItem.add(checkin);
                            list.add(jCheckinData.getString("userNameAction") + " comment at your checkin - "+ checkin.getCheckinPlace().getName() +" - ");///user commented
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1, list);
                notifyList.setAdapter(adapter);

                adapter.notifyDataSetChanged();

            }
        });
        conn.execute(Constants.getCommentNotification);

        try {
            conn.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        Connection _conn = new Connection(params, new ConnectionPostListener() {
            @Override
            public void doSomething(String result) {
                try {
                    JSONObject reader = new JSONObject(result);
                    if (reader != null){
                        JSONArray jitems = reader.getJSONArray("checkins");
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

                            notifyItem.add(checkin);
                            list.add(jCheckinData.getString("userNameAction") + " like at your checkin");///user commented
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1, list);
                notifyList.setAdapter(adapter);

                adapter.notifyDataSetChanged();

            }
        });
        _conn.execute(Constants.getLikeNotification);

        try {
            _conn.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return rootview;
    }


}
