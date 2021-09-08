package com.java.rongyilang.home.newslist.placeholder;

import android.icu.text.SimpleDateFormat;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.util.Log;
import com.java.rongyilang.home.newslist.HomeItemAdapter;
import com.java.rongyilang.model.NewsData;
import com.java.rongyilang.model.Post;
import com.java.rongyilang.utils.NewsAPI;
import com.java.rongyilang.utils.NewsType;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceholderContent {

    public final List<PlaceholderItem> ITEMS = new ArrayList<PlaceholderItem>();

    public final Map<String, PlaceholderItem> ITEM_MAP = new HashMap<String, PlaceholderItem>();

    public String type;
    public RecyclerView view;

    public PlaceholderContent(String type, RecyclerView view) {
        this.type = type;
        this.view = view;
        updateData();
    }

    /* TODO */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateData() {
        NewsAPI newsAPI = new NewsAPI();

        Call<Post> call;

        Log.d("", "type = " + type);

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        String nowTime = formatter.format(date);
        Log.d("", nowTime);

        if (type.equals("推荐")) call = newsAPI.getAPI(30, "", nowTime, "", "");
        else call = newsAPI.getAPI(30, "", nowTime, "", type);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Post post = response.body();
                for (int i = 0; i < post.getData().size(); i++) {
                    NewsData newsData = post.getData().get(i);
                    Log.d("", "Category = " + newsData.getCategory());
                    addItem(createPlaceholderItem(
                            newsData.getNewsID(),
                            newsData.getTitle(),
                            newsData.getContent(),
                            newsData.getPublishTime(),
                            newsData.getPublisher(),
                            newsData.getImage(),
                            newsData.getVideo(),
                            newsData.getCategory()
                    ));
                }
                view.setAdapter(new HomeItemAdapter(ITEMS));

            }
            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("", "response: " + t.toString());
            }
        });

    }

    public void addItem(PlaceholderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private PlaceholderItem createPlaceholderItem(String id, String title, String text,
                                                  String time, String author, List<String> image,
                                                  String video, String category) {
        return new PlaceholderItem(id, title, text, time, author, image, video, category);
    }

    public class PlaceholderItem {
        public final String id;
        public final String title;
        public final String text;
        public final String time;
        public final String author;
        public final String video;
        public final String category;
        public final List<String> image;

        public PlaceholderItem(String id, String title, String text,
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