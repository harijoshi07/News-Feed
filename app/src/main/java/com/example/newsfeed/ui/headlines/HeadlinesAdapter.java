package com.example.newsfeed.ui.headlines;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsfeed.R;
import com.example.newsfeed.data.local.ArticleEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HeadlinesAdapter extends RecyclerView.Adapter<HeadlinesAdapter.HeadlineViewHolder> {

    private final List<ArticleEntity> articles = new ArrayList<>();

    @NonNull
    @Override
    public HeadlineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_headline, parent, false);
        return new HeadlineViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HeadlineViewHolder holder, int position) {
        holder.bind(articles.get(position));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setArticles(List<ArticleEntity> newArticles) {
        articles.clear();
        articles.addAll(newArticles == null ? Collections.emptyList() : newArticles);
        notifyDataSetChanged();
    }

    static class HeadlineViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleText;
        private final TextView descriptionText;
        private final ImageView headlineImage;
        private final ImageView sourceIcon;
        private final TextView sourceNameText;
        private final TextView publicationTimeText;

        HeadlineViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.headlineTitleText);
            descriptionText = itemView.findViewById(R.id.headlineDescriptionText);
            headlineImage = itemView.findViewById(R.id.headlineImage);
            sourceIcon = itemView.findViewById(R.id.sourceIcon);
            sourceNameText = itemView.findViewById(R.id.sourceNameText);
            publicationTimeText = itemView.findViewById(R.id.publicationTimeText);
        }

        void bind(ArticleEntity article) {
            titleText.setText(article.getTitle());
            descriptionText.setText(article.getDescription());
            sourceNameText.setText(article.getSourceName());

            Glide.with(headlineImage)
                    .load(article.getImageUrl())
                    .placeholder(R.drawable.article_image_placeholder)
                    .error(R.drawable.article_image_placeholder)
                    .centerCrop()
                    .into(headlineImage);

            // Source icon and publication-time formatting are added in later steps.
            sourceIcon.setImageDrawable(null);
            publicationTimeText.setText("");
        }
    }
}
