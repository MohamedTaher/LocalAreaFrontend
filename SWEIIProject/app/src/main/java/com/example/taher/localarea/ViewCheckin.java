package com.example.taher.localarea;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by root on 4/21/16.
 */
public class ViewCheckin extends Fragment {
    private CheckinModel checkin;
    private MapFragment mapfrag;
    private GoogleMap gmap;
    private TextView checkinName;
    private TextView checkinDescription;
    private ListView commentsListView;
    private static View view = null;
    private TextView NoCommentsView;
    private FragmentManager fm;
    private CommentAdapter commentsAdapter;
//    private ArrayList<CommentView> comments = new ArrayList<CommentView>();
    public  CheckinModel getCheckin() {
        return checkin;
    }
    private CommentView[] comments;
    public void setCheckin(CheckinModel checkin) {
        this.checkin = checkin;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.viewcheckinlayout, container, false);
        checkinName = (TextView) view.findViewById(R.id.checkinName);
        checkinDescription = (TextView) view.findViewById(R.id.checkinDisc);
        fm  = getActivity().getFragmentManager();
        mapfrag = (MapFragment) fm.findFragmentById(R.id.checkinmapfragment);
        gmap = ((MapFragment)fm.findFragmentById(R.id.checkinmapfragment)).getMap();
        commentsListView = (ListView) view.findViewById(R.id.commentsListView);
        NoCommentsView = (TextView) view.findViewById(R.id.NoCommentsView);

        checkinName.setText(checkin.getuName());
        checkinDescription.setText(checkin.getDescription());
        //get comments from database instead of this standard initialization
        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("uID", checkin.getUserID()+"");
        params.put("checkinID", checkin.getId()+"");
//        params.put("desc", checkin.getDescription());
//        Comment[] comments = new Comment[10];


        Connection con = new Connection(params, new ConnectionPostListener(){
            @Override
            public void doSomething(String result) {
                try {
                    JSONObject reader = new JSONObject(result);
                    if (reader != null) {
                        JSONArray jComments = reader.getJSONArray("comments");
                        comments = new CommentView[jComments.length()];
                        for (int i = 0; i < jComments.length(); i++) {
                            JSONObject jCheckinData = jComments.getJSONObject(i);
                            CommentView comment = new CommentView();
                            JSONObject jComment = jComments.getJSONObject(i);
                            comment.checkinID = jComment.getInt("checkinID");
                            comment.comment = jComment.getString("desc");
                            comment.id = jComment.getInt("id");
                            comment.username = jComment.getString("userName");
                            comments[i] = comment;
                        }
                        commentsAdapter = new CommentAdapter(getContext(), view.getId(), comments);
                        commentsListView.setAdapter(commentsAdapter);
                    }
                    else
                        Toast.makeText(getContext(), "Empty List", Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

//        String s = "";
//        try {
//            s = con.execute(Constants.getCommentsForCheckin).get();
//            con.onPostExecute(s);
            con.execute(Constants.getCommentsForCheckin);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
        //اللون مش عايز يطلع
        //هتشل يا ناس اللون مش بيطلع لا من هنا ولا من الفيو

        NoCommentsView.setTextColor(Color.parseColor("#FF0000F4"));
        commentsListView.setEmptyView(view.findViewById(R.id.NoCommentsView));
        PlaceModel checkinPlace = checkin.getCheckinPlace();
        LatLng placeLatLng = new LatLng(checkinPlace.getLat(), checkinPlace.getLng());
        CameraUpdate camUpdate = CameraUpdateFactory.newLatLngZoom(placeLatLng, 14);
//        CameraUpdate camUpdate = CameraUpdateFactory.newLatLng(placeLatLng);
        gmap.addMarker(new MarkerOptions().position(placeLatLng).title(checkinPlace.getName()));
        gmap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gmap.animateCamera(camUpdate);
        return view;
    }
}