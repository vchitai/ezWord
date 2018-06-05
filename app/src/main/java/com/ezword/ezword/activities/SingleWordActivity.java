package com.ezword.ezword.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ezword.ezword.R;
import com.ezword.ezword.dictionary.Dictionary;
import com.ezword.ezword.dictionary.Word;

public class SingleWordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_word);
        Intent intent = getIntent();
        String searchPhrase = intent.getStringExtra("searchPhrase");
        Word word = Dictionary.getInstance().search(SingleWordActivity.this, searchPhrase);

        TextView wordEng = findViewById(R.id.word_item_word);
        wordEng.setText(word.getData(Word.WORD_ENGLISH));
        TextView wordType = findViewById(R.id.word_item_type);
        wordType.setText(word.getData(Word.WORD_TYPE));
        TextView wordDef = findViewById(R.id.word_item_def);
        wordDef.setText(word.getData(Word.WORD_DEFINITION));
        TextView wordPron = findViewById(R.id.word_item_phonetic);
        wordPron.setText(word.getData(Word.WORD_PHONETIC));
    }
}
