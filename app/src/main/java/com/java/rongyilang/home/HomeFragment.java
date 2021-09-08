package com.java.rongyilang.home;

import android.os.Bundle;

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

import com.google.android.exoplayer2.util.Log;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.tabs.TabLayout;
import com.java.rongyilang.R;
import com.java.rongyilang.home.newslist.HomeItemFragment;
import com.java.rongyilang.utils.NewsType;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private TabLayout tabLayout;
    private ChipGroup chipGroup;

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
        viewPager.setOffscreenPageLimit(10);
        viewPager.setAdapter(new NewsListPagerAdapter(getChildFragmentManager()));

        tabLayout.setupWithViewPager(viewPager);
        return viewRoot;
    }

    public void setTabLayout() {
        int cnt = 0;
        for (int i = 0; i < NewsType.TYPE_COUNT; i++)
            if (NewsType.status[i]) {
                tabLayout.addTab(tabLayout.newTab());
                tabLayout.getTabAt(cnt).setText(NewsType.TYPES[i]);
                NewsType.typeDisplay[cnt] = NewsType.TYPES[i];
                cnt++;
            }

    }

}

class NewsListPagerAdapter extends FragmentStatePagerAdapter {
    public NewsListPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return HomeItemFragment.newInstance(NewsType.typeDisplay[i]);
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