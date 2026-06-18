package com.example.newsfeed.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.newsfeed.BuildConfig;
import com.example.newsfeed.data.local.ArticleEntity;
import com.example.newsfeed.data.local.ArticlesDao;
import com.example.newsfeed.data.local.NewsDatabase;
import com.example.newsfeed.data.mapper.ArticleMapper;
import com.example.newsfeed.data.remote.model.NewsResponseDto;
import com.example.newsfeed.data.remote.network.NewsApiClient;
import com.example.newsfeed.data.remote.network.NewsApiService;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private final NewsApiService apiService;
    private final ArticlesDao articlesDao;
    private final Executor databaseExecutor;

    public NewsRepository(Context context) {
        apiService = NewsApiClient.getService();
        articlesDao = NewsDatabase.getInstance(context).articlesDao();
        databaseExecutor = Executors.newSingleThreadExecutor();
    }

    // Room is the source observed by higher layers.
    public LiveData<List<ArticleEntity>> observeArticles() {
        return articlesDao.observeArticles();
    }

    public void refreshTopHeadlines(NewsCallback callback) {
        apiService.getTopHeadlines(
                BuildConfig.NEWS_API_KEY,
                "us"
        ).enqueue(new Callback<NewsResponseDto>() {
            @Override
            public void onResponse(Call<NewsResponseDto> call, Response<NewsResponseDto> response) {
                NewsResponseDto body = response.body();

                if (response.isSuccessful() && body != null) {
                    List<ArticleEntity> articles = ArticleMapper.toEntities(body.getArticles());

                    databaseExecutor.execute(() -> {
                        articlesDao.replaceArticles(articles);
                        callback.onSuccess();
                    });
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
        void onSuccess();
        void onError(String message);
    }
}
