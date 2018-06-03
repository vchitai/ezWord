package com.ezword.ezword.activities;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ezword.ezword.R;

public class RequestSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        AlertDialog alertDialog = new AlertDialog.Builder(RequestSearchActivity.this).create();
        alertDialog.setMessage("Search keyword is " + "LOL");
        alertDialog.show();*/
        setContentView(R.layout.activity_request_search);
    }
}
