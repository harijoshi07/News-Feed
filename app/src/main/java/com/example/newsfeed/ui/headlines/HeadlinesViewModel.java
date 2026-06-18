package com.example.newsfeed.ui.headlines;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.newsfeed.data.local.ArticleEntity;
import com.example.newsfeed.data.repository.NewsRepository;

import java.util.List;

public class HeadlinesViewModel extends AndroidViewModel {

    private final NewsRepository repository;
    private final LiveData<List<ArticleEntity>> articles;

    public HeadlinesViewModel(@NonNull Application application) {
        super(application);

        repository = new NewsRepository(application);
        articles = repository.observeArticles();
        refreshHeadlines();
    }

    public LiveData<List<ArticleEntity>> getArticles() {
        return articles;
    }

    public void refreshHeadlines() {
        repository.refreshTopHeadlines(
                new NewsRepository.NewsCallback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(String message) {

                    }
                }
        );
    }
}
