package com.example.newsfeed.data.remote.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsApiClient {
    private static final String BASE_URL = "https://newsapi.org/";
    private static NewsApiService service;
    private NewsApiClient() {}

    public static NewsApiService getService() {
        if (service == null) {
            service = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(NewsApiService.class);
        }
        return service;
    }
}
