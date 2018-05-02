package com.paditech.mvpbase.screen.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.EventLog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.gravity.IChildGravityResolver;
import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.base.BaseActivity;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.mvp.activity.MVPActivity;
import com.paditech.mvpbase.common.utils.CommonUtil;
import com.paditech.mvpbase.common.view.LoadMoreRecyclerView;
import com.paditech.mvpbase.screen.home.HomeListAppAdapter;
import com.paditech.mvpbase.screen.main.adapter.ChipCateAdapter;
import com.paditech.mvpbase.screen.showMoreApp.ShowMoreActicity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

public class HomeActivity extends MVPActivity<HomeActContact.PresenterViewOps> implements HomeActContact.ViewOps, ViewPager.OnPageChangeListener, View.OnClickListener
{

    @BindView(R.id.vp_tablayout)
    ViewPager viewPager_tab_layout;
    @BindView(R.id.notification_view)
    LinearLayout notification_view;
    @BindView(R.id.btn_assivetouch)
    FloatingActionButton btn_assivetouch;
    NotificationRecycleViewAdapter mNotificationRecycleViewAdapter;
    @BindView(R.id.recycler_view_notification)
    RecyclerView recycler_view_notification;

    // tab
    @BindView(R.id.tab_layout)
    TabLayout tab_layout;

    private int[] navLabels = {
            R.string.title_home,
            R.string.title_category,
            R.string.title_search,
    };
    private int[] navIcons = {
            R.drawable.ic_iphone_1,
            R.drawable.ic_category_18dp,
            R.drawable.ic_search_18dp
    };



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {

        setupViewPagerMain();
        setUpAdapter();



        btn_assivetouch.setOnClickListener(this);
        viewPager_tab_layout.addOnPageChangeListener(this);
//        btn_notification.setOnClickListener(this);


    }


    private void setupViewPagerMain() {
        FragmentManager manager = getSupportFragmentManager();
        MainViewPagerAdapter mMainViewPagerAdapter = new MainViewPagerAdapter(manager);
        mMainViewPagerAdapter.setAct(this);
        viewPager_tab_layout.setAdapter(mMainViewPagerAdapter);
        viewPager_tab_layout.setOffscreenPageLimit(3);
        tab_layout.setupWithViewPager(viewPager_tab_layout);
        for (int i = 0; i < tab_layout.getTabCount(); i++) {
            // inflate the Parent LinearLayout Container for the tab
            // from the layout nav_tab.xml file that we created 'R.layout.nav_tab
            LinearLayout tab = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.navigation_tablayout, null);

            // get child TextView and ImageView from this layout for the icon and label
            TextView tab_label = (TextView) tab.findViewById(R.id.nav_label);
            ImageView tab_icon = (ImageView) tab.findViewById(R.id.nav_icon);

            // set the label text by getting the actual string value by its id
            // by getting the actual resource value `getResources().getString(string_id)`
            tab_label.setText(getResources().getString(navLabels[i]));
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
        if (position != 0) {
            btn_assivetouch.setImageResource(R.drawable.ic_home);
        } else {
            btn_assivetouch.setImageResource(R.drawable.pulse);
        }
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




    private void setUpAdapter(){

        mNotificationRecycleViewAdapter = new NotificationRecycleViewAdapter();
        recycler_view_notification.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler_view_notification.setAdapter(mNotificationRecycleViewAdapter);

    }


}
