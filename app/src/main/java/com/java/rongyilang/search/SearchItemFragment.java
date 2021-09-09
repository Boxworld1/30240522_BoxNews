package com.java.rongyilang.search;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.java.rongyilang.R;
import com.java.rongyilang.database.DataBase;
import com.java.rongyilang.history.placeholder.HistoryPlaceholderContent;
import com.java.rongyilang.search.placeholder.SearchPlaceholderContent;

public class SearchItemFragment extends Fragment {

    private static final String ARG_TYPE = "type";
    public Activity mActivity;
    private String mType;
    public Context mContext;
    public View viewRoot;
    public DataBase mDataBase;
    public RecyclerView recyclerView;
    public SearchPlaceholderContent mSearchPlaceholderContent;

    public SearchItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SearchItemFragment newInstance(String type) {
        SearchItemFragment fragment = new SearchItemFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.fragment_history_list, container, false);
        mDataBase = DataBase.getInstance(viewRoot.getContext());
        mActivity = getActivity();
        // Set the adapter
        if (viewRoot instanceof RecyclerView) {
            mContext = viewRoot.getContext();
            recyclerView = (RecyclerView) viewRoot;
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                    DividerItemDecoration.VERTICAL));

            mSearchPlaceholderContent = new SearchPlaceholderContent(mDataBase, viewRoot, recyclerView,"", "", "", "");
        }
        return viewRoot;
    }

}