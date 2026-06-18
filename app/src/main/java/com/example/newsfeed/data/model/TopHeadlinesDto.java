package com.example.newsfeed.data.model;

import java.util.List;

public class TopHeadlinesDto {
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

