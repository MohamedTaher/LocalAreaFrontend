package com.example.taher.localarea;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by root on 4/23/16.
 */
public class CommentAdapter extends ArrayAdapter<Comment> {

    private Context context;
    private int resource;
    Comment[] data = null;


    public CommentAdapter(Context context, int resource, Comment[] objects) {
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

        Comment comment = data[position];
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
