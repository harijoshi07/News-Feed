package com.example.newsfeed.data.remote.network;

import com.example.newsfeed.data.remote.model.NewsResponseDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface NewsApiService {
    @GET("v2/top-headlines")
    Call<NewsResponseDto> getTopHeadlines(
      @Header("X-Api-Key") String apiKey,
      @Query("country") String country
    );
}
