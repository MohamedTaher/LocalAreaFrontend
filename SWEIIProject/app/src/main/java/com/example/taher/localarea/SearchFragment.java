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
 * Created by taher on 22/03/16.
 */
public class SearchFragment extends Fragment implements View.OnClickListener{
    View rootview;

    private Home parant = null;
    private UserModel sorceUser = null;

    private EditText searchTxt;
    private Button search;
    private RadioButton name, place, brand;
    private ListView searchList;
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

    private void nameSearch() { // fill array list name ,id ,email

        users.clear();
        list.clear();


        HashMap<String, String> params = new HashMap<String, String>();

        params.put("name",searchTxt.getText().toString());

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
        conn.execute(Constants.SEARCH);

    }

    public void setParent(Home home)
    {
        parant = home;
    }

    public void setSourceUser(UserModel sourceUser) { this.sorceUser = sourceUser; }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.search_fragment, container, false);

        searchTxt = (EditText) rootview.findViewById(R.id.searchText);

        search = (Button) rootview.findViewById(R.id.searchButton);
        search.setOnClickListener(this);

        name = (RadioButton) rootview.findViewById(R.id.nameRadio);
        name.setOnClickListener(this);
        name.setChecked(true);

        place = (RadioButton) rootview.findViewById(R.id.placeRadio);
        place.setOnClickListener(this);

        brand = (RadioButton) rootview.findViewById(R.id.brandRadio);
        brand.setOnClickListener(this);

        searchList = (ListView) rootview.findViewById(R.id.searchList);
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                if (position >= 0) {
                    if (list.get(position) != "Empty List") {
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
        list.add("Empty List");

        adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, list);
        searchList.setAdapter(adapter);

        return rootview;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.nameRadio:
                place.setChecked(false);
                brand.setChecked(false);
                break;

            case R.id.placeRadio:
                name.setChecked(false);
                brand.setChecked(false);
                break;

            case R.id.brandRadio:
                place.setChecked(false);
                name.setChecked(false);
                break;

            case R.id.searchButton:
                if (searchTxt.getText().length() == 0) {
                    Toast.makeText(getContext(),
                            "Enter text to search on it", Toast.LENGTH_LONG)
                            .show();
                } else {
                    nameSearch();
                    if (e == false) adapter.notifyDataSetChanged();
                }
                break;

            default:
                return;
        }

    }

}
