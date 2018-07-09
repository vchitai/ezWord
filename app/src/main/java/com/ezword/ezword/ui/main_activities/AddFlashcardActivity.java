package com.ezword.ezword.ui.main_activities;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezword.ezword.R;
import com.ezword.ezword.background.database.LocalData;
import com.ezword.ezword.background.dictionary.Dictionary;
import com.ezword.ezword.background.dictionary.FlashCard;
import com.ezword.ezword.background.services.CustomSearchEngine;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddFlashcardActivity extends AppCompatActivity {
    private String mWord;
    private int mWordId;
    private ArrayList<String> mSearchResult;
    private int mCurrentMemo;
    private String mNote;
    private int mode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flashcard);
        setTitle("Add New Flashcard");
        mWord = getIntent().getStringExtra("word");
        mWordId = getIntent().getIntExtra("word_id", -1);
        startSearch();
        switchAddMemoView();
    }

    private void startSearch() {
        CustomSearchEngine customSearchEngine = new CustomSearchEngine();
        final ArrayList<String> searchResults = new ArrayList<>();
        customSearchEngine.startSearch(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resonseString = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(resonseString);
                    JSONArray jsonArray = jsonObject.getJSONArray("items");
                    searchResults.clear();
                    for (int i = 0; i<jsonArray.length(); i++) {
                        searchResults.add(jsonArray.getJSONObject(i).getString("link"));
                        Log.v("s", searchResults.get(i));
                    }
                    mSearchResult = searchResults;
                    Handler uiHandler = new Handler(Looper.getMainLooper());
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            updateImage(searchResults);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, mWord);
    }

    private void switchAddMemoView() {
        mode = 1;
        setContentView(R.layout.fragment_add_memo);
        if (mCurrentMemo == -1) {
            ((TextView)findViewById(R.id.add_fc_memo_label)).setText("Select Memo:");
        } else {
            ((TextView)findViewById(R.id.add_fc_memo_label)).setText("Select Memo: (Current chose: " + String.valueOf(mCurrentMemo+1) + ")");
        }
        updateImage(mSearchResult);
    }

    private void updateImage(final ArrayList<String> searchResults) {
        if (mode != 1 || mSearchResult == null)
            return;

        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Picasso.get().load(searchResults.get(position)).into(imageView);
            }
        };
        mSearchResult = searchResults;
        CarouselView carouselView = findViewById(R.id.add_fc_memo);
        carouselView.setImageListener(imageListener);
        carouselView.setPageCount(searchResults.size());
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                if (mCurrentMemo == position) {
                    mCurrentMemo = -1;
                    ((TextView)findViewById(R.id.add_fc_memo_label)).setText("Select Memo: ");
                } else {
                    mCurrentMemo = position;
                    ((TextView)findViewById(R.id.add_fc_memo_label)).setText("Select Memo: (Current chose: " + String.valueOf(position+1) + ")");
                }
            }
        });

        findViewById(R.id.add_fc_memo_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchTakeNoteView();
            }
        });
    }

    private void switchTakeNoteView() {
        mode = 2;
        setContentView(R.layout.fragment_take_note);
        ((TextView)findViewById(R.id.add_fc_note)).setText(mNote);
        findViewById(R.id.add_fc_note_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFlashCard();
                finish();
            }
        });
        findViewById(R.id.add_fc_note_prev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNote = ((TextView)findViewById(R.id.add_fc_note)).getText().toString();
                switchAddMemoView();
            }
        });
    }

    private void saveFlashCard() {
        String note = ((TextView)findViewById(R.id.add_fc_note)).getText().toString();
        String selectedMemo = "";
        if (mCurrentMemo != -1)
            selectedMemo = mSearchResult.get(mCurrentMemo);
        Dictionary.getInstance().addFlashCardToDatabase(getApplicationContext(), mWordId, selectedMemo, note);
        LocalData.getInstance(AddFlashcardActivity.this).addBookmark(AddFlashcardActivity.this, Dictionary.getInstance().getWordById(AddFlashcardActivity.this, mWordId));
    }
}
