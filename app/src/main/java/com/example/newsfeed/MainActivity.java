package com.example.newsfeed;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsfeed.ui.headlines.HeadlinesAdapter;
import com.example.newsfeed.ui.headlines.HeadlinesViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        RecyclerView headlinesRecyclerView = findViewById(R.id.headlinesRecyclerView);
        ViewCompat.setOnApplyWindowInsetsListener(headlinesRecyclerView, (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        HeadlinesAdapter headlinesAdapter = new HeadlinesAdapter();
        headlinesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        headlinesRecyclerView.setAdapter(headlinesAdapter);

        HeadlinesViewModel headlinesViewModel = new ViewModelProvider(this)
                .get(HeadlinesViewModel.class);
        headlinesViewModel.getArticles().observe(this, headlinesAdapter::setArticles);
    }
}
