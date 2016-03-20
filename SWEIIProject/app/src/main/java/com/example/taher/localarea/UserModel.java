package com.example.taher.localarea;

import java.io.Serializable;

/**
 * Created by taher on 20/03/16.
 */
public class UserModel implements Serializable {
    private int id;
    private String name;
    private String email;
    private String password;
    private double _long;
    private double lat;

    public UserModel(int id, String name, String email, String password, double _long, double lat) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this._long = _long;
        this.lat = lat;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getLong() {
        return _long;
    }

    public void setLong(double _long) {
        this._long = _long;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
