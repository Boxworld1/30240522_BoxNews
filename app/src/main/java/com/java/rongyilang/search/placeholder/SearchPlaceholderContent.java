package com.java.rongyilang.search.placeholder;

import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.util.Log;
import com.java.rongyilang.database.DataBase;
import com.java.rongyilang.database.MyData;
import com.java.rongyilang.model.NewsData;
import com.java.rongyilang.model.Post;
import com.java.rongyilang.search.SearchItemAdapter;
import com.java.rongyilang.utils.NewsAPI;
import com.java.rongyilang.utils.SearchNewsCall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPlaceholderContent {

    public final List<SearchPlaceholderItem> ITEMS = new ArrayList<SearchPlaceholderItem>();
    public DataBase mDataBase;

    public SearchPlaceholderContent(DataBase dataBase) {
        mDataBase = dataBase;
    }

    public void updateData(SearchNewsCall newsCall, String keyword, String startDate, String endDate, String type) {
        ITEMS.clear();

        NewsAPI newsAPI = new NewsAPI();
        Call<Post> call;
        
        Log.d("", "In updateData with values { " + keyword + " , " + startDate + " , " + endDate + " , " + type + " }");
        if (type.equals("全部")) type = "";

        //addItem(createPlaceholderItem("214235", "On9", "3241w3523", "4352", " 432523", a, "", "o"));
        call = newsAPI.getAPI(30, startDate, endDate, keyword, type, 1);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Post post = response.body();
                for (int i = 0; i < post.getData().size(); i++) {
                    NewsData newsData = post.getData().get(i);
                    Log.d("", "Category = " + newsData.getCategory());
                    String mID = newsData.getNewsID();
                    String mTitle = newsData.getTitle();
                    String mText = newsData.getContent();
                    String mTime = newsData.getPublishTime();
                    String mAuthor = newsData.getPublisher();
                    List<String> mImage = newsData.getImage();
                    String mVideo = newsData.getVideo();
                    String mCategory = newsData.getCategory();

                    addItem(createPlaceholderItem(mID, mTitle, mText, mTime, mAuthor, mImage, mVideo, mCategory));

                }

                Collections.reverse(ITEMS);
                newsCall.callback(ITEMS);

            }
            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("", "response: " + t.toString());
            }
        });


    }

    private void addItem(SearchPlaceholderItem item) {
        ITEMS.add(item);
    }

    private SearchPlaceholderItem createPlaceholderItem(String id, String title, String text,
                                                         String time, String author, List<String> image,
                                                         String video, String category) {
        return new SearchPlaceholderItem(id, title, text, time, author, image, video, category);
    }

    public class SearchPlaceholderItem {
        public final String id;
        public final String title;
        public final String text;
        public final String time;
        public final String author;
        public final String video;
        public final String category;
        public final List<String> image;

        public SearchPlaceholderItem(String id, String title, String text,
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