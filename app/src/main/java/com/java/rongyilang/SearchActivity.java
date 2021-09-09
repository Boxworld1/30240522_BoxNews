package com.java.rongyilang;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;

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

        String mKeyword = getIntent().getStringExtra("QueryKeyWord");

        SearchView mSearchView = findViewById(R.id.search_searchBar);

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

    public void startSearch() {

    }
}
