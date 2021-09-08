package com.java.rongyilang.utils;

import androidx.annotation.NonNull;

import com.google.android.exoplayer2.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;

public class NewsResponseInterceptor extends ResponseBodyInterceptor {

    @Override
    Response intercept(@NonNull Response response, String url, String body) throws IOException {

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(body);
            JSONArray data = jsonObject.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject news = data.getJSONObject(i);
                Object image = news.get("image");
                if (image instanceof String) {
                    String str = (String) image;
                    str = str.substring(1, str.length() - 1);
                    List<String> images = new ArrayList<>(Arrays.asList(str.split(",")));
                    images.removeAll(Arrays.asList("", " "));
                    for (int j = 0; j < images.size(); j++) {
                        String nowString = images.get(j);
                        images.set(j, nowString.trim());
                    }
                    news.remove("image");
                    news.put("image", new JSONArray(images));
                }
            }

            MediaType contentType = response.body().contentType();
            ResponseBody responseBody = ResponseBody.create(contentType, jsonObject.toString());
            return response.newBuilder().body(responseBody).build();

        } catch (Exception e) {
            e.printStackTrace();
            MediaType contentType = response.body().contentType();
            ResponseBody responseBody = ResponseBody.create(contentType, body);
            return response.newBuilder().body(responseBody).build();
        }
    }
}


abstract class ResponseBodyInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        String url = request.url().toString();
        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            long contentLength = responseBody.contentLength();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.getBuffer();

            if ("gzip".equals(response.headers().get("Content-Encoding"))) {
                GzipSource gzippedResponseBody = new GzipSource(buffer.clone());
                buffer = new Buffer();
                buffer.writeAll(gzippedResponseBody);
            }

            MediaType contentType = responseBody.contentType();
            Charset charset;
            if (contentType == null || contentType.charset(StandardCharsets.UTF_8) == null) {
                charset = StandardCharsets.UTF_8;
            } else {
                charset = contentType.charset(StandardCharsets.UTF_8);
            }

            if (charset != null && contentLength != 0L) {
                return intercept(response, url, buffer.clone().readString(charset));
            }
        }
        return response;
    }

    abstract Response intercept(@NonNull Response response, String url, String body) throws IOException;
}