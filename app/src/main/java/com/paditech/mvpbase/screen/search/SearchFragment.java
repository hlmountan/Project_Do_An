package com.paditech.mvpbase.screen.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.base.BaseFragment;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.model.Appsxyz;
import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenter;
import com.paditech.mvpbase.common.mvp.fragment.MVPFragment;
import com.paditech.mvpbase.common.service.APIClient;
import com.paditech.mvpbase.common.service.ICallBack;
import com.paditech.mvpbase.common.utils.CommonUtil;
import com.paditech.mvpbase.common.view.LoadMoreRecyclerView;
import com.paditech.mvpbase.screen.home.HomeListAppAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Nha Nha on 1/2/2018.
 */

public class SearchFragment extends MVPFragment<SearchContact.PresenterViewOps> implements SwipeRefreshLayout.OnRefreshListener,
        LoadMoreRecyclerView.LoadMoreListener, SearchContact.ViewOps {


    @BindView(R.id.recycler_view_search_item)
    LoadMoreRecyclerView recyclerView;
    HomeListAppAdapter mHomeListAppAdapter;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    public String getmAppAPI() {
        return mAppAPI;
    }

    public void setmAppAPI(String mAppAPI) {
        this.mAppAPI = mAppAPI;
    }

    private String mAppAPI;

    private int mPage = 1;

    @Override
    protected int getContentView() {
        return R.layout.frag_search;
    }

    @Override
    protected void initView(View view) {
        CommonUtil.dismissSoftKeyboard(view, getActivityReference());
        progressBar.setVisibility(View.GONE);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        mHomeListAppAdapter = new HomeListAppAdapter(getActivityReference());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(mHomeListAppAdapter);
        recyclerView.setLoadMoreListener(this);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView1, int newState) {
                super.onScrollStateChanged(recyclerView1, newState);

                if (!recyclerView1.canScrollVertically(1)) {
                    if (!recyclerView.ismIsLoading()) {
                        recyclerView.showLoadMoreIndicator();
                        onLoadMore();
                    }
                } else
                    swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected Class<? extends FragmentPresenter> onRegisterPresenter() {
        return SearchPresenter.class;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSearchResult(String key) {
        String url = "https://appsxyz.com/api/apk/search/?q=" + key + "&page=%s&size=15";
        if (!key.equals(""))
            progressBar.setVisibility(View.VISIBLE);

        mPage = 1;
        setmAppAPI(url);
        getPresenter().getMoreApp(mPage, mAppAPI);
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        mPage++;
        getPresenter().getMoreApp(mPage, mAppAPI);
        System.out.println(" load more ");
    }

    @Override
    public void onLoadDone() {
        super.onLoadDone();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                recyclerView.onLoadMoreComplete();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onLoading() {
        super.onLoading();
        swipeRefreshLayout.setRefreshing(true);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateApps(final List<AppModel> appModels) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mHomeListAppAdapter.setmList(appModels);
            }
        });
    }

    @Override
    public void addApps(final List<AppModel> appModels) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mHomeListAppAdapter.addMoremList(appModels);
            }
        });
    }
}
