package com.ezword.ezword.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ezword.ezword.R;
import com.ezword.ezword.ui.adapters.WordListAdapter;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        setTitle(R.string.history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.history_history_rv);

        recyclerView.setAdapter(new WordListAdapter(HistoryActivity.this,WordListAdapter.TYPE_HISTORY,R.layout.word_item));
        recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
        recyclerView.addItemDecoration(new DividerItemDecoration(HistoryActivity.this, DividerItemDecoration.VERTICAL));
    }
}
