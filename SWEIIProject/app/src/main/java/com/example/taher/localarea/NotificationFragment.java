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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.notification_fragment, container, false);

        notifyList = (ListView) rootview.findViewById(R.id.searchList);
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
                        JSONArray jUsers = reader.getJSONArray("list");
                        for (int i = 0; i < jUsers.length(); i++)
                        {
                            JSONObject jUserData = jUsers.getJSONObject(i);/*
                            UserModel temp = new UserModel(jUserData.getInt("id")
                                    ,jUserData.getString("name")
                                    ,jUserData.getString("email")
                                    ,"-1"
                                    ,jUserData.getDouble("long")
                                    ,jUserData.getDouble("lat"));

                            users.add(temp);
                            list.add(jUserData.getString("name"));*/
                        }
                    }
                    else {
                        Toast.makeText(getContext(), "Not Found",
                                Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Network Error", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1, list);
                notifyList.setAdapter(adapter);

                adapter.notifyDataSetChanged();

            }
        });
        conn.execute(Constants.SEARCH);

        try {
            conn.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return rootview;
    }


}
