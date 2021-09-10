package com.java.rongyilang;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    private String mKeyword = "";
    private String mStartDate = "";
    private String mEndDate = "";
    private String mType = "";

    private Calendar startCalendar;
    private Calendar endCalendar;

    private EditText startEditText;
    private EditText endEditText;

    private DrawerLayout drawerLayout;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        setContentView(R.layout.fragment_search);

        //mKeyword = getIntent().getStringExtra("QueryKeyword");
        drawerLayout = findViewById(R.id.search_drawer_layout);

        SearchItemFragment searchItemFragment = SearchItemFragment.newInstance(mKeyword, mStartDate, mEndDate, mType);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.search_list_container, searchItemFragment).commit();

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

        EditText editText = findViewById(R.id.search_drawer_keyword_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.search_auto_complete);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.search_categories, R.layout.search_item);
        autoCompleteTextView.setSelection(0);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mType = adapterView.getItemAtPosition(i).toString();
                Log.d("", "Change to:" + mKeyword);
            }
        });


        startCalendar = Calendar.getInstance();
        startEditText= (EditText) findViewById(R.id.search_drawer_start_date_edit_text);

        DatePickerDialog.OnDateSetListener startDate = (view, year, monthOfYear, dayOfMonth) -> {
            startCalendar.set(Calendar.YEAR, year);
            startCalendar.set(Calendar.MONTH, monthOfYear);
            startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateStartLabel();
        };

        startEditText.setOnClickListener(v -> {
            new DatePickerDialog(SearchActivity.this, startDate,
                    startCalendar.get(Calendar.YEAR),
                    startCalendar.get(Calendar.MONTH),
                    startCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        endCalendar = Calendar.getInstance();
        endEditText = (EditText) findViewById(R.id.search_drawer_end_date_edit_text) ;

        DatePickerDialog.OnDateSetListener endDate = (view, year, monthOfYear, dayOfMonth) -> {
            endCalendar.set(Calendar.YEAR, year);
            endCalendar.set(Calendar.MONTH, monthOfYear);
            endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateEndLabel();
        };

        endEditText.setOnClickListener(v -> {
            new DatePickerDialog(SearchActivity.this, endDate,
                    endCalendar.get(Calendar.YEAR),
                    endCalendar.get(Calendar.MONTH),
                    endCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });


        ImageView buttonClose = findViewById(R.id.search_close_button);
        buttonClose.setOnClickListener((View.OnClickListener) view -> {
            finish();
        });

        ImageView buttonFilter = findViewById(R.id.search_filter_button);

        buttonFilter.setOnClickListener(view -> drawerLayout.openDrawer(Gravity.RIGHT));

        ImageView buttonSearch = findViewById(R.id.search_drawer_button);
        buttonSearch.setOnClickListener(view -> {
            mKeyword = editText.getText().toString();
            searchItemFragment.update(mKeyword, mStartDate, mEndDate, mType);
            drawerLayout.closeDrawers();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateStartLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.CHINA);
        mStartDate = simpleDateFormat.format(startCalendar.getTime());
        startEditText.setText(simpleDateFormat.format(startCalendar.getTime()));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateEndLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.CHINA);
        mEndDate = simpleDateFormat.format(endCalendar.getTime());
        endEditText.setText(simpleDateFormat.format(endCalendar.getTime()));
    }

}
