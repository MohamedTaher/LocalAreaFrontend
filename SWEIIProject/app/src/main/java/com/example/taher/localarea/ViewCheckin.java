package com.example.taher.localarea;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.FocusFinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
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
 * Created by Kareem on 4/21/16.
 */
public class ViewCheckin extends Fragment {
    //The variables representing elements in the view in fragment
    private CheckinModel checkin;
    private MapFragment mapfrag;
    private GoogleMap gmap;
    private TextView checkinName;
    private TextView checkinDescription;
    private ListView commentsListView;
    private static View view = null;
    private TextView NoCommentsView;
    private EditText commentTextBox;
    private ImageButton sendCommentButton;
    private FragmentManager fm;
    private CommentAdapter commentsAdapter;
    private ArrayList<CommentView> comments = new ArrayList<CommentView>();




    //Setters and getters
    public  CheckinModel getCheckin() {
        return checkin;
    }
    public void setCheckin(CheckinModel checkin) {
        this.checkin = checkin;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.viewcheckinlayout, container, false);
            
        //Getting references to elements from the view, and setting these references
        checkinName = (TextView) view.findViewById(R.id.checkinName);
        checkinDescription = (TextView) view.findViewById(R.id.checkinDisc);
        commentTextBox = (EditText) view.findViewById(R.id.commentTextBox);
        commentTextBox.setHint("Enter a comment..");
        sendCommentButton = (ImageButton) view.findViewById(R.id.sendCommentButton);
        
        //Setting the map to view it on the fragment
        fm  = getActivity().getFragmentManager();
        mapfrag = (MapFragment) fm.findFragmentById(R.id.checkinmapfragment);
        gmap = ((MapFragment)fm.findFragmentById(R.id.checkinmapfragment)).getMap();
        
        //Setting the list of comments
        commentsListView = (ListView) view.findViewById(R.id.commentsListView);
        NoCommentsView = (TextView) view.findViewById(R.id.NoCommentsView);
        checkinName.setText(checkin.getuName());
        checkinDescription.setText(checkin.getDescription());
        
        //Preparing connection data to call the service and retrieve data from database
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("checkinID", checkin.getId()+"");
        Connection con = new Connection(params, new FetchComments());
        con.execute(Constants.getCommentsForCheckin);


        //Color is not working, always displays the default color
        NoCommentsView.setTextColor(Color.parseColor("#FF0000F4"));
        commentsListView.setEmptyView(view.findViewById(R.id.NoCommentsView));
        
        //Now the map is working, redirect the map to the place's location and adding a marker on it
        PlaceModel checkinPlace = checkin.getCheckinPlace();
        LatLng placeLatLng = new LatLng(checkinPlace.getLat(), checkinPlace.getLng());
        CameraUpdate camUpdate = CameraUpdateFactory.newLatLngZoom(placeLatLng, 14);
        //If want no map zoom, then uncomment this line and comment the line right above this one
//        CameraUpdate camUpdate = CameraUpdateFactory.newLatLng(placeLatLng);
        gmap.addMarker(new MarkerOptions().position(placeLatLng).title(checkinPlace.getName()));
        gmap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gmap.animateCamera(camUpdate);
        sendCommentButton.setOnClickListener(new SendCommentButton());
        return view;
    }


    //The action done after the data as been fetched from the database, parsing these data and filling it to the view
    private class FetchComments implements ConnectionPostListener {
        @Override
        public void doSomething(String result) {
            try {
                JSONObject reader = new JSONObject(result);
                if (reader != null) {
                    JSONArray jComments = reader.getJSONArray("comments");
                    for (int i = 0; i < jComments.length(); i++) {
                        CommentView comment = new CommentView();
                        JSONObject jComment = jComments.getJSONObject(i);
                        comment.checkinID = jComment.getInt("checkinID");
                        comment.comment = jComment.getString("desc");
                        comment.id = jComment.getInt("id");
                        comment.username = jComment.getString("userName");
                        comment.userImage = R.drawable.username_icon;
                        comments.add(comment);
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
    }

    //When the user makes a comment and presses the button, the function will send the comment to the DB
    private class MakeCommentPostListener implements ConnectionPostListener {
        @Override
        public void doSomething(String result) {
            try {
                JSONObject reader = new JSONObject(result);
                if(reader != null) {
                    String res = reader.getString("status");
                    if(res == "Error" || res == "SQL Error") {
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        return ;
                    }
                    CommentView comment = new CommentView();
                    comment.checkinID = (checkin.getId());
                    comment.userImage = R.drawable.username_icon;
                    comment.comment = commentTextBox.getText()+"";
                    comment.username = Home.user.getName();
                    comment.id = Integer.parseInt(res);
                    comment.userID = Home.user.getId();
                    commentsAdapter.add(comment);
                    commentsAdapter.notifyDataSetChanged();
                    commentTextBox.setText("");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class SendCommentButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String commentText = commentTextBox.getText()+"";
            if(commentText.length() == 0) {
                Toast.makeText(getContext(), "Enter a comment", Toast.LENGTH_SHORT).show();
                return;
            }
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("uID", Home.user.getId()+"");
            params.put("checkInID", checkin.getId() + "");
            params.put("desc", commentTextBox.getText() + "");
            Connection con = new Connection(params, new MakeCommentPostListener());
            con.execute(Constants.comment);
            View keyboard = getActivity().getCurrentFocus();
            if(keyboard != null) {
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
