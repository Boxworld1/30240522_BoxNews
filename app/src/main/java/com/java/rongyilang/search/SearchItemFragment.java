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

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.util.Log;
import com.java.rongyilang.R;
import com.java.rongyilang.database.DataBase;
import com.java.rongyilang.history.HistoryItemAdapter;
import com.java.rongyilang.history.placeholder.HistoryPlaceholderContent;
import com.java.rongyilang.search.placeholder.SearchPlaceholderContent;
import com.java.rongyilang.utils.SearchNewsCall;

import java.util.List;

public class SearchItemFragment extends Fragment {

    private static final String ARG_TYPE = "";
    private static final String ARG_KEYWORD = "";
    private static final String ARG_START_DATE = "";
    private static final String ARG_END_DATE = "";
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
    public static SearchItemFragment newInstance(String keyword, String startDate,
                                                 String endDate, String type) {
        SearchItemFragment fragment = new SearchItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        args.putString(ARG_KEYWORD, keyword);
        args.putString(ARG_START_DATE, startDate);
        args.putString(ARG_END_DATE, endDate);
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

        }
        return viewRoot;
    }

    public void update(String keyword, String startDate, String endDate, String type) {
        mSearchPlaceholderContent = new SearchPlaceholderContent(mDataBase);
        Log.d("", "In ItemFragment: keyword=" + keyword);
        mSearchPlaceholderContent.updateData(new SearchNewsCall() {
            @Override
            public void callback(List<SearchPlaceholderContent.SearchPlaceholderItem> news) {
                mActivity.runOnUiThread(() -> {
                    if (news.size() == 0) {
                        Toast toast = Toast.makeText(viewRoot.getContext(), "找不到符合条件的內容！", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 0, 0);
                        toast.show();
                    } else {
                        recyclerView.setAdapter(new SearchItemAdapter(news));
                    }
                });
            }
        }, keyword, startDate, endDate, type);

    }

}