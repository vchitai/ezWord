package com.ezword.ezword.ui.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.util.Log;

import com.ezword.ezword.R;
import com.ezword.ezword.background.database.LocalData;
import com.ezword.ezword.background.services.AlarmReceiver;

import java.util.Calendar;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;

public class SettingsFragment extends PreferenceFragment{
    private static final String TAG = SettingsFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.pref_user_settings);

        SwitchPreference daily_word_enable_sp = (SwitchPreference) findPreference("daily-word-enable");
        daily_word_enable_sp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                LocalData.getInstance(getActivity()).setDailyWordEnable((boolean)o);
                setUpDailyNotification();
                return true;
            }
        });

        ListPreference countDownTimePref = (ListPreference)findPreference("countdown_time_pref");
        countDownTimePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                LocalData.getInstance(getActivity()).setCountDownTime((String) o);
                return true;
            }
        });

        ListPreference numOfWordInSession = (ListPreference) findPreference("num_of_words_one_session");
        numOfWordInSession.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                LocalData.getInstance(getActivity()).setNumOfWordInSession((String) o);
                return true;
            }
        });
    }

    private void setUpDailyNotification() {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(getActivity(), AlarmReceiver.class);
        alarmIntent.setData((Uri.parse("custom://"+System.currentTimeMillis())));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }

        if (LocalData.getInstance(getActivity()).getDailyWordEnable()) {
            Calendar alarmStartTime = Calendar.getInstance();
            Calendar now = Calendar.getInstance();
            alarmStartTime.set(Calendar.HOUR_OF_DAY, 0);
            alarmStartTime.set(Calendar.MINUTE, 0);
            alarmStartTime.set(Calendar.SECOND, 0);
            if (now.after(alarmStartTime)) {
                Log.d(TAG, "Added a day");
                alarmStartTime.add(Calendar.DATE, 1);
            }

            if (alarmManager != null) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
            Log.d(TAG, "Alarms set for everyday");
        }
    }
}