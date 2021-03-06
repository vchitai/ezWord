package com.ezword.ezword.ui.main_activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ezword.ezword.background.database.LocalData;
import com.ezword.ezword.background.services.AlarmReceiver;
import com.ezword.ezword.R;
import com.ezword.ezword.ui.activities.AboutUsActivity;
import com.ezword.ezword.ui.activities.BookmarkActivity;
import com.ezword.ezword.ui.activities.HelpActivity;
import com.ezword.ezword.ui.activities.HistoryActivity;
import com.ezword.ezword.ui.activities.SettingsActivity;
import com.ezword.ezword.ui.adapters.MainViewPagerAdapter;
import com.ezword.ezword.background.dictionary.Dictionary;

import java.util.Calendar;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.calligraphy3.CalligraphyUtils;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private MainViewPagerAdapter mMainPagerAdapter;
    private ViewPager mMainViewPager;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private Dictionary mDictionary;
    private int currentTab = 0;
    private static final int[] TabIcons = {R.drawable.ic_search_white_24dp,
            R.drawable.ic_check_white_24dp,
            R.drawable.ic_search_accent_24dp,
            R.drawable.ic_check_accent_24dp};
    public static void changeTabsFont(TabLayout tabLayout, String fontName) {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    CalligraphyUtils.applyFontToTextView(tabLayout.getContext(), (TextView) tabViewChild, fontName);
                }
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpNavigationLayout();
        setUpToolbar();
        setUpViewPager();
        setUpDailyNotification();
    }

    private void setUpDailyNotification() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        alarmIntent.setData((Uri.parse("custom://"+System.currentTimeMillis())));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }

        if (LocalData.getInstance(MainActivity.this).getDailyWordEnable()) {
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

    private void setUpViewPager() {
        final TabLayout tabLayout = findViewById(R.id.main_tab_layout);
        mMainPagerAdapter = new MainViewPagerAdapter(this,getSupportFragmentManager());
        mMainViewPager = findViewById(R.id.main_view_pager);
        mMainViewPager.setAdapter(mMainPagerAdapter);
        mMainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                CharSequence title = tabLayout.getTabAt(position).getText();
                tabLayout.getTabAt(position).setIcon(TabIcons[position+2]);
                getSupportActionBar().setTitle(title);
                tabLayout.getTabAt(currentTab).setIcon(TabIcons[currentTab]);
                currentTab = position;
                changeTabsFont(tabLayout,"Lato/Lato-Light.ttf");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setupWithViewPager(mMainViewPager);
        CharSequence title = tabLayout.getTabAt(0).getText();
        getSupportActionBar().setTitle(title);
        tabLayout.getTabAt(0).setIcon(TabIcons[2]);
        tabLayout.getTabAt(1).setIcon(TabIcons[1]);
        changeTabsFont(tabLayout,"Lato/Lato-Light.ttf");
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setTitle(null);
    }

    private void setUpNavigationLayout() {
        mDrawerLayout = findViewById(R.id.main_drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_closed);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        NavigationView navigationView = findViewById(R.id.main_navigation_view);
        navigationView.getMenu().findItem(R.id.nav_history).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
                return true;
            }
        });
        navigationView.getMenu().findItem(R.id.nav_bookmark).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this, BookmarkActivity.class);
                startActivity(intent);
                return true;
            }
        });
        navigationView.getMenu().findItem(R.id.nav_settings).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            }
        });
        navigationView.getMenu().findItem(R.id.nav_help).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
                return true;
            }
        });
        navigationView.getMenu().findItem(R.id.nav_about_us).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
                startActivity(intent);
                return true;
            }
        });
        navigationView.getMenu().findItem(R.id.nav_sync).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this, SyncActivity.class);
                startActivity(intent);
                return true;
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
