package com.paditech.mvpbase.screen.main;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.paditech.mvpbase.common.mvp.fragment.MVPFragment;
import com.paditech.mvpbase.screen.apkManage.ApkFragment;
import com.paditech.mvpbase.screen.home.HomeFragment;
import com.paditech.mvpbase.screen.search.SearchFragment;
import com.paditech.mvpbase.screen.uploadApk.UploadApkFragment;

/**
 * Created by hung on 1/22/2018.
 */

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    Activity act;

    public Activity getAct() {
        return act;
    }

    public void setAct(Activity act) {
        this.act = act;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        android.support.v4.app.Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = HomeFragment.getInstance(getAct());
                return fragment;
            case 1:
                fragment = SearchFragment.getInstance(getAct());
                return fragment;
            case 2:
                fragment = UploadApkFragment.getInstance(getAct());
                return fragment;
            default:
                fragment = ApkFragment.getInstance(getAct());
                return fragment;


        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Home";
                break;
            case 1:
                title = "Search";
                break;
            default:
                title = "APK";
                break;
        }
        return title;
    }
}
