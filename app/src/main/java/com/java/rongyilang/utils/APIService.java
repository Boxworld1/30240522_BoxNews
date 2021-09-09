package com.java.rongyilang.utils;

import com.java.rongyilang.model.Post;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("svc/news/queryNewsList")

    Call<Post> fetchNews(@Query("size") int size,
                         @Query("startDate") String startDate,
                         @Query("endDate") String endDate,
                         @Query("words") String words,
                         @Query("categories") String categories,
                         @Query("page") int page
    );
}