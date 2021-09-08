package com.java.rongyilang.favourite.placeholder;

import com.google.android.exoplayer2.util.Log;
import com.java.rongyilang.database.DataBase;
import com.java.rongyilang.database.MyData;
import com.java.rongyilang.utils.FavouriteNewsCall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavouritePlaceholderContent {

    public final List<FavouritePlaceholderItem> ITEMS = new ArrayList<FavouritePlaceholderItem>();
    public DataBase dataBase;
    public FavouritePlaceholderContent(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    /* TODO */
    public void updateData(FavouriteNewsCall newsCall) {
        ITEMS.clear();
        new Thread(()->{
            List<MyData> list = dataBase.getDataUao().searchFavouriteData();
            for (int i = 0; i < list.size(); i++) {
                MyData nowData = list.get(i);
                addItem(createPlaceholderItem(nowData.newsID, nowData.title, nowData.content,
                        nowData.publishTime, nowData.publisher, nowData.image, nowData.video,
                        nowData.category));
            }
            Collections.reverse(ITEMS);
            newsCall.callback(ITEMS);
        }).start();
    }

    private void addItem(FavouritePlaceholderItem item) {
        ITEMS.add(item);
    }

    private FavouritePlaceholderItem createPlaceholderItem(String id, String title, String text,
                                                           String time, String author, List<String> image,
                                                           String video, String category) {
        return new FavouritePlaceholderItem(id, title, text, time, author, image, video, category);
    }

    public class FavouritePlaceholderItem {
        public final String id;
        public final String title;
        public final String text;
        public final String time;
        public final String author;
        public final String video;
        public final String category;
        public final List<String> image;

        public FavouritePlaceholderItem(String id, String title, String text,
                                        String time, String author, List<String> image,
                                        String video, String category) {
            this.id = id;
            this.time = time;
            this.text = text;
            this.image = image;
            this.title = title;
            this.author = author;
            this.video = video;
            this.category = category;
        }

    }
}