package com.example.newsfeed;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsfeed.data.local.ArticleEntity;
import com.example.newsfeed.ui.headlines.HeadlinesAdapter;
import com.example.newsfeed.ui.headlines.HeadlinesViewModel;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView headlinesRecyclerView;
    private View loadingStateContainer;
    private TextView errorStateText;

    private List<ArticleEntity> currentArticles = Collections.emptyList();
    private boolean loading = true;
    private String errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        headlinesRecyclerView = findViewById(R.id.headlinesRecyclerView);
        loadingStateContainer = findViewById(R.id.loadingStateContainer);
        errorStateText = findViewById(R.id.errorStateText);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        HeadlinesAdapter headlinesAdapter = new HeadlinesAdapter();
        headlinesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        headlinesRecyclerView.setAdapter(headlinesAdapter);

        HeadlinesViewModel headlinesViewModel = new ViewModelProvider(this)
                .get(HeadlinesViewModel.class);
        headlinesViewModel.getArticles().observe(this, articles -> {
            currentArticles = articles == null ? Collections.emptyList() : articles;
            headlinesAdapter.setArticles(currentArticles);
            renderState();
        });
        headlinesViewModel.getLoading().observe(this, isLoading -> {
            loading = Boolean.TRUE.equals(isLoading);
            renderState();
        });
        headlinesViewModel.getErrorMessage().observe(this, message -> {
            errorMessage = message;
            renderState();
        });
    }

    private void renderState() {
        boolean hasArticles = !currentArticles.isEmpty();
        boolean hasError = errorMessage != null && !errorMessage.trim().isEmpty();

        headlinesRecyclerView.setVisibility(hasArticles ? View.VISIBLE : View.GONE);
        loadingStateContainer.setVisibility(
                !hasArticles && loading ? View.VISIBLE : View.GONE
        );
        errorStateText.setVisibility(
                !hasArticles && !loading && hasError ? View.VISIBLE : View.GONE
        );
        errorStateText.setText(errorMessage);
    }
}
