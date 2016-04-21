package com.example.taher.localarea;


import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by taher on 20/04/16.
 */
public class LazyAdapter extends BaseAdapter {

    private Fragment fragment;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    //public ImageLoader imageLoader;

    public LazyAdapter(Fragment a, ArrayList<HashMap<String, String>> d) {
        fragment = a;
        data = d;
        Context context = fragment.getContext();
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //imageLoader=new ImageLoader(activity.getApplicationContext());
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
                if (likesNum.getText().toString().equals("Like")) {
                    like.setText("Unlike");
                    likesNum.setText((Integer.parseInt(likesNum.getText().toString()) + 1) + "");

                    // like do service
                } else  {
                    like.setText("Like");
                    likesNum.setText((Integer.parseInt(likesNum.getText().toString()) - 1) + "");

                    // like undo service
                }
            }
        });
        //ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image

        HashMap<String, String> dic = new HashMap<String, String>();
        dic = data.get(position);

        // Setting all values in listview
        title.setText(dic.get(HomeFragment.DESCRIPTIN));
        artist.setText(dic.get(HomeFragment.USER_NAME));
        duration.setText(dic.get(HomeFragment.Time));
        //likesNum.setText(dic.get(HomeFragment.numLikes));
        //imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
        return vi;
    }
}