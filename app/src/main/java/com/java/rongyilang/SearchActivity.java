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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

    private String mKeyword = "";
    private String mStartDate = "";
    private String mEndDate = "";
    private String mType = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        setContentView(R.layout.fragment_search);
        mKeyword = getIntent().getStringExtra("QueryKeyword");

        SearchItemFragment searchItemFragment = SearchItemFragment.newInstance(mKeyword, mStartDate, mEndDate, mType);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.search_list_container, searchItemFragment).commit();

        searchItemFragment.update(mKeyword, mStartDate, mEndDate, mType);

        SearchView mSearchView = findViewById(R.id.search_searchBar);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mKeyword = query;
                searchItemFragment.update(mKeyword, mStartDate, mEndDate, mType);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        Spinner spinner = findViewById(R.id.search_category_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.search_categories, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mType = (String) adapterView.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ImageView buttonClose = findViewById(R.id.search_close_button);
        buttonClose.setOnClickListener((View.OnClickListener) view -> {
            finish();
        });

        ImageView buttonFilter = findViewById(R.id.search_filter_button);
        DrawerLayout drawerLayout = findViewById(R.id.search_drawer_layout);
        buttonFilter.setOnClickListener(view -> drawerLayout.openDrawer(Gravity.RIGHT));
    }

}
