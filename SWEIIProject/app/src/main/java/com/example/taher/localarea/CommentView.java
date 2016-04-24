package com.example.taher.localarea;



/**
 * Created by Kareem on 4/23/16.
 */
public class CommentView {
    public int userImage = 0;
    public String username = "";
    public String comment = "";
    public int id;
    public int userID;
    public int checkinID;

    public CommentView(){
        super();
//        username = new String();
//        comment = new String();
    }

    public CommentView(int userImage, String username, String comment) {
        this.userImage = userImage;
        this.username = username;
        this.comment = comment;
    }


}
