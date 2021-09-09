package com.java.rongyilang;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.util.Log;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.Chip;
import com.java.rongyilang.database.DataBase;
import com.java.rongyilang.database.MyData;
import com.java.rongyilang.history.HistoryItemFragment;
import com.java.rongyilang.search.SearchItemFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        setContentView(R.layout.fragment_search);

        String mKeyword = getIntent().getStringExtra("QueryKeyword");
        String mStartDate = "";
        String mEndDate = "";
        String mType = "";

        Log.d("", "Query word = " + mKeyword);
        SearchView mSearchView = findViewById(R.id.search_searchBar);

        SearchItemFragment searchItemFragment = SearchItemFragment.newInstance(mKeyword, mStartDate, mEndDate, mType);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.search_list_container, searchItemFragment).commit();

        searchItemFragment.update(mKeyword, mStartDate, mEndDate, mType);

        ImageView buttonClose = findViewById(R.id.search_close_button);
        ImageView buttonFilter = findViewById(R.id.search_filter_button);

        buttonClose.setOnClickListener((View.OnClickListener) view -> {
            finish();
        });

        buttonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
