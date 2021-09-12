package com.java.rongyilang.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.tabs.TabLayout;
import com.java.rongyilang.DetailActivity;
import com.java.rongyilang.MainActivity;
import com.java.rongyilang.R;
import com.java.rongyilang.SearchActivity;
import com.java.rongyilang.home.newslist.HomeItemFragment;
import com.java.rongyilang.utils.NewsType;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private TabLayout tabLayout;
    private ChipGroup chipGroup;
    public NewsListPagerAdapter newsListPagerAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View viewRoot = inflater.inflate(R.layout.fragment_home, container, false);

        LinearLayout searchView = viewRoot.findViewById(R.id.search_view);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                getContext().startActivity(intent);
            }
        });

        ImageView button = viewRoot.findViewById(R.id.nav_button);
        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });

        chipGroup = getActivity().findViewById(R.id.nav_chip_group);

        tabLayout = viewRoot.findViewById(R.id.tab_layout);
        setTabLayout();

        ViewPager viewPager = viewRoot.findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(5);

        newsListPagerAdapter = new NewsListPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(newsListPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        return viewRoot;
    }

    public void setTabLayout() {
//        int cnt = 0;
//        for (int i = 0; i < NewsType.TYPE_COUNT; i++)
//            if (NewsType.status[i]) {
//                tabLayout.addTab(tabLayout.newTab());
//                tabLayout.getTabAt(cnt).setText(NewsType.TYPES[i]);
//                NewsType.typeDisplay[cnt] = NewsType.TYPES[i];
//                cnt++;
//            }

        for (int i = 0; i < NewsType.typeDisplayCount; i++) {
            tabLayout.addTab(tabLayout.newTab());
            tabLayout.getTabAt(i).setText(NewsType.typeDisplay[i]);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void update() {

        for (int i = 0; i < NewsType.typeDisplayCount; i++)
            if (newsListPagerAdapter.homeItemFragment[i] != null)
                newsListPagerAdapter.homeItemFragment[i].update();
    }

}

class NewsListPagerAdapter extends FragmentStatePagerAdapter {
    public HomeItemFragment[] homeItemFragment = new HomeItemFragment[1000];
    public NewsListPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        HomeItemFragment nowHomeItemFragment = HomeItemFragment.newInstance(NewsType.typeDisplay[i]);
        homeItemFragment[i] = nowHomeItemFragment;

        return nowHomeItemFragment;
    }

    @Override
    public int getCount() {
        return NewsType.typeDisplayCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return NewsType.typeDisplay[position];
    }

}