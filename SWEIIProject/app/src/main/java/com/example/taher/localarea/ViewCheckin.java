package com.example.taher.localarea;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by root on 4/21/16.
 */
public class ViewCheckin extends Fragment {
    private PlaceModel checkinPlace;
    private CheckinModel checkin;
    private MapFragment mapfrag;
    private GoogleMap gmap;
    private EditText checkinName;
    private EditText checkinDescription;
    private ListView commentsListView;
    private View view;
    private FragmentManager fm;

    public CheckinModel getCheckin() {
        return checkin;
    }

    public void setCheckin(CheckinModel checkin) {
        this.checkin = checkin;
    }

    public PlaceModel getCheckinPlace() {
        return checkinPlace;
    }

    public void setCheckinPlace(PlaceModel checkinPlace) {
        this.checkinPlace = checkinPlace;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.viewcheckinlayout, container, false);
        fm  = getActivity().getFragmentManager();
        mapfrag = (MapFragment) fm.findFragmentById(R.id.checkinmapfragment);
        gmap = ((MapFragment)fm.findFragmentById(R.id.checkinmapfragment)).getMap();
        commentsListView = (ListView) view.findViewById(R.id.commentsListView);
        //get comments from database instead of this standard initialization
        Comment[] comments = new Comment[10];
        for(int i = 0;i < 10;i++) {
            comments[i] = new Comment();
            comments[i].comment = "This is comment #" + i;
            comments[i].username = "User#" + i;
            comments[i].userImage = R.drawable.username_icon;
        }
        CommentAdapter commentsAdapter = new CommentAdapter(getContext(), view.getId(), comments);
        commentsListView.setAdapter(commentsAdapter);
//        LatLng placeLatLng = new LatLng(checkinPlace.getLat(), checkinPlace.getLng());
//        CameraUpdate camUpdate = CameraUpdateFactory.newLatLngZoom(placeLatLng, 14);
//        gmap.addMarker(new MarkerOptions().position(placeLatLng).title(checkinPlace.getName()));
//        gmap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        gmap.animateCamera(camUpdate);
        return view;
    }
}