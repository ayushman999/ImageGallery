package com.example.atg3.RoomDatabase;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class Photo {


    @PrimaryKey
    @NonNull
    public String id;

    @NonNull
    @ColumnInfo(name = "title")
    public String title;
    @NonNull
    @ColumnInfo(name = "url")
    public String photo_url;

    public Photo(@NonNull String id, @NonNull String title, @NonNull String photo_url) {
        this.id = id;
        this.title = title;
        this.photo_url = photo_url;
    }


    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(@NonNull String photo_url) {
        this.photo_url = photo_url;
    }
}
