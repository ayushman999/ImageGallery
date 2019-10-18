package com.example.atg3.User;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    public long id;
    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    String title;

    @ColumnInfo(name = "url")
    @SerializedName("url_s")
    @Expose
    String url_s;

    public User(String title,String url_s) {
        this.title = title;
        this.url_s = url_s;
    }

    @NonNull
    public long getId() {
        return id;
    }




    public String getTitle() {
        return title;
    }

    public void setTitle( String title) {
        this.title = title;
    }


    public String getUrl_s() {
        return url_s;
    }

    public void setUrl_s( String url_s) {
        this.url_s = url_s;
    }
}
