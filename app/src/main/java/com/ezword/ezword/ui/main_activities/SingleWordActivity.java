package com.ezword.ezword.ui.main_activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ezword.ezword.R;
import com.ezword.ezword.background.database.LocalData;
import com.ezword.ezword.background.dictionary.Dictionary;
import com.ezword.ezword.background.dictionary.Word;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class SingleWordActivity extends AppCompatActivity {
    private Word currentWord;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    public static final String SEARCH_PHRASE = "searchPhrase";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_word_2);
        Intent intent = getIntent();
        String searchPhrase = intent.getStringExtra(SEARCH_PHRASE);
        currentWord = Dictionary.getInstance().search(SingleWordActivity.this, searchPhrase);
/*
        findViewById(R.id.single_word_back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(getParentActivityIntent());
            }
        });*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.single_word_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        TextView wordEng = findViewById(R.id.word_item_word);
        wordEng.setText(currentWord.getData(Word.WORD_ENGLISH));
        //TextView wordType = findViewById(R.id.word_item_type);
        //wordType.setText(currentWord.getData(Word.WORD_TYPE));
        TextView wordDef = findViewById(R.id.word_item_def);
        wordDef.setText(Html.fromHtml(currentWord.getData(Word.WORD_DEFINITION_HTML)));
        TextView wordPron = findViewById(R.id.word_item_phonetic);
        wordPron.setText(currentWord.getData(Word.WORD_PHONETIC_HTML));
    }

    private void addBookmark() {
        boolean res = LocalData.getInstance(SingleWordActivity.this).addBookmark(SingleWordActivity.this, currentWord);
        Toast.makeText(SingleWordActivity.this, res ? "Added":"Already Added", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_menu_add:
                Intent intent = new Intent(SingleWordActivity.this, AddFlashcardActivity.class);
                intent.putExtra("word", currentWord.getData(Word.WORD_ENGLISH));
                startActivity(intent);
                //addBookmark();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
