package com.spareroom.speedflatmating;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.spareroom.speedflatmating.model.Event;
import com.spareroom.speedflatmating.network.RetrofitClientInstance;
import com.spareroom.speedflatmating.network.EventApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private List<Event> mEvents = new ArrayList<>();
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        fetchData();
    }

    private void initUI() {
        mTextView = findViewById(R.id.textview_example);
    }

    private void fetchData() {

        EventApiService service = RetrofitClientInstance.getRetrofitInstance().create(EventApiService.class);
        Call<List<Event>> call = service.getEvents();
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.body() != null) {
                    mEvents = response.body();
                    mTextView.setText(mEvents.get(0).toString());
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.d(TAG, "There was an retrieving data");
            }
        });
    }
}
