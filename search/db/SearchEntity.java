package com.example.search.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "search")
public class SearchEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private String title;

    public SearchEntity(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "SearchEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
