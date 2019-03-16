package com.spareroom.speedflatmating.controller.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.spareroom.speedflatmating.R;
import com.spareroom.speedflatmating.model.Event;
import com.spareroom.speedflatmating.network.EventApiService;
import com.spareroom.speedflatmating.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventListFragment extends Fragment {

    private static final String TAG = EventListFragment.class.getSimpleName();

    private List<Event> eventList = new ArrayList<>();
    private View mView;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;

    public EventListFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.frag_list, container, false);
        initUI();
        fetchData();
        return mView;
    }

    private void initUI() {
        mProgressBar = mView.findViewById(R.id.progress_bar);
        mRecyclerView = mView.findViewById(R.id.recycler_view_event_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void fetchData() {

        EventApiService service = RetrofitClientInstance.getRetrofitInstance().create(EventApiService.class);
        Call<List<Event>> call = service.getEvents();
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.body() != null) {
                    eventList = response.body();
                    mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.d(TAG, "There was an retrieving data");
            }
        });
    }
}
