package com.java.rongyilang.utils;

import com.java.rongyilang.history.placeholder.HistoryPlaceholderContent;

import java.util.List;

public interface HistoryNewsCall {
    void callback(List<HistoryPlaceholderContent.HistoryPlaceholderItem> news);
}
