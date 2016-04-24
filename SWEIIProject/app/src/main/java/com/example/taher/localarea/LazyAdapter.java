package com.example.taher.localarea;


import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by taher on 20/04/16.
 */
public class LazyAdapter extends BaseAdapter {

    private Fragment fragment;
    private ArrayList<HashMap<String, String>> data;
    private ArrayList<CheckinModel> data2;
    private static LayoutInflater inflater=null;
    String uID;
    String checkinID;
    //public ImageLoader imageLoader;

//    public LazyAdapter(Fragment a, ArrayList<HashMap<String, String>> d) {
//        fragment = a;
//        data = d;
//        Context context = fragment.getContext();
//        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        //imageLoader=new ImageLoader(activity.getApplicationContext());
//    }



    public LazyAdapter(Fragment a, ArrayList<CheckinModel> d) {
        fragment = a;
        data2 = d;
        Context context = fragment.getContext();
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //imageLoader=new ImageLoader(activity.getApplicationContext());
    }

//    public int getCount() {
//        return data.size();
//    }
    public int getCount() {
        return data2.size();
    }

    public Object getItem(int position) {
        return data2.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);

        TextView title = (TextView)vi.findViewById(R.id.title); // title
        TextView artist = (TextView)vi.findViewById(R.id.artist); // name
        TextView duration = (TextView)vi.findViewById(R.id.duration); // duration




        final TextView likesNum = (TextView)vi.findViewById(R.id.likesNum);
        final TextView like = (TextView)vi.findViewById(R.id.likes);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (like.getText().toString().equals("Like")) {
                    like.setText("Unlike");
                    likesNum.setText((Integer.parseInt(likesNum.getText().toString()) + 1) + "");

                    // like do service
                    HashMap<String, String> params = new HashMap<String, String>();

                    params.put("uID",uID);
                    params.put("checkInID", checkinID);
                    Connection conn = new Connection(params, new ConnectionPostListener() {
                        @Override
                        public void doSomething(String result) {
                            try {
                                JSONObject reader = new JSONObject(result);
                                if (reader != null){

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    conn.execute(Constants.like);
                } else  {
                    like.setText("Like");
                    likesNum.setText((Integer.parseInt(likesNum.getText().toString()) - 1) + "");

                    // like undo service
                    HashMap<String, String> params = new HashMap<String, String>();

                    params.put("uID",uID);
                    params.put("checkInID", checkinID);
                    Connection conn = new Connection(params, new ConnectionPostListener() {
                        @Override
                        public void doSomething(String result) {
                            try {
                                JSONObject reader = new JSONObject(result);
                                if (reader != null){

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    conn.execute(Constants.unlike);
                }
            }
        });
        //ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image

//        HashMap<String, String> dic = new HashMap<String, String>();
//        dic = data.get(position);

        CheckinModel checkin = data2.get(position);

        // Setting all values in listview
//        title.setText(dic.get(HomeFragment.DESCRIPTIN));
//        artist.setText(dic.get(HomeFragment.USER_NAME));
//        duration.setText(dic.get(HomeFragment.Time));
//        uID = dic.get(HomeFragment.uID);
//        checkinID = dic.get(HomeFragment.checkinID);

        title.setText(checkin.getDescription());
        artist.setText(checkin.getuName());
        duration.setText(checkin.getDate());
        uID = checkin.getUserID() + "";
        checkinID = checkin.getId() + "";

        //likesNum.setText(dic.get(HomeFragment.numLikes));
        //imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
        return vi;
    }
}