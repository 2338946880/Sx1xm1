package com.example.search.db;

import androidx.room.Room;

import com.example.search.SearApp;

public class DBInstance {

    public static final String TAB_NAME="search1";

    public static AppDatabase appDatabase;

    public static AppDatabase getDatabase(){
        if(appDatabase == null){

            synchronized (DBInstance.class){
                 if (appDatabase==null){
                     appDatabase= Room.databaseBuilder(SearApp.content,AppDatabase.class,TAB_NAME)
                             .allowMainThreadQueries()
                             .build();
                 }
            }
        }
        return appDatabase;
    }
}
