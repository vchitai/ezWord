package com.ezword.ezword.ui.main_activities;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.ezword.ezword.R;
import com.ezword.ezword.background.services.CustomSearchEngine;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flashcard);

       // https://www.googleapis.com/customsearch/v1?key=AIzaSyB2X_OdDC_Ti5SghFT6bbckLqbVEciws_k&cx=008138058657872763058:lh1xelbuwm4&searchType=image&num=10&q=flowers
        //final CarouselPicker carouselPicker = (CarouselPicker) findViewById(R.id.add_fc_memo);
        CustomSearchEngine customSearchEngine = new CustomSearchEngine();
        String word = getIntent().getStringExtra("word");
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
        }, word);


    }

    private void updateImage(final ArrayList<String> searchResults) {
        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Picasso.get().load(searchResults.get(position)).into(imageView);
            }
        };
        CarouselView carouselView = (CarouselView) findViewById(R.id.add_fc_memo);
        carouselView.setImageListener(imageListener);
        carouselView.setPageCount(searchResults.size());

    }
}
