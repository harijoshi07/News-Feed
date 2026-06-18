package com.example.newsfeed.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import java.util.List;

@Dao
public interface ArticlesDao {

    @Query("SELECT * FROM articles ORDER BY published_at DESC")
    LiveData<List<ArticleEntity>> observeArticles();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticles(List<ArticleEntity> articles);

    @Query("DELETE FROM articles")
    void clearArticles();

    @Transaction
    default void replaceArticles(List<ArticleEntity> articles) {
        clearArticles();
        insertArticles(articles);
    }
}
