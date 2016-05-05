package com.example.taher.localarea;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kareem on 4/26/16.
 */




public class PlaceList extends Fragment {
    private View view;
    private ArrayAdapter<String> savedPlacesAdapter;
    private ArrayList<String> data = new ArrayList<>();
    private ArrayList<PlaceModel> places = new ArrayList<>();
    private ListView placesView;
    private Home home = null;

    public void setHome(Home home) {
        this.home = home;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.place_list, container, false);

        placesView = (ListView) view.findViewById(R.id.savedPlacesListView);
        HashMap<String, String> params = new HashMap<>();
        params.put("userID", Home.user.getId()+"");
        Connection con = new Connection(params, new PlaceListPostListener());
        con.execute(Constants.getSavedPlacesList);
//        placesView.setAdapter(savedPlacesAdapter);
        return view;
    }



    private class PlaceListPostListener implements ConnectionPostListener{
        @Override
        public void doSomething(String result) {
            try {
                JSONObject jres = new JSONObject(result);
                JSONArray jplaces = jres.getJSONArray("places");
                for(int i = 0;i < jplaces.length();i++) {
                    JSONObject jplace = jplaces.getJSONObject(i);
                    PlaceModel place = new PlaceModel();
                    place.setId(jplace.getInt("id"));
                    place.setName(jplace.getString("name"));
                    place.setDescription(jplace.getString("description"));
                    place.setLat(jplace.getDouble("lat"));
                    place.setLng(jplace.getDouble("lng"));
                    place.setUserID(jplace.getInt("userID"));
                    place.setNumberOfCheckins(jplace.getInt("numberOfCheckins"));
                    place.setRateSum(jplace.getInt("rateSum"));
                    place.setUserNum(jplace.getInt("userNum"));
                    places.add(place);
                    data.add(place.getName());
                }
                savedPlacesAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, data);
                placesView.setAdapter(savedPlacesAdapter);
                savedPlacesAdapter.notifyDataSetChanged();
                placesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        PlaceView placeFrag = new PlaceView();
                        placeFrag.setPlace(places.get(position));
//                        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
//                        ft.replace()
                        home.replaceFragment(places.get(position).getName(), placeFrag);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
