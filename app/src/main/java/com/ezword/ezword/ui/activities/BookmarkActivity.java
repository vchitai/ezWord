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

public class BookmarkActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        setSupportActionBar((Toolbar)findViewById(R.id.bookmark_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.bookmark);
        RecyclerView recyclerView = findViewById(R.id.bookmark_bookmark_rv);

        recyclerView.setAdapter(new WordListAdapter(BookmarkActivity.this,WordListAdapter.TYPE_BOOKMARK,R.layout.word_item_2));
        recyclerView.setLayoutManager(new LinearLayoutManager(BookmarkActivity.this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(BookmarkActivity.this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getDrawable(R.drawable.line));
        recyclerView.addItemDecoration(dividerItemDecoration);

    }
}
