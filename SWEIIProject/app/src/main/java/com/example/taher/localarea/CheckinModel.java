package com.example.taher.localarea;

/**
 * Created by taher on 21/04/16.
 */
public class CheckinModel {
    private String description;
    private String date;
    private int placeID;
    private int userID;
    private int id;
    private int likes;
    private int comments;
    private PlaceModel checkinPlace;
    private String uName;

    public CheckinModel() {

    }

    public CheckinModel(String description, String date, int placeID, int userID, int id, int likes, int comments, PlaceModel checkinPlace, String uName) {
        this.description = description;
        this.date = date;
        this.placeID = placeID;
        this.userID = userID;
        this.id = id;
        this.likes = likes;
        this.comments = comments;
        this.checkinPlace = checkinPlace;
        this.uName = uName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPlaceID() {
        return placeID;
    }

    public void setPlaceID(int placeID) {
        this.placeID = placeID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public PlaceModel getCheckinPlace() {
        return checkinPlace;
    }

    public void setCheckinPlace(PlaceModel checkinPlace) {
        this.checkinPlace = checkinPlace;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }
}
