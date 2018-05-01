package com.paditech.mvpbase.screen.showMoreApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.base.BaseActivity;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.model.Appsxyz;
import com.paditech.mvpbase.common.service.APIClient;
import com.paditech.mvpbase.common.service.ICallBack;
import com.paditech.mvpbase.common.utils.CommonUtil;
import com.paditech.mvpbase.common.view.LoadMoreRecyclerView;
import com.paditech.mvpbase.common.view.TranslationNestedScrollView;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by hung on 1/19/2018.
 */

public class ShowMoreActicity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, LoadMoreRecyclerView.LoadMoreListener {

    ShowMoreViewPagerAdapter mShowMoreViewPagerAdapter;
    ShowMoreRecyclerViewAdapter mShowMoreRecyclerViewAdapter;
    @BindView(R.id.view_pager_show_more)
    ViewPager viewPagerShowMore;
    @BindView(R.id.recycler_show_more)
    LoadMoreRecyclerView recyclerViewShowMore;
    @BindView(R.id.indicator_show_more)
    CircleIndicator circleIndicator;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.vp_progress_bar)
    ProgressBar vp_progress_bar;
    @BindView(R.id.scrollView_show_more)
    TranslationNestedScrollView scrollView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.text_list_app_sm)
    TextView textView_sm;
    @BindView(R.id.tv_list_end)
    TextView textView_ls;
    @BindView(R.id.btn_back)
    FloatingActionButton button;

    private List<AppModel.SourceBean> mList = new ArrayList<>();
    private boolean mRunned;
    private GridLayoutManager mGridLayoutManager;
    private int mPage = 1;
    private String mAppAPI;

    @Override
    protected int getContentView() {
        return R.layout.act_show_more_app;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();

        //check  if true -> be called from category fragment else home fragment
        if (!Objects.equals(intent.getStringExtra("CHECKCATE"), "TRUE")) {
            mAppAPI = intent.getStringExtra("CATE_URL");
            setRecyclerViewAdapter();
        } else {
            mAppAPI = "https://appsxyz.com/api/apk/apk_category/?cat_name=" + URLEncoder.encode(intent.getStringExtra("CATE_NAME")) + "&size=20&order_by=d_rating&installs=10000&page=%s";
            setRecyclerViewAdapter();
        }
        // present screen name
//        setCatename(intent.getStringExtra("CATE_NAME"));
    }


    private void setCatename(String cate) {
        switch (cate) {

            //show in homefragment
            case "lastest":
                textView_sm.setText("Lastest ");
                textView_ls.setText("Sale");
                break;
            case "topdownload":
                textView_sm.setText("Google Play ");
                textView_ls.setText("Top Download");
                break;
            case "grossing":
                textView_sm.setText("Top Grossing Android ");
                textView_ls.setText("App");
                break;
            case "gonefree":
                textView_sm.setText("App ");
                textView_ls.setText("Gone Free");
                break;
            default:
                // show cate name
                textView_sm.setText("Top "+ cate);
                textView_ls.setText("  Apps");
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initView() {

        scrollView.setViewPager(viewPagerShowMore, CommonUtil.getWidthScreen(this)/2);
        if (!mRunned) {
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new ShowMoreActicity.MyTimerTask(), 2000, 4000);
        }
        mRunned = true;

        // set up recycleview
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    Log.d("Scroll", "BOTTOM SCROLL");
                    // onLoadMore
                    if (!recyclerViewShowMore.ismIsLoading()) {
                        recyclerViewShowMore.showLoadMoreIndicator();
                        onLoadMore();
                    }else
                        swipeRefreshLayout.setRefreshing(false);

                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void getMoreApp() {
        APIClient.getInstance().execGet(String.format(mAppAPI, mPage), null, new ICallBack() {
            @Override
            public void onErrorToken() {
            }

            @Override
            public void onFailed(IOException e) {

            }

            @Override
            public void onResponse(String response, boolean isSuccessful) {
                final Appsxyz result = new Gson().fromJson(response, Appsxyz.class);
                if (result != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.getResult() != null)
                                if (mPage == 1) {
                                    mShowMoreRecyclerViewAdapter.setmList(result.getResult());
                                    setViewpager(result);
                                } else {
                                    mShowMoreRecyclerViewAdapter.addMoremList(result.getResult());
                                }
                            vp_progress_bar.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            System.out.println("Load more in");
                            swipeRefreshLayout.setRefreshing(false);
                            recyclerViewShowMore.onLoadMoreComplete();
                        }
                    });

                }
            }
        });
    }



    public void setViewpager(Appsxyz result) {
        mShowMoreViewPagerAdapter = new ShowMoreViewPagerAdapter();
        mShowMoreViewPagerAdapter.setResult(result);
        mShowMoreViewPagerAdapter.setAct(this);
        viewPagerShowMore.setAdapter(mShowMoreViewPagerAdapter);
        circleIndicator.setViewPager(viewPagerShowMore);
    }

    public void setRecyclerViewAdapter() {
        if (mShowMoreRecyclerViewAdapter == null) {
            mShowMoreRecyclerViewAdapter = new ShowMoreRecyclerViewAdapter(this);
//            getMoreApp();
        }
        mGridLayoutManager = new GridLayoutManager(ShowMoreActicity.this, 3);
        recyclerViewShowMore.setLayoutManager(mGridLayoutManager);
        recyclerViewShowMore.setAdapter(mShowMoreRecyclerViewAdapter);
        recyclerViewShowMore.setNestedScrollingEnabled(false);
        recyclerViewShowMore.setLoadMoreListener(this);
    }


    @Override
    public void onRefresh() {
        mPage = 1;
//        getMoreApp();
    }

    @Override
    public void onLoadMore() {
        mPage++;
//        getMoreApp();
        System.out.println(" load more ");
    }

    public class MyTimerTask extends TimerTask {

        @Override

        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPagerShowMore.getCurrentItem() == 0) {
                        viewPagerShowMore.setCurrentItem(1);
                    } else if (viewPagerShowMore.getCurrentItem() == 1) {
                        viewPagerShowMore.setCurrentItem(2);
                    } else if (viewPagerShowMore.getCurrentItem() == 2) {
                        viewPagerShowMore.setCurrentItem(3);
                    } else if (viewPagerShowMore.getCurrentItem() == 3) {
                        viewPagerShowMore.setCurrentItem(4);
                    } else if (viewPagerShowMore.getCurrentItem() == 4) {
                        viewPagerShowMore.setCurrentItem(0);
                    }
                }
            });

        }
    }

}
