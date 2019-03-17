package com.spareroom.speedflatmating.controller.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spareroom.speedflatmating.R;
import com.spareroom.speedflatmating.controller.adapters.ViewPagerAdapter;

public class MainFragment extends Fragment {

    private View mView;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    public MainFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.frag_main, container, false);
        initUI();
        return mView;
    }

    private void initUI() {
        initToolbar();
        initViewPagerTabLayout();
    }

    private void initToolbar() {
        Toolbar toolbar = mView.findViewById(R.id.toolbar);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if (appCompatActivity != null) {
            appCompatActivity.setSupportActionBar(toolbar);
        }
    }

    private void initViewPagerTabLayout() {
        mTabLayout = mView.findViewById(R.id.tab_bar_layout);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.upcoming_tab_label));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.archived_tab_label));
        mViewPager = mView.findViewById(R.id.view_pager);

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(new UpcomingEventFragment());
        pagerAdapter.addFragment(new ArchivedEventFragment());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
