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

public class MainFragment extends Fragment {

    private View mView;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    public MainFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        initViewPager();
        initTabLayout();
    }

    private void initTabLayout() {
        TabLayout tabLayout = mView.findViewById(R.id.tab_bar_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.upcoming_tab_label));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.archived_tab_label));
    }

    private void initViewPager() {
        mViewPager = mView.findViewById(R.id.view_pager);
    }

    private void initToolbar() {
        Toolbar toolbar = mView.findViewById(R.id.toolbar);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if (appCompatActivity != null) {
            appCompatActivity.setSupportActionBar(toolbar);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
