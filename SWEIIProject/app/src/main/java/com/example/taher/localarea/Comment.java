package com.example.taher.localarea;



/**
 * Created by Kareem on 4/23/16.
 */
public class Comment {
    public int userImage = 0;
    public String username = "";
    public String comment = "";

    public Comment(){
        super();
//        username = new String();
//        comment = new String();
    }

    public Comment(int userImage, String username, String comment) {
        this.userImage = userImage;
        this.username = username;
        this.comment = comment;
    }
}
