package com.example.taher.localarea;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.taher.localarea.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by taher on 24/03/16.
 */
public class FollowersFragment extends Fragment implements View.OnClickListener{
    View rootview;

    private Home parant = null;
    private UserModel sorceUser = null;

    private ListView followersList;
    private ArrayAdapter<String> adapter;
    private ArrayList<UserModel> users;
    private ArrayList<String> list;

    private boolean e = true;

    public UserModel getSorceUser() {
        return sorceUser;
    }

    public void setSorceUser(UserModel sorceUser) {
        this.sorceUser = sorceUser;
    }

    private void followersSearch() { // fill array list name ,id ,email

        users.clear();
        list.clear();


        HashMap<String, String> params = new HashMap<String, String>();

        params.put("ID",sorceUser.getId()+"");

        Connection conn = new Connection(params, new ConnectionPostListener() {
            @Override
            public void doSomething(String result) {
                try {
                    JSONObject reader = new JSONObject(result);
                    if (reader != null){
                        JSONArray jUsers = reader.getJSONArray("list");
                        for (int i = 0; i < jUsers.length(); i++)
                        {
                            JSONObject jUserData = jUsers.getJSONObject(i);
                            UserModel temp = new UserModel(jUserData.getInt("id")
                                    ,jUserData.getString("name")
                                    ,jUserData.getString("email")
                                    ,"-1"
                                    ,jUserData.getDouble("long")
                                    ,jUserData.getDouble("lat"));

                            users.add(temp);
                            list.add(jUserData.getString("name"));
                        }
                        e = false;
                    }
                    else {
                        list.add("Empty List");
                        e = true;
                        Toast.makeText(getContext(), "Not Found",
                                Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Network Error", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        });
        conn.execute(Constants.getFollowers);

    }

    public void setParent(Home home)
    {
        parant = home;
    }

    public void setSourceUser(UserModel sourceUser) { this.sorceUser = sourceUser; }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.followers_fragment, container, false);

        followersList = (ListView) rootview.findViewById(R.id.followersList);
        followersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                if (position >= 0) {
                    if (list.get(position) != "No Followers") {
                        /*Toast.makeText(getContext(),
                                list.get(position), Toast.LENGTH_LONG)
                                .show();*/

                        UserModel wanted = users.get(position);
                        AnotherAccountFragment anotherUser = new AnotherAccountFragment();
                        anotherUser.setUser(sorceUser,wanted);
                        Fragment f = anotherUser;
                        parant.replaceFragment(wanted.getName(),f);

                    }

                }
            }
        });

        users = new ArrayList<UserModel>();
        list = new ArrayList<String>();


        adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, list);
        followersList.setAdapter(adapter);

        list.add("No Followers");
        adapter.notifyDataSetChanged();

        /////////////////////////////////////////////////////////////////////////////
        followersSearch();
        if (e == false) adapter.notifyDataSetChanged();

        return rootview;
    }

    @Override
    public void onClick(View v) {

    }
}
