package com.example.taher.localarea;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.taher.localarea.R;
import com.google.android.gms.location.places.internal.PlaceLikelihoodEntity;

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
    private ArrayList<PlaceModel> places;
    private ArrayList<String> list;

    public UserModel getSorceUser() {
        return sorceUser;
    }

    public void setSorceUser(UserModel sorceUser) {
        this.sorceUser = sorceUser;
    }

    private void nameSearch() { // fill array list name ,id ,email

        searchList.setAdapter(null);
        list = new ArrayList<String>();
        users = new ArrayList<UserModel>();

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
                searchList.setAdapter(adapter);

                adapter.notifyDataSetChanged();

            }
        });
        conn.execute(Constants.SEARCH);
/*
        adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, list);
        searchList.setAdapter(adapter);*/
/*
        adapter = new ArrayAdapter<String>(this.rootview.getContext(),
                android.R.layout.simple_list_item_1, list);
        searchList.setAdapter(adapter);

        adapter.notifyDataSetChanged();*/

    }

    private void placeSearch(){
        searchList.setAdapter(null);
        list = new ArrayList<String>();
        places = new ArrayList<PlaceModel>();

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
                            PlaceModel temp = new PlaceModel(jUserData.getInt("id")
                                    ,jUserData.getString("name")
                                    ,jUserData.getString("description")
                                    ,jUserData.getDouble("lng")
                                    ,jUserData.getDouble("lat")
                                    ,jUserData.getInt("userID")
                            );
//                            PlaceModel temp = new PlaceModel();
//                            temp.setId(jUserData.getInt("id"));
//                            temp.setName(jUserData.getString("name"));
//                            temp.setDescription(jUserData.getString("description"));
//                            temp.setLat(jUserData.getDouble("lat"));
//                            temp.setLng(jUserData.getDouble("lng"));
//                            temp.setUserID(jUserData.getInt("userID"));

                            places.add(temp);
                            list.add(jUserData.getString("name"));
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
                searchList.setAdapter(adapter);

                adapter.notifyDataSetChanged();

            }
        });
        conn.execute(Constants.searchPlaceByName);
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
                        if (name.isChecked()){
                            UserModel wanted = users.get(position);
                            AnotherAccountFragment anotherUser = new AnotherAccountFragment();
                            anotherUser.setUser(sorceUser,wanted);
                            Fragment f = anotherUser;
                            parant.replaceFragment(wanted.getName(),f);
                        } else if (place.isChecked()){
                            int id  = places.get(position).getId();
                            /*
                            placeFragment _place = new placeFragment();
                            _place.setPlaceID(id);
                            parant.replaceFragment(places.get(position).getName(),_place);*/
                            PlaceView place = new PlaceView();
                            place.getPlace().setId(id);
                            parant.replaceFragment("placetst", place);
                        }

                    }

                }
            }
        });

        users = new ArrayList<UserModel>();
        list = new ArrayList<String>();

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
                    View keyboard = getActivity().getCurrentFocus();
                    if(keyboard != null) {
                        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    if (name.isChecked()) nameSearch();
                    else if (place.isChecked()) placeSearch();
                }
                break;

            default:
                return;
        }

    }

}
