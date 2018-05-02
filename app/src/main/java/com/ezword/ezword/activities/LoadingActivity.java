package com.ezword.ezword.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ezword.ezword.R;
import com.ezword.ezword.activities.MainActivity;

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
