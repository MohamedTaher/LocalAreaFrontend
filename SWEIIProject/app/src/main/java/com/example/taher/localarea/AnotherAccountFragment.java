package com.example.taher.localarea;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.location.Location;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.taher.localarea.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by taher on 21/03/16.
 */
public class AnotherAccountFragment extends Fragment {
    private View rootview;
    private Button getUserLastLocation;
    private Context context;
    private UserModel user = null;
    private UserModel wanted = null;
    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    private FragmentManager fm;
    private MapFragment map;
    private MapFragment frag;
    private GoogleMap g_map;
    private final int[] MAP_TYPES = { GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE };
    private int curMapTypeIndex = 1;

    public void setUser(UserModel user, UserModel wanted) {
        this.user = user;
        this.wanted = wanted;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.another_account_fragment, container, false);
        rootview = inflater.inflate(R.layout.another_account_fragment, container, false);
        context = getContext();
        fm = getActivity().getFragmentManager();
        map = (MapFragment) fm.findFragmentById(R.id.mapfragment);
        g_map = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.mapfragment)).getMap();
        g_map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.hide(map);
//        ft.commit();
        getUserLastLocation = (Button) rootview.findViewById(R.id.getUserLastLocation);
        getUserLastLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get last coordinates first
                final LatLng lastLocation = new LatLng(wanted.getLat(), wanted.getLong());
                //intialize the map with the coordinates
                CameraUpdate camUpdate = CameraUpdateFactory.newLatLngZoom(lastLocation, 14);
                g_map.addMarker(new MarkerOptions().position(lastLocation).title(wanted.getName()+" was here!"));
                g_map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                g_map.animateCamera(camUpdate);
                //make the map visible
                FragmentManager fm = getActivity().getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.show(map);
                ft.commit();
                //change the text of the button

            }
        });

        return rootview;
    }
}
