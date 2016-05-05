package com.example.taher.localarea;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by taher on 20/04/16.
 */
public class LazyAdapter extends BaseAdapter {

    private Fragment fragment;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    private String uID;
    private String checkinID;
    private static ArrayList<View> rowsView = new ArrayList<View>();



    //public ImageLoader imageLoader;

    public LazyAdapter(Fragment a, ArrayList<HashMap<String, String>> d) {
        fragment = a;
        data = d;
        Context context = fragment.getContext();

        //imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public class ViewHolder
    {
        TextView title;
        TextView artist;
        TextView duration;
        TextView likesNum;
        TextView like;
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        final ViewHolder holder;
        if(convertView==null) {
            Context context = fragment.getContext();
            holder = new ViewHolder();
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_row, null);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.artist = (TextView) convertView.findViewById(R.id.artist);
            holder.duration = (TextView) convertView.findViewById(R.id.duration);
            holder.likesNum = (TextView)convertView.findViewById(R.id.likesNum);
            holder.like = (TextView)convertView.findViewById(R.id.likes);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.like.getText().toString().equals("Like")) {
                    holder.like.setText("Unlike");
                    holder.likesNum.setText((Integer.parseInt(holder.likesNum.getText().toString()) + 1) + "");

                    // like do service
                    HashMap<String, String> params = new HashMap<String, String>();

                    params.put("uID", Home.user.getId() + "");
                    params.put("checkInID", checkinID);
                    Connection conn = new Connection(params, new ConnectionPostListener() {
                        @Override
                        public void doSomething(String result) {
                            try {
                                JSONObject reader = new JSONObject(result);
                                if (reader != null) {

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    conn.execute(Constants.like);
                } else {
                    holder.like.setText("Like");
                    holder.likesNum.setText((Integer.parseInt(holder.likesNum.getText().toString()) - 1) + "");

                    // like undo service
                    HashMap<String, String> params = new HashMap<String, String>();

                    params.put("uID", Home.user.getId() + "");
                    params.put("checkInID", checkinID);
                    Connection conn = new Connection(params, new ConnectionPostListener() {
                        @Override
                        public void doSomething(String result) {
                            try {
                                JSONObject reader = new JSONObject(result);
                                if (reader != null) {

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

        HashMap<String, String> dic = new HashMap<String, String>();
        dic = data.get(position);

        // Setting all values in listview
        holder.title.setText(dic.get(HomeFragment.DESCRIPTIN));
        holder.artist.setText(dic.get(HomeFragment.USER_NAME));
        holder.duration.setText(dic.get(HomeFragment.Time));
        holder.likesNum.setText(dic.get(HomeFragment.numLikes));
        uID = dic.get(HomeFragment.uID);
        checkinID = dic.get(HomeFragment.checkinID);


        HashMap<String, String> params = new HashMap<String, String>();

        params.put("userID",Home.user.getId()+""+"");
        params.put("chinID", checkinID);
        Connection conn = new Connection(params, new ConnectionPostListener() {
            @Override
            public void doSomething(String result) {
                try {
                    JSONObject reader = new JSONObject(result);
                    if (reader != null){
                        String status = reader.getString("status");

                        if (status == "") {
                            holder.like.setText("Like");
                        } else {
                            holder.like.setText("Unlike");
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        conn.execute(Constants.checkLike);

        try {
            conn.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        //likesNum.setText(dic.get(HomeFragment.numLikes));
        //imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
        return convertView;
    }

    public void setData(ArrayList<HashMap<String, String>> data) {
        this.data = data;
    }
}