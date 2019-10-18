package com.example.atg3;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.atg3.RoomDatabase.Photo;
import com.example.atg3.RoomDatabase.PhotoDao;

public class PhotoViewModel extends AndroidViewModel {

    public PhotoDao photoDao;
    public Photo photo;
    public PhotoViewModel(@NonNull Application application) {
        super(application);

        


    }
}
