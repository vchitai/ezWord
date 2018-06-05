package com.ezword.ezword.activities;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.ezword.ezword.R;
import com.ezword.ezword.dictionary.Dictionary;
import com.ezword.ezword.dictionary.Word;

public class RequestSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_request_search);
        CharSequence text = getIntent()
                .getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        Word word = Dictionary.getInstance().search(RequestSearchActivity.this, text.toString());
        if (word != null) {
            TextView wordEng = findViewById(R.id.word_item_word);
            wordEng.setText(word.getData(Word.WORD_ENGLISH));
            TextView wordType = findViewById(R.id.word_item_type);
            wordType.setText(word.getData(Word.WORD_TYPE));
            TextView wordDef = findViewById(R.id.word_item_def);
            wordDef.setText(word.getData(Word.WORD_DEFINITION));
        } else {
            Toast.makeText(RequestSearchActivity.this, "Word not found!", Toast.LENGTH_SHORT).show();
        }
    }
}
