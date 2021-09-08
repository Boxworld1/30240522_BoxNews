package com.java.rongyilang.favourite;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.java.rongyilang.R;

public class FavouriteFragment extends Fragment {

    FavouriteItemFragment favouriteItemFragment;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate(R.layout.fragment_favourite, container, false);

        favouriteItemFragment = new FavouriteItemFragment();
        getFragmentManager().beginTransaction().
                add(R.id.fav_list_container, favouriteItemFragment).commit();

        return viewRoot;
    }

    public void update() {
        favouriteItemFragment.update();
    }
}