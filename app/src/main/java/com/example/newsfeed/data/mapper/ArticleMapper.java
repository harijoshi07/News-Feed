package com.example.newsfeed.data.mapper;

import com.example.newsfeed.data.local.ArticleEntity;
import com.example.newsfeed.data.remote.model.ArticleDto;
import com.example.newsfeed.data.remote.model.SourceDto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArticleMapper {
    private ArticleMapper() {}
    public static List<ArticleEntity> toEntities(List<ArticleDto> articles) {
        if (articles == null) {
            return Collections.emptyList();
        }

        List<ArticleEntity> entities = new ArrayList<>();

        for (ArticleDto article: articles) {
            if (article == null || article.getUrl() == null) {
                continue;
            }

            SourceDto source = article.getSource();
            String sourceName = source == null ? null : source.getName();

            entities.add(new ArticleEntity(
                    article.getUrl(),
                    sourceName,
                    article.getTitle(),
                    article.getDescription(),
                    article.getUrlToImage(),
                    article.getPublishedAt()
            ));
        }
        return entities;
    }
}
