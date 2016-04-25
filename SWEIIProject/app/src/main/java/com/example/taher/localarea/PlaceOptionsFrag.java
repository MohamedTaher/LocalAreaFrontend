package com.example.taher.localarea;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by root on 4/25/16.
 */
public class PlaceOptionsFrag extends Fragment {
    private PlaceModel place;
    private View view;
    private Button save, makeCheckin, rateButton;
    private EditText checkinDesc;
    private RatingBar rate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view == null)
            view = inflater.inflate(R.layout.place_options_frag, container, false);

        makeCheckin = (Button) view.findViewById(R.id.PlaceOptionsFragMakeCheckin);
        save = (Button) view.findViewById(R.id.PlaceOptionsFragSaveButton);
        rateButton = (Button) view.findViewById(R.id.PlaceOptionsFragRateButton);
        checkinDesc = (EditText) view.findViewById(R.id.PlaceOptionsFragDesc);
        rate = (RatingBar) view.findViewById(R.id.PlaceOptionsFragRateBar);
        rate.setNumStars(5);

        save.setOnClickListener(new saveActionListener());
        makeCheckin.setOnClickListener(new checkinActionListener());
        rateButton.setOnClickListener(new rateActionListener());

        return view;
    }

    public void initiate(PlaceModel place) {
        this.place = place;
    }



    private class saveActionListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

    private class rateActionListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

    private class checkinActionListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String desc = checkinDesc.getText()+"";
            if(desc.length() == 0) {
                Toast.makeText(getContext(), "Enter a description for your post", Toast.LENGTH_SHORT).show();
                return ;
            }
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("description", desc);
            params.put("userID", Home.user.getId()+"");
            params.put("placeID", place.getId()+"");
            Connection con = new Connection(params,new createCheckinPostListener());
            con.execute(Constants.createCheckin);
        }
    }

    private class createCheckinPostListener implements ConnectionPostListener {
        @Override
        public void doSomething(String result) {
            try {
                JSONObject jres = new JSONObject(result);
                String res = jres.getString("status");
                if(!res.equals("Success")) {
                    Toast.makeText(getContext(), "Something went wrong :/ " + res, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Check-in posted successfully :)", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
