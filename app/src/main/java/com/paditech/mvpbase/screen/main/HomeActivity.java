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

public class HomeActivity extends MVPActivity<HomeActContact.PresenterViewOps> implements HomeActContact.ViewOps, ViewPager.OnPageChangeListener, View.OnFocusChangeListener, View.OnClickListener, TextWatcher,SwipeRefreshLayout.OnRefreshListener,
        LoadMoreRecyclerView.LoadMoreListener {

    @BindView(R.id.vp_tablayout)
    ViewPager viewPager_tab_layout;
    @BindView(R.id.edit_text_search)
    EditText edit_text_search;
    @BindView(R.id.notification_view)
    LinearLayout notification_view;

    @BindView(R.id.search_view)
    NestedScrollView search_view;
    @BindView(R.id.btn_cancel_search)
    Button btn_cancel_search;
    @BindView(R.id.btn_assivetouch)
    FloatingActionButton btn_assivetouch;

    @BindView(R.id.ab_search)
    AppBarLayout ab_search;


    @BindView(R.id.recycler_view_search_item)
    LoadMoreRecyclerView recycler_view_search_item;
    HomeListAppAdapter mHomeListAppAdapter;
    NotificationRecycleViewAdapter mNotificationRecycleViewAdapter;

    @BindView(R.id.recycler_view_notification)
    RecyclerView recycler_view_notification;

    @BindView(R.id.btn_notification)
    Button btn_notification;
    @BindView(R.id.recycler_view_list_cate)
    RecyclerView recyclerViewCategory;
    @BindView(R.id.progressBar_search)
    ProgressBar progressBarSearch;
    private int mPage = 1;
    int isTextSearch =0 ;
    public String getmAppAPI() {
        return mAppAPI;
    }

    public void setmAppAPI(String mAppAPI) {
        this.mAppAPI = mAppAPI;
    }

    private String mAppAPI;

    private ChipCateAdapter mChipCateAdapter;


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

    @Subscribe (threadMode = ThreadMode.MAIN )
    public void onClickCate(String cate){
        System.out.println("focus");
        edit_text_search.requestFocus();
        edit_text_search.setText(cate);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {

        setupViewPagerMain();
        setRecyclerViewCategory();
        setUpAdapter();
        CommonUtil.dismissSoftKeyboard(findViewById(R.id.drawer), this);
        edit_text_search.setOnFocusChangeListener(this);
        edit_text_search.addTextChangedListener(this);
        btn_cancel_search.setOnClickListener(this);
        btn_assivetouch.setOnClickListener(this);
        viewPager_tab_layout.addOnPageChangeListener(this);
        btn_notification.setOnClickListener(this);

        search_view.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            public static final String TAG = "";

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    System.out.println("adqwd asxc aw ");
                }
                if (scrollY < oldScrollY) {
                    System.out.println("Scroll UP");
                    Log.i(TAG, "Scroll UP");
                }

                if (scrollY == 0) {
                    System.out.println("TOP SCROLL");
                    Log.i(TAG, "TOP SCROLL");
                }

                if (scrollY == ( v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight() )) {
                    if (!recycler_view_search_item.ismIsLoading()) {
                        System.out.println("bottom SCROLL");
//                        recycler_view_search_item.showLoadMoreIndicator();
                        onLoadMore();

                    }
                }
            }
        });

        recycler_view_search_item.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                System.out.println(dy);
            }


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                System.out.println(newState);
                if (!recyclerView.canScrollVertically(1)) {

                    System.out.println("loading more ");

                } else{
//                    search_view.setRefreshing(false);
                }
            }
        });

    }

    private void setRecyclerViewCategory() {
        mChipCateAdapter = new ChipCateAdapter();
        mChipCateAdapter.setAct(this);
        mChipCateAdapter.setmListener(new ChipCateAdapter.OnSelectCateListener() {
            @Override
            public void selectCate(String string) {
                edit_text_search.setText(string);
                isTextSearch = 1;
                 setmAppAPI("https://appsxyz.com/api/apk/search/?q=" + string + "&page=%s&size=15");


            }
        });
        ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(getActivityContext())
                //set vertical gravity for all items in a row. Default = Gravity.CENTER_VERTICAL
                .setChildGravity(Gravity.TOP)
                //whether RecyclerView can scroll. TRUE by default
                .setScrollingEnabled(true)
                //set maximum views count in a particular row
                .setMaxViewsInRow(4)
                //set gravity resolver where you can determine gravity for item in position.
                //This method have priority over previous one
                .setGravityResolver(new IChildGravityResolver() {
                    @Override
                    public int getItemGravity(int position) {
                        return Gravity.CENTER;
                    }
                })
                //a layoutOrientation of layout manager, could be VERTICAL OR HORIZONTAL. HORIZONTAL by default
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .build();
        recyclerViewCategory.setLayoutManager(chipsLayoutManager);
        recyclerViewCategory.setAdapter(mChipCateAdapter);
        recyclerViewCategory.setNestedScrollingEnabled(false);
        getPresenter().getListCates();
    }

    private void setupViewPagerMain() {
        FragmentManager manager = getSupportFragmentManager();
        MainViewPagerAdapter mMainViewPagerAdapter = new MainViewPagerAdapter(manager);
        mMainViewPagerAdapter.setAct(this);
        viewPager_tab_layout.setAdapter(mMainViewPagerAdapter);
        viewPager_tab_layout.setOffscreenPageLimit(2);

    }

    private void getSearchData(final String key) {

        EventBus.getDefault().post(key);
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
        switch (view.getId()) {
            case R.id.btn_cancel_search:
                search_view.setVisibility(View.GONE);
                viewPager_tab_layout.setVisibility(View.VISIBLE);
                btn_cancel_search.setVisibility(View.GONE);
                notification_view.setVisibility(View.GONE);
                btn_notification.setVisibility(View.VISIBLE);
                // clear text when cancel search
                edit_text_search.setText("");
                break;
            case R.id.btn_assivetouch:
                if (viewPager_tab_layout.getCurrentItem() == 0) {
                    EventBus.getDefault().post(new ScrollTopEvent());
                    ab_search.setExpanded(true);
                } else {
                    viewPager_tab_layout.setCurrentItem(0);
                }
                if (viewPager_tab_layout.getVisibility() == View.GONE) {
                    viewPager_tab_layout.setVisibility(View.VISIBLE);
                    notification_view.setVisibility(View.GONE);
                    search_view.setVisibility(View.GONE);
                    btn_notification.setVisibility(View.VISIBLE);
                    btn_cancel_search.setVisibility(View.GONE);
                    edit_text_search.setText("");
                }
                break;
            case R.id.btn_notification:
                viewPager_tab_layout.setVisibility(View.GONE);
                notification_view.setVisibility(View.VISIBLE);
                btn_assivetouch.setImageResource(R.drawable.ic_home);

                break;
        }
    }


    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
            search_view.setVisibility(View.VISIBLE);
            viewPager_tab_layout.setVisibility(View.GONE);
            btn_cancel_search.setVisibility(View.VISIBLE);
            notification_view.setVisibility(View.GONE);
            btn_notification.setVisibility(View.GONE);

        }
    }

    @Override
    protected Class<? extends ActivityPresenter> onRegisterPresenter() {
        return HomeActPresenter.class;
    }

    @Override
    public void setSearchResult(final List<AppModel> listApp) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mHomeListAppAdapter.setmList(listApp);
            }
        });

    }

    @Override
    public void updateListCates(final List<String> strings) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                 mChipCateAdapter.setmListCates(strings);
            }
        });
    }

    @Override
    public void onSearching() {
            btn_cancel_search.setVisibility(View.GONE);
            progressBarSearch.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSearchDone() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                    btn_cancel_search.setVisibility(View.VISIBLE);
                    progressBarSearch.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public void loadMore(final List<AppModel> listApp) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mHomeListAppAdapter.addMoremList(listApp);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (viewPager_tab_layout.getVisibility() == View.GONE) {
            viewPager_tab_layout.setVisibility(View.VISIBLE);
            notification_view.setVisibility(View.GONE);
            search_view.setVisibility(View.GONE);
            btn_notification.setVisibility(View.VISIBLE);
            btn_cancel_search.setVisibility(View.GONE);
            progressBarSearch.setVisibility(View.GONE);
        } else
            super.onBackPressed();
    }

    private void setUpAdapter(){
        mHomeListAppAdapter = new HomeListAppAdapter(this);
        mNotificationRecycleViewAdapter = new NotificationRecycleViewAdapter();
        recycler_view_notification.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler_view_notification.setAdapter(mNotificationRecycleViewAdapter);

        recycler_view_search_item.setLayoutManager(new GridLayoutManager(this, 3));
        recycler_view_search_item.setAdapter(mHomeListAppAdapter);
        recycler_view_search_item.setNestedScrollingEnabled(false);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!charSequence.toString().equals("")){
            mPage = 1;
            setmAppAPI("https://appsxyz.com/api/apk/search/?q=" + charSequence.toString() + "&page=%s&size=15");
            getPresenter().cURLSearchData(mPage,getmAppAPI());
            search_view.setSmoothScrollingEnabled(true);
            search_view.smoothScrollTo(0,0);
            recycler_view_search_item.smoothScrollBy(0,0);
            if (isTextSearch == 1){
                CommonUtil.dismissSoftKeyboard(findViewById(R.id.drawer), this);
                isTextSearch = 0 ;
            }

        }else{
            search_view.setSmoothScrollingEnabled(true);
            search_view.smoothScrollTo(0,0);
            recycler_view_search_item.smoothScrollBy(0,0);
        }


    }

    @Override
    public void afterTextChanged(Editable editable) {
        search_view.setSmoothScrollingEnabled(true);
        search_view.smoothScrollTo(0,0);
        recycler_view_search_item.smoothScrollBy(0,0);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        mPage +=1;
        getPresenter().cURLSearchData(mPage,getmAppAPI());
    }
}
