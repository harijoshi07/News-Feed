package com.example.newsfeed.ui.headlines;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newsfeed.data.local.ArticleEntity;
import com.example.newsfeed.data.repository.NewsRepository;

import java.util.List;

public class HeadlinesViewModel extends AndroidViewModel {

    private final NewsRepository repository;
    private final LiveData<List<ArticleEntity>> articles;
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public HeadlinesViewModel(@NonNull Application application) {
        super(application);

        repository = new NewsRepository(application);
        articles = repository.observeArticles();
        refreshHeadlines();
    }

    public LiveData<List<ArticleEntity>> getArticles() {
        return articles;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void refreshHeadlines() {
        loading.setValue(true);
        errorMessage.setValue(null);

        repository.refreshTopHeadlines(
                new NewsRepository.NewsCallback() {
                    @Override
                    public void onSuccess() {
                        loading.postValue(false);
                    }

                    @Override
                    public void onError(String message) {
                        loading.postValue(false);
                        errorMessage.postValue(message);
                    }
                }
        );
    }
}
