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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by root on 4/27/16.
 */
public class PlaceAddView extends Fragment {
    private PlaceModel place;
    static private View view;
    private Button getPos, add;
    private TextView plng, plat, pname, pdesc;
    private EditText name, desc, lat, lng;
    private Home home;

    public void setHome(Home home) {
        this.home = home;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.place_add_view, container, false);
        }
        add = (Button) view.findViewById(R.id.placeAdd);
        getPos = (Button) view.findViewById(R.id.getPosition);


        plng = (EditText) view.findViewById(R.id.placeLng);
        plat = (EditText) view.findViewById(R.id.placeLat);
        pdesc = (EditText) view.findViewById(R.id.placeDesc);
        pname = (EditText) view.findViewById(R.id.PlaceName);

        add.setOnClickListener(new AddButtonListener());
        getPos.setOnClickListener(new GetPostListener());
        return view;
    }


    private class AddButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if((pname.getText()+"").length() == 0 || (pdesc.getText()+"").length() == 0 || (plng.getText()+"").length()==0 || (plat.getText()+"").length() == 0)
                return;
            String name = pname.getText()+"";
            String desc = pdesc.getText()+"";
            String lng = plng.getText()+"";
            String lat = plat.getText()+"";
            HashMap<String, String> params = new HashMap<>();
            params.put("name", name);
            params.put("description", desc);
            params.put("lng", lng);
            params.put("lat", lat);
            params.put("userID", Home.user.getId()+"");

            Connection con = new Connection(params, new AddPostListener());
            con.execute(Constants.addPlace);
        }
    }

    private class AddPostListener implements ConnectionPostListener {
        @Override
        public void doSomething(String result) {
            Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
        }
    }

    private class GetPostListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            plng.setText(Home.user.getLong()+"");
            plat.setText(Home.user.getLat()+"");
        }
    }


}
