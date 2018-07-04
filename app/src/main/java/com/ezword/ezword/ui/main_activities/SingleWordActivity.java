package com.ezword.ezword.ui.main_activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ezword.ezword.R;
import com.ezword.ezword.background.database.LocalData;
import com.ezword.ezword.background.dictionary.Dictionary;
import com.ezword.ezword.background.dictionary.Word;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class SingleWordActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    public static final String SEARCH_PHRASE = "searchPhrase";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_word);
        Intent intent = getIntent();
        String searchPhrase = intent.getStringExtra(SEARCH_PHRASE);
        final Word word = Dictionary.getInstance().search(SingleWordActivity.this, searchPhrase);

        findViewById(R.id.single_word_back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(getParentActivityIntent());
            }
        });
        TextView wordEng = findViewById(R.id.word_item_word);
        wordEng.setText(word.getData(Word.WORD_ENGLISH));
        //TextView wordType = findViewById(R.id.word_item_type);
        //wordType.setText(word.getData(Word.WORD_TYPE));
        TextView wordDef = findViewById(R.id.word_item_def);
        wordDef.setText(word.getData(Word.WORD_DEFINITION));
        TextView wordPron = findViewById(R.id.word_item_phonetic);
        wordPron.setText(word.getData(Word.WORD_PHONETIC));

        findViewById(R.id.single_word_bookmark_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean res = LocalData.getInstance(SingleWordActivity.this).addBookmark(SingleWordActivity.this, word);
                Toast.makeText(SingleWordActivity.this, res ? "Added":"Already Added", Toast.LENGTH_LONG).show();
            }
        });
    }
}
