package com.example.newsfeed.data.remote.model;

import java.util.List;

public class NewsResponseDto {
    private String status;
    private Integer totalResults;
    private List<ArticleDto> articles;

    public String getStatus() {
        return status;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public List<ArticleDto> getArticles() {
        return articles;
    }
}

