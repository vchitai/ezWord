package com.ezword.ezword.Activities;

import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ezword.ezword.Adapter.MainViewPagerAdapter;
import com.ezword.ezword.R;

public class MainActivity extends AppCompatActivity {
    private MainViewPagerAdapter mMainPagerAdapter;
    private ViewPager mMainViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        mMainPagerAdapter = new MainViewPagerAdapter(this,getSupportFragmentManager());

        mMainViewPager = (ViewPager) findViewById(R.id.main_view_pager);
        mMainViewPager.setAdapter(mMainPagerAdapter);
        mMainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getSupportActionBar().setTitle(((TabLayout)findViewById(R.id.main_tab_layout)).getTabAt(position).getText());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        tabLayout.setupWithViewPager(mMainViewPager);
    }
}
