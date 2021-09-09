package com.java.rongyilang.home.newslist;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.rongyilang.R;
import com.java.rongyilang.database.DataBase;
import com.java.rongyilang.history.HistoryItemAdapter;
import com.java.rongyilang.history.placeholder.HistoryPlaceholderContent;
import com.java.rongyilang.home.newslist.placeholder.PlaceholderContent;

/**
 * A fragment representing a list of Items.
 */
public class HomeItemFragment extends Fragment {

    private static final String ARG_TYPE = "type";
    private String mType;
    public RecyclerView recyclerView;
    public DataBase mDataBase;
    public Activity mActivity;
    public Context mContext;
    public View viewRoot;
    public PlaceholderContent mPlaceholderContent;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomeItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HomeItemFragment newInstance(String type) {
        HomeItemFragment fragment = new HomeItemFragment();
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
        viewRoot = inflater.inflate(R.layout.fragment_item_list, container, false);
        mDataBase = DataBase.getInstance(viewRoot.getContext());
        mActivity = getActivity();
        // Set the adapter
        if (viewRoot instanceof RecyclerView) {
            mContext = viewRoot.getContext();
            recyclerView = (RecyclerView) viewRoot;
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                    DividerItemDecoration.VERTICAL));

            //recyclerView.setAdapter(new HomeItemAdapter(placeholderContent.ITEMS));
            update();
        }

        return viewRoot;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void update() {
        mPlaceholderContent = new PlaceholderContent(mType, viewRoot, mDataBase);

        mPlaceholderContent.updateData(news -> {
            mActivity.runOnUiThread(()->{
                recyclerView.setAdapter(new HomeItemAdapter(news));
            });
        });
    }
}