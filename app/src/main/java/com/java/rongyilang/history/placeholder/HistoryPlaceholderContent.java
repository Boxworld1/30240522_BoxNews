package com.java.rongyilang.history.placeholder;

import android.provider.ContactsContract;

import com.google.android.exoplayer2.util.Log;
import com.java.rongyilang.database.DataBase;
import com.java.rongyilang.database.MyData;
import com.java.rongyilang.history.HistoryItemAdapter;
import com.java.rongyilang.utils.HistoryNewsCall;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.callback.Callback;

public class HistoryPlaceholderContent {

    public final List<HistoryPlaceholderItem> ITEMS = new ArrayList<HistoryPlaceholderItem>();
    public DataBase dataBase;

    public HistoryPlaceholderContent(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public void updateData(HistoryNewsCall newsCall) {
        ITEMS.clear();
        new Thread(()->{
            List<MyData> list = dataBase.getDataUao().searchHistoryData();
            Log.d("His", "DB count = " + list.size());
            for (int i = 0; i < list.size(); i++) {
                MyData nowData = list.get(i);
                Log.d("", "Title = " + nowData.title);
                addItem(createPlaceholderItem(nowData.newsID, nowData.title, nowData.content,
                        nowData.publishTime, nowData.publisher, nowData.image, nowData.video,
                        nowData.category));
            }
            Collections.reverse(ITEMS);
            newsCall.callback(ITEMS);
        }).start();
    }

    private void addItem(HistoryPlaceholderItem item) {
        ITEMS.add(item);
    }

    private HistoryPlaceholderItem createPlaceholderItem(String id, String title, String text,
                                                         String time, String author, List<String> image,
                                                         String video, String category) {
        return new HistoryPlaceholderItem(id, title, text, time, author, image, video, category);
    }

    public class HistoryPlaceholderItem {
        public final String id;
        public final String title;
        public final String text;
        public final String time;
        public final String author;
        public final String video;
        public final String category;
        public final List<String> image;

        public HistoryPlaceholderItem(String id, String title, String text,
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