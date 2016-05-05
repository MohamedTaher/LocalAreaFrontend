package com.example.taher.localarea;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taher on 21/04/16.
 */
public class PlaceModel {
    private int id;
    private String name;
    private String description;
    private double lng, lat;
    private int userID;
    private int numberOfCheckins;
    private int rateSum;
    private int userNum;
    private View view;


    public PlaceModel() {
    }

    public PlaceModel(int id, String name, String description, double lng, double lat, int userID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.lng = lng;
        this.lat = lat;
        this.userID = userID;
    }

    public PlaceModel(int id, String name, String description, double lng, double lat, int userID, int numberOfCheckins, int rateSum, int userNum) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.lng = lng;
        this.lat = lat;
        this.userID = userID;
        this.numberOfCheckins = numberOfCheckins;
        this.rateSum = rateSum;
        this.userNum = userNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getNumberOfCheckins() {
        return numberOfCheckins;
    }

    public void setNumberOfCheckins(int numberOfCheckins) {
        this.numberOfCheckins = numberOfCheckins;
    }

    public int getRateSum() {
        return rateSum;
    }

    public void setRateSum(int rateSum) {
        this.rateSum = rateSum;
    }

    public int getUserNum() {
        return userNum;
    }

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }



}
