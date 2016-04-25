package com.example.taher.localarea;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by root on 4/25/16.
 */
public class PlaceInfoFrag extends Fragment {
    private PlaceModel place;
    private View view;
    private GoogleMap gmap;
    private TextView placeName;
    private TextView placeDesc;
    private TextView checkins;
    private RatingBar placeRate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view == null)
            view = inflater.inflate(R.layout.place_info_frag, container, false);

        gmap = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.PlaceInfoFragMap)).getMap();
        placeName = (TextView) view.findViewById(R.id.PlaceInfoFragPlaceName);
        placeDesc = (TextView) view.findViewById(R.id.PlaceInfoFragDesc);
        checkins = (TextView) view.findViewById(R.id.PlaceInfoFragCheckins);
        placeRate = (RatingBar) view.findViewById(R.id.PlaceInfoFragRate);
        return view;
    }

    public void initiate(PlaceModel place) {
        this.place = place;
        placeName.setText(place.getName());
        placeDesc.setText(place.getDescription());
        checkins.setText(place.getNumberOfCheckins() + "");
        placeRate.setNumStars(5);
        placeRate.setRating((float) (place.getRateSum() * 1.0 / place.getUserNum()));
        placeRate.setEnabled(false);
        LatLng latlng = new LatLng(place.getLat(), place.getLng());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latlng, 14);
        gmap.addMarker(new MarkerOptions().position(latlng).title(place.getName()));
        gmap.moveCamera(cameraUpdate);
    }
}
