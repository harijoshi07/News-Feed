package com.example.newsfeed.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ArticleEntity.class}, version = 1, exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "news.db";
    private static volatile NewsDatabase instance;

    public abstract ArticlesDao articlesDao();

    public static NewsDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (NewsDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            NewsDatabase.class,
                            DATABASE_NAME
                    ).build();
                }
            }
        }
        return instance;
    }
}
