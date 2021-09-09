package com.java.rongyilang.utils;



import com.java.rongyilang.search.placeholder.SearchPlaceholderContent;

import java.util.List;

public interface SearchNewsCall {
    void callback(List<SearchPlaceholderContent.SearchPlaceholderItem> news);
}
