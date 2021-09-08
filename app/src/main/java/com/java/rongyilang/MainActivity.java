package com.java.rongyilang;

import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.java.rongyilang.databinding.ActivityMainBinding;
import com.java.rongyilang.favourite.FavouriteFragment;
import com.java.rongyilang.favourite.FavouriteItemFragment;
import com.java.rongyilang.history.HistoryFragment;
import com.java.rongyilang.history.HistoryItemFragment;
import com.java.rongyilang.home.HomeFragment;
import com.java.rongyilang.utils.HistoryNewsCall;
import com.java.rongyilang.utils.NewsType;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ChipGroup chipGroup;
    private ChipGroup chipGroup2;
    private HomeFragment homeFragment;
    private HistoryFragment historyFragment;
    private FavouriteFragment favouriteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        historyFragment = new HistoryFragment();
        favouriteFragment = new FavouriteFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, historyFragment)
                .add(R.id.content, favouriteFragment)
                .add(R.id.content, homeFragment)
                .hide(historyFragment)
                .hide(favouriteFragment)
                .commit();

        BottomNavigationView navigation = findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

        chipGroup = findViewById(R.id.nav_chip_group);
        chipGroup2 = findViewById(R.id.nav_chip_group2);
        createChipGroup();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

                NewsType.typeDisplayCount = 0;

                for (int i = 0; i < NewsType.TYPE_COUNT; i++)
                    if (NewsType.status[i]) {
                        NewsType.typeDisplay[NewsType.typeDisplayCount++] = NewsType.TYPES[i];
                    }

                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(homeFragment)
                        .commit();

                homeFragment = new HomeFragment();

                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.content, homeFragment)
                        .commit();
                changeFragmentHome();;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    protected void onResume() {
        historyFragment.update();
        favouriteFragment.update();
        super.onResume();
    }


    private void createChipGroup() {

        for (int i = 0; i < NewsType.TYPE_COUNT; i++) {

            Chip chip = (Chip) getLayoutInflater().inflate(R.layout.layout_chip, null, false);
            chip.setText(NewsType.TYPES[i]);
            chip.setCheckable(false);

            int nowI = i;
            if (i != 0) chip.setOnClickListener(v -> {
                if (NewsType.status[nowI]) {
                    chipGroup.removeView(chip);
                    chipGroup2.addView(chip);
                    NewsType.status[nowI] = false;
                } else {
                    chipGroup2.removeView(chip);
                    chipGroup.addView(chip);
                    NewsType.status[nowI] = true;
                }
            });
            chipGroup.addView(chip);
        }

    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void changeFragmentHome() {
        getSupportFragmentManager()
                .beginTransaction()
                .hide(historyFragment)
                .hide(favouriteFragment)
                .show(homeFragment)
                .commit();
    }

    private void changeFragmentHistory() {
        getSupportFragmentManager()
                .beginTransaction()
                .hide(homeFragment)
                .hide(favouriteFragment)
                .show(historyFragment)
                .commit();
    }

    private void changeFragmentFavourite() {
        getSupportFragmentManager()
                .beginTransaction()
                .hide(homeFragment)
                .hide(historyFragment)
                .show(favouriteFragment)
                .commit();
    }

    private boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_home:
                changeFragmentHome();
                break;
            case R.id.navigation_history:
                changeFragmentHistory();
                historyFragment.update();
                break;
            case R.id.navigation_favourite:
                changeFragmentFavourite();
                favouriteFragment.update();
                break;
        }
        return true;
    }
}