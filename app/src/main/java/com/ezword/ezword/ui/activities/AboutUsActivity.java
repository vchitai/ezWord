package com.ezword.ezword.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ezword.ezword.R;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class AboutUsActivity extends AppCompatActivity {

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
    }
}
