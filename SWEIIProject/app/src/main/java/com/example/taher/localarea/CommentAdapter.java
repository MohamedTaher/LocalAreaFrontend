package com.example.taher.localarea;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by root on 4/23/16.
 */
public class CommentAdapter extends ArrayAdapter<CommentView> {

    private Context context;
    private int resource;
//    CommentView[] data = null;
    ArrayList<CommentView> data = null;


//    public CommentAdapter(Context context, int resource, CommentView[] objects) {
//        super(context, resource, objects);
//        this.context = context;
//        this.resource = resource;
//        this.data = objects;
//    }


    public CommentAdapter(Context context, int resource, ArrayList<CommentView> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.data = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View row = convertView;
        CommentHolder holder = null;
        holder = new CommentHolder();
        if (row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.commentrow, parent, false);
        }
        holder.userImage = (ImageView)row.findViewById(R.id.commentUserImage);
        holder.username = (TextView)row.findViewById(R.id.commentUsername);
        holder.comment = (TextView)row.findViewById(R.id.userComment);

//        CommentView comment = data[position];
        CommentView comment = data.get(position);
        holder.username.setText(comment.username);
        holder.comment.setText(comment.comment);
        holder.userImage.setImageResource(comment.userImage);
        return row;
    }

    static class CommentHolder {
        ImageView userImage;
        TextView username;
        TextView comment;
    }
}
