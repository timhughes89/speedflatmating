package com.spareroom.speedflatmating.controller.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.spareroom.speedflatmating.R;
import com.spareroom.speedflatmating.controller.adapters.UpcomingEventAdapter;
import com.spareroom.speedflatmating.model.Event;
import com.spareroom.speedflatmating.model.EventHeader;
import com.spareroom.speedflatmating.model.EventSection;
import com.spareroom.speedflatmating.network.EventApiService;
import com.spareroom.speedflatmating.network.RetrofitClientInstance;
import com.spareroom.speedflatmating.ui.ItemOffsetDecoration;
import com.spareroom.speedflatmating.ui.OnItemClickListener;
import com.spareroom.speedflatmating.utils.EventUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingEventFragment extends Fragment implements OnItemClickListener {

    private static final String TAG = UpcomingEventFragment.class.getSimpleName();

    private EventUtils eventUtils = new EventUtils();
    private View mView;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private MenuItem ascendingMenuItem;
    private MenuItem descendingMenuItem;

    private List<Event> eventsList = new ArrayList<>();

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
//                    displayList(response.body());
                    eventsList = response.body();
                    sortByAscending(eventsList);
                    displayList(eventsList);
                    mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.d(TAG, "There was an retrieving data");
            }
        });
    }

    private void displayList(List<Event> events){
        final List<EventSection> eventSections = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            List<Event> eventsInMonth = getEventsInMonth(events, i);

            if(!eventsInMonth.isEmpty()){
                eventSections.add(new EventHeader(i));
                eventSections.addAll(eventsInMonth);
            }
        }
        updateAdapter(eventSections);
    }

    private List<Event> getEventsInMonth(List<Event> eventList, int monthFilter) {
        final List<Event> eventsInMonth = new ArrayList<>();
        for (Event event : eventList) {
            Calendar date = Calendar.getInstance();
            date.setTime(eventUtils.getDateFromStartDate(event.getStartTime()));
            int month = date.get(Calendar.MONTH);
            if (month == monthFilter) {
                eventsInMonth.add(event);
            }
        }
        return eventsInMonth;
    }

    private void updateAdapter(List<EventSection> eventList) {
        UpcomingEventAdapter upcomingEventAdapter = new UpcomingEventAdapter(getContext(), this);
        upcomingEventAdapter.addEvents(eventList);
        mRecyclerView.setAdapter(upcomingEventAdapter);
        upcomingEventAdapter.notifyDataSetChanged();
        upcomingEventAdapter.setOnItemClickListener(this);
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
    }

    private void sortByDescending(List<Event> eventList) {
        Collections.sort(eventList, new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                return o2.getStartTime().compareTo(o1.getStartTime());
            }
        });
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
                sortByAscending(eventsList);
                displayList(eventsList);
                ascendingMenuItem.setChecked(true);
                descendingMenuItem.setChecked(false);
                break;
            case R.id.action_order_descending:
                sortByDescending(eventsList);
                displayList(eventsList);
                ascendingMenuItem.setChecked(false);
                descendingMenuItem.setChecked(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position, Event event) {
        Uri phoneNumber = Uri.parse("tel:" + event.getPhoneNumber());
        Intent callIntent = new Intent(Intent.ACTION_DIAL, phoneNumber);
        startActivity(callIntent);
    }
}
