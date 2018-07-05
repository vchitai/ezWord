package com.ezword.ezword.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.ezword.ezword.R;
import com.ezword.ezword.ui.adapters.WordListAdapter;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        setSupportActionBar((Toolbar)findViewById(R.id.history_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.history);
        RecyclerView recyclerView = findViewById(R.id.history_history_rv);

        recyclerView.setAdapter(new WordListAdapter(HistoryActivity.this,WordListAdapter.TYPE_HISTORY,R.layout.word_item_2));
        recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(HistoryActivity.this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getDrawable(R.drawable.line));
        recyclerView.addItemDecoration(dividerItemDecoration);

    }
}
