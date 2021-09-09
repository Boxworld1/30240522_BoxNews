package com.java.rongyilang.history;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.java.rongyilang.R;
import com.java.rongyilang.database.DataBase;
import com.java.rongyilang.favourite.FavouriteItemFragment;

public class HistoryFragment extends Fragment {

    public Activity mActivity;
    public HistoryItemFragment historyItemFragment;
    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewRoot = inflater.inflate(R.layout.fragment_history, container, false);

        historyItemFragment = new HistoryItemFragment();

        getFragmentManager().beginTransaction()
                .add(R.id.his_list_container, historyItemFragment).commit();


        return viewRoot;
    }

    public void update() {
        historyItemFragment.update();
    }
}