package com.ezword.ezword.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ezword.ezword.R;
import com.ezword.ezword.adapters.WordListAdapter;

public class BookmarkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        setTitle(R.string.bookmark);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.bookmark_recycler_view);

        recyclerView.setAdapter(new WordListAdapter(BookmarkActivity.this,WordListAdapter.TYPE_BOOKMARK));
        recyclerView.setLayoutManager(new LinearLayoutManager(BookmarkActivity.this));
        recyclerView.addItemDecoration(new DividerItemDecoration(BookmarkActivity.this, DividerItemDecoration.VERTICAL));
    }
}
