package com.java.rongyilang.utils;

import com.java.rongyilang.model.Post;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsAPI {
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new NewsResponseInterceptor())
            .build();

    final Retrofit retrofit = new Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api2.newsminer.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private APIService service = retrofit.create(APIService.class);

    public Call<Post> getAPI(int size, String startDate, String endDate, String words, String categories) {
        Call<Post> call = service.fetchNews(size, startDate, endDate, words, categories);
        return call;
    }

}
