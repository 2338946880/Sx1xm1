package com.example.search.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface SearchDao {
    @Insert
    void insert(SearchEntity searchEntities);

    @Query("delete  from search")
    void delete();


    @Query("select * from search")
    List<SearchEntity> getSearch();
}
