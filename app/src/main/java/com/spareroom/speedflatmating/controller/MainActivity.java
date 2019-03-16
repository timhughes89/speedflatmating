package com.spareroom.speedflatmating.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.spareroom.speedflatmating.R;
import com.spareroom.speedflatmating.controller.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    private static final String FRAGMENT_TAG = "MAIN";

    private static final String TAG = MainActivity.class.getSimpleName();
    private FragmentManager mFragmentManager;

    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();

        if (mFragmentManager != null) {
            if (savedInstanceState != null) {
                // Find fragment
                mFragmentManager.findFragmentByTag(FRAGMENT_TAG);
            } else {
                // Create Fragment
                mFragment = new MainFragment();
                mFragmentManager.beginTransaction()
                        .add(R.id.container, mFragment)
                        .commit();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (getSupportFragmentManager() != null) {
            getSupportFragmentManager().putFragment(outState, FRAGMENT_TAG, mFragment);
        }

    }
}
