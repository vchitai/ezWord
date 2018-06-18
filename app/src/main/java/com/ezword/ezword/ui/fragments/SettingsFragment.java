package com.ezword.ezword.ui.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.ezword.ezword.R;

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.pref_user_settings);
    }
}