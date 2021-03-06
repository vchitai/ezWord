package com.ezword.ezword.ui.main_activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.ezword.ezword.R;
import com.ezword.ezword.background.database.LocalData;
import com.ezword.ezword.background.dictionary.Dictionary;
import com.ezword.ezword.background.dictionary.Word;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class RequestSearchActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CharSequence text = getIntent()
                .getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);

        final Word word = Dictionary.getInstance().search(RequestSearchActivity.this, text.toString());
        if (word != null) {
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.activity_request_search);
            LocalData.getInstance(RequestSearchActivity.this).addHistory(word);
            View v = findViewById(R.id.request_search_root);
            TextView wordEng = v.findViewById(R.id.word_item_word);
            wordEng.setText(word.getData(Word.WORD_ENGLISH));
            //TextView wordType = v.findViewById(R.id.word_item_type);
            //wordType.setText(word.getData(Word.WORD_TYPE));
            TextView wordDef = v.findViewById(R.id.word_item_def);
            wordDef.setText(Html.fromHtml(word.getData(Word.WORD_DEFINITION_HTML)));
            findViewById(R.id.request_search_go_to_def).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(RequestSearchActivity.this, SingleWordActivity.class);
                    intent.putExtra(SingleWordActivity.SEARCH_PHRASE, word.getData(Word.WORD_ENGLISH));
                    startActivity(intent);
                }
            });
            findViewById(R.id.request_search_bookmark).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(RequestSearchActivity.this, AddFlashcardActivity.class);
                    intent.putExtra("word", word.getData(Word.WORD_ENGLISH));
                    intent.putExtra("word_id", word.getWordID());

                    startActivity(intent);
                    //boolean res = LocalData.getInstance(RequestSearchActivity.this).addBookmark(RequestSearchActivity.this, word);
                    //Toast.makeText(RequestSearchActivity.this, res ? "Added":"Already Added", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(RequestSearchActivity.this, R.string.IF_word_not_found, Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
