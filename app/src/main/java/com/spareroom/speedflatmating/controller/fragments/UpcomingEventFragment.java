package com.spareroom.speedflatmating.controller.fragments;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;

import com.spareroom.speedflatmating.EventUtils;
import com.spareroom.speedflatmating.R;
import com.spareroom.speedflatmating.controller.adapters.UpcomingEventAdapter;
import com.spareroom.speedflatmating.model.Event;
import com.spareroom.speedflatmating.network.EventApiService;
import com.spareroom.speedflatmating.network.RetrofitClientInstance;
import com.spareroom.speedflatmating.ui.ItemOffsetDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingEventFragment extends Fragment {

    private static final String TAG = UpcomingEventFragment.class.getSimpleName();

    private List<Event> eventList = new ArrayList<>();
    private View mView;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private MenuItem ascendingMenuItem;
    private MenuItem descendingMenuItem;
    private EventUtils mEventUtils = new EventUtils();

    public UpcomingEventFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.frag_upcoming_event, container, false);
        initUI();
        fetchDataFromNetwork();
        return mView;
    }

    private void initUI() {
        int spacing = getResources().getDimensionPixelSize(R.dimen.unitX1);
        mProgressBar = mView.findViewById(R.id.progress_bar);
        mRecyclerView = mView.findViewById(R.id.recycler_view_event_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new ItemOffsetDecoration(spacing));
    }

    private void fetchDataFromNetwork() {
        EventApiService service = RetrofitClientInstance.getRetrofitInstance().create(EventApiService.class);
        Call<List<Event>> call = service.getEvents();
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(@NonNull Call<List<Event>> call, @NonNull Response<List<Event>> response) {
                if (response.body() != null) {
                    eventList = response.body();
                    sortByAscending(eventList);
                    mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.d(TAG, "There was an retrieving data");
            }
        });
    }

    private void updateAdapter(List<Event> eventList) {
        UpcomingEventAdapter upcomingEventAdapter = new UpcomingEventAdapter(getContext());
        upcomingEventAdapter.addEvents(eventList);
        mRecyclerView.setAdapter(upcomingEventAdapter);
        upcomingEventAdapter.notifyDataSetChanged();
        runLayoutAnimation();
    }

    private void runLayoutAnimation() {
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_fall_down);
        if (mRecyclerView.getAdapter() != null) {
            mRecyclerView.setLayoutAnimation(layoutAnimationController);
            mRecyclerView.scheduleLayoutAnimation();
        }
    }

    private void sortByAscending(List<Event> eventList) {
        Collections.sort(eventList, new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                return o1.getStartTime().compareTo(o2.getStartTime());
            }
        });
        updateAdapter(eventList);
    }

    private void sortByDescending(List<Event> eventList) {
        Collections.sort(eventList, new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                return o2.getStartTime().compareTo(o1.getStartTime());
            }
        });
        updateAdapter(eventList);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        ascendingMenuItem = menu.findItem(R.id.action_order_ascending);
        descendingMenuItem = menu.findItem(R.id.action_order_descending);
        ascendingMenuItem.setChecked(true);
        super.onCreateOptionsMenu(menu, inflater);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.action_order_ascending:
                sortByAscending(eventList);
                ascendingMenuItem.setChecked(true);
                descendingMenuItem.setChecked(false);
                break;
            case R.id.action_order_descending:
                sortByDescending(eventList);
                ascendingMenuItem.setChecked(false);
                descendingMenuItem.setChecked(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
