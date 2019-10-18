package com.example.atg3.RoomDatabase;

import androidx.room.RoomDatabase;

import com.example.atg3.User.User;


@androidx.room.Database(entities = {User.class},version = 4,exportSchema = false)
public abstract class Database extends RoomDatabase {
        public abstract PhotoDao userDao();

}
