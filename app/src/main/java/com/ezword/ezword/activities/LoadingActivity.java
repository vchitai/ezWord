package com.ezword.ezword.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ezword.ezword.R;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        final Intent mainIntent = new Intent(this, MainActivity.class);
        int res = 0;
        startActivityForResult(mainIntent, res);
    }
}
