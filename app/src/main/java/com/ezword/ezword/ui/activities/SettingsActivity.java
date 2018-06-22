package com.ezword.ezword.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ezword.ezword.R;
import com.ezword.ezword.ui.fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
