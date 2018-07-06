package com.ezword.ezword.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.ezword.ezword.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ru.noties.markwon.Markwon;

public class AboutUsActivity extends AppCompatActivity {

    protected String getContent(String filename) {
        BufferedReader reader = null;
        StringBuilder res = new StringBuilder();
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open(filename), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                res.append(mLine).append("\n");
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return res.toString();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        setTitle("");
        setSupportActionBar((Toolbar)findViewById(R.id.about_us_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Markwon.setMarkdown((TextView)findViewById(R.id.about_us_content), getContent("AboutUs.md"));
    }
}
