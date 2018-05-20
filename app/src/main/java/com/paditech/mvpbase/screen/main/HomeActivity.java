package com.paditech.mvpbase.screen.main;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.event.NewNotificationEvent;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.mvp.activity.MVPActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;

public class HomeActivity extends MVPActivity<HomeActContact.PresenterViewOps> implements HomeActContact.ViewOps, ViewPager.OnPageChangeListener, View.OnClickListener, TabLayout.OnTabSelectedListener {

    @BindView(R.id.vp_tablayout)
    ViewPager viewPager_tab_layout;
    @BindView(R.id.btn_assivetouch)
    FloatingActionButton btn_assivetouch;

    // tab
    @BindView(R.id.tab_layout)
    TabLayout tab_layout;

    private View mBadge;
    private int[] navIcons = {
            R.drawable.ic_today_home_main,
            R.drawable.ic_search_18dp,
            R.drawable.ic_upload_selector,
            R.drawable.ic_notification_selector
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void searchTag(ArrayList<String> tag) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewNotification(NewNotificationEvent event) {
        if (mBadge != null)
            mBadge.setVisibility(View.VISIBLE);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
//    CommonUtil.setMiuiStatusBarDarkMode(this,true);

        setupViewPagerMain();

        btn_assivetouch.setOnClickListener(this);
        viewPager_tab_layout.addOnPageChangeListener(this);
//        btn_notification.setOnClickListener(this);


    }


    private void setupViewPagerMain() {
        FragmentManager manager = getSupportFragmentManager();
        MainViewPagerAdapter mMainViewPagerAdapter = new MainViewPagerAdapter(manager);
        mMainViewPagerAdapter.setAct(this);
        viewPager_tab_layout.setAdapter(mMainViewPagerAdapter);
        viewPager_tab_layout.setOffscreenPageLimit(4);
        tab_layout.setupWithViewPager(viewPager_tab_layout);
        tab_layout.addOnTabSelectedListener(this);
        for (int i = 0; i < tab_layout.getTabCount(); i++) {
            // inflate the Parent LinearLayout Container for the tab
            // from the layout nav_tab.xml file that we created 'R.layout.nav_tab
            View tab =  LayoutInflater.from(this).inflate(R.layout.navigation_tablayout, null);

            // get child TextView and ImageView from this layout for the icon and label
            ImageView tab_icon = (ImageView) tab.findViewById(R.id.nav_icon);
            if (i == tab_layout.getTabCount() - 1) {
                mBadge = tab.findViewById(R.id.badge);
            }

            // set the label text by getting the actual string value by its id
            // by getting the actual resource value `getResources().getString(string_id)`
            tab_icon.setImageResource(navIcons[i]);

            // finally publish this custom view to navigation tab
            tab_layout.getTabAt(i).setCustomView(tab);
        }

        viewPager_tab_layout.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 3) mBadge.setVisibility(View.GONE);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {

    }


    @Override
    protected Class<? extends ActivityPresenter> onRegisterPresenter() {
        return HomeActPresenter.class;
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        System.out.println("selected");
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        System.out.println("unselected");
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        if (tab.getPosition() != 2) {
            EventBus.getDefault().post(new ScrollTopEvent());
        }
    }

    public void setVPitem(int pos) {
        viewPager_tab_layout.setCurrentItem(pos);
    }
}
