package com.example.newsfeed.data.local;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "articles")
public class ArticleEntity {
    @PrimaryKey
    @NonNull
    private String url;

    @ColumnInfo(name = "source_name")
    private String sourceName;
    private String title;
    private String description;
    @ColumnInfo(name = "image_url")
    private String imageUrl;
    @ColumnInfo(name = "published_at")
    private String publishedAt;

    public ArticleEntity(
            @NonNull String url,
            String sourceName,
            String title,
            String description,
            String imageUrl,
            String publishedAt
    ) {
        this.url = url;
        this.sourceName = sourceName;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.publishedAt = publishedAt;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPublishedAt() {
        return publishedAt;
    }
}
