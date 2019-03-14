package com.spareroom.speedflatmating.network;

import com.spareroom.speedflatmating.model.Event;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.Call;

public interface EventApiService {

    @GET("/b/5c6a9becf73bfe1ce3ed01bf")
    Call<List<Event>> getEvents();

}
