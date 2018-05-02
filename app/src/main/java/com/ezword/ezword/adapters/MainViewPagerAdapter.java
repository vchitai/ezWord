package com.ezword.ezword.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ezword.ezword.R;
import com.ezword.ezword.fragments.HomeFragment;
import com.ezword.ezword.fragments.ReviewFragment;

/**
 * Created by chita on 02/05/2018.
 */

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public MainViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            //break;
            case 1:
                return new ReviewFragment();
            //break;
            default:
                return null;
            //break;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.lookup);
            case 1:
                return mContext.getString(R.string.revise);
            default:
                return null;
        }
    }
}