package com.java.rongyilang.history;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.java.rongyilang.R;
import com.java.rongyilang.database.DataBase;
import com.java.rongyilang.history.placeholder.HistoryPlaceholderContent;
import com.java.rongyilang.home.newslist.HomeItemAdapter;

/**
 * A fragment representing a list of Items.
 */
public class HistoryItemFragment extends Fragment {

    private static final String ARG_TYPE = "type";
    public Activity mActivity;
    private String mType;
    public View viewRoot;
    public DataBase dataBase;
    public HistoryPlaceholderContent mHistoryPlaceholderContent;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HistoryItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HistoryItemFragment newInstance(String type) {
        HistoryItemFragment fragment = new HistoryItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mType = getArguments().getString(ARG_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.fragment_history_list, container, false);
        dataBase = DataBase.getInstance(viewRoot.getContext());
        mActivity = getActivity();
        // Set the adapter
        if (viewRoot instanceof RecyclerView) {
            update();
        }
        return viewRoot;
    }

    public void update() {
        Context context = viewRoot.getContext();
        RecyclerView recyclerView = (RecyclerView) viewRoot;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mHistoryPlaceholderContent = new HistoryPlaceholderContent(dataBase);

        mHistoryPlaceholderContent.updateData(news -> {
            mActivity.runOnUiThread(()->{
                recyclerView.setAdapter(new HistoryItemAdapter(news));
            });
        });
    }
}