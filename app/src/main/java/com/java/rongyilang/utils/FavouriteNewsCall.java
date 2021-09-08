package com.java.rongyilang.utils;

import com.java.rongyilang.favourite.placeholder.FavouritePlaceholderContent;

import java.util.List;

public interface FavouriteNewsCall {
    void callback(List<FavouritePlaceholderContent.FavouritePlaceholderItem> news);

}
