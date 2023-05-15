package com.example.search.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {SearchEntity.class} ,version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SearchDao searchDao();
}
