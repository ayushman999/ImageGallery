package com.example.atg3.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class model {

    @SerializedName("stat")
    @Expose
    String stat;

    @SerializedName("photo")
    @Expose
    ArrayList<User> photo;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }



    public ArrayList<User> getPhoto() {
        return photo;
    }

    public void setPhoto(ArrayList<User> photo) {
        this.photo = photo;
    }
}
