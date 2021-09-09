package com.java.rongyilang.utils;


import com.java.rongyilang.home.newslist.placeholder.PlaceholderContent;

import java.util.List;

public interface HomeNewsCall {
    void callback(List<PlaceholderContent.PlaceholderItem> news);
}
