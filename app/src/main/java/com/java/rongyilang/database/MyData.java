package com.java.rongyilang.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "MyTable")
public class MyData {

    @PrimaryKey
    @NonNull
    public String newsID;

    public List<String> image;
    public String publishTime;
    public String video;
    public String title;
    public String content;
    public String publisher;
    public String category;
    public boolean isHistory;
    public boolean isFavourite;

    @Ignore
    public MyData() {

    }

    public MyData(@NonNull String newsID, List<String> image, String publishTime, String video, String title,
                  String content, String publisher, String category, boolean isHistory, boolean isFavourite) {
        this.newsID = newsID;
        this.image = image;
        this.publishTime = publishTime;
        this.video = video;
        this.title = title;
        this.content = content;
        this.publisher = publisher;
        this.category = category;
        this.isHistory = isHistory;
        this.isFavourite = isFavourite;
    }

}