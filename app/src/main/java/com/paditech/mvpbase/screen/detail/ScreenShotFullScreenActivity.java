package com.paditech.mvpbase.screen.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by hung on 2/28/2018.
 */

public class ScreenShotFullScreenActivity extends BaseActivity implements View.OnClickListener{

    DetailViewPagerAdapter detailViewPagerAdapter;
    @BindView(R.id.rl_screen_shot)
    RelativeLayout relativeLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.indicator)
    CircleIndicator circleIndicator;

    @Override
    protected int getContentView() {
        return R.layout.act_screen_short;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initView() {


    }

    @Subscribe (threadMode = ThreadMode.MAIN, sticky = true)
    public void getPagerData(ArrayList<String> app){
        detailViewPagerAdapter = new DetailViewPagerAdapter();
        detailViewPagerAdapter.setmList(app);
        viewPager.setAdapter(detailViewPagerAdapter);
        circleIndicator.setViewPager(viewPager);
    }

    @Override
    public void onClick(View view) {
        finish();
    }

}
