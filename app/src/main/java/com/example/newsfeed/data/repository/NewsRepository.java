package com.example.newsfeed.data.repository;

import com.example.newsfeed.BuildConfig;
import com.example.newsfeed.data.remote.model.ArticleDto;
import com.example.newsfeed.data.remote.model.NewsResponseDto;
import com.example.newsfeed.data.remote.network.NewsApiClient;
import com.example.newsfeed.data.remote.network.NewsApiService;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private final NewsApiService apiService;

    public NewsRepository() {
        apiService = NewsApiClient.getService();
    }

    public void fetchTopHeadlines(NewsCallback callback) {
        apiService.getTopHeadlines(
                BuildConfig.NEWS_API_KEY,
                "us"
        ).enqueue(new Callback<NewsResponseDto>() {
            @Override
            public void onResponse(Call<NewsResponseDto> call, Response<NewsResponseDto> response) {
                NewsResponseDto body = response.body();

                if (response.isSuccessful() && body != null) {
                    List<ArticleDto> articles = body.getArticles();
                    callback.onSuccess(articles == null ? Collections.emptyList() : articles);
                } else {
                    callback.onError("Request failed with HTTP " + response.code());
                }
            }

            @Override
            public void onFailure(Call<NewsResponseDto> call, Throwable t) {
                callback.onError("Network request failed.");
            }
        });
    }


    public interface NewsCallback {
        void onSuccess(List<ArticleDto> articles);

        void onError(String message);
    }
}
