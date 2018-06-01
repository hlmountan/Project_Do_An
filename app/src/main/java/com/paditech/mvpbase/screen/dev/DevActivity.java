package com.paditech.mvpbase.screen.dev;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.mvp.activity.MVPActivity;
import com.paditech.mvpbase.common.view.SimpleDividerItemDecoration;
import com.paditech.mvpbase.screen.adapter.HomeRecyclerViewAdapter;
import com.paditech.mvpbase.screen.adapter.StartSnapHelper;

import java.util.List;

import butterknife.BindView;

/**
 * Created by hung on 4/27/2018.
 */

public class DevActivity extends MVPActivity<DevContact.PresenterViewOps> implements DevContact.ViewOps {
    @BindView(R.id.tv_dev_name)
    TextView tv_dev_namel;
    @BindView(R.id.recycler_view_all)
    RecyclerView recycler_view_all;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    SnapHelper snapHelper = new StartSnapHelper();
    HomeRecyclerViewAdapter mHomeListAppAdapter = new HomeRecyclerViewAdapter(this);
    String url = "";

//    Intent intent = getIntent();
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    protected int getContentView() {
        return R.layout.act_dev_app;
    }

    @Override
    protected void initView() {
        SimpleDividerItemDecoration simpleDividerItemDecoration = new SimpleDividerItemDecoration(this, ContextCompat.getColor(this, R.color.gray_line), 120, 20);
        simpleDividerItemDecoration.setHasLastLine(false);
        snapHelper.attachToRecyclerView(recycler_view_all);
        mHomeListAppAdapter.setItemId(R.layout.item_app_horizontal_white_bg);
        recycler_view_all.setLayoutManager(new GridLayoutManager(this, 6, LinearLayoutManager.HORIZONTAL, false));
        recycler_view_all.setAdapter(mHomeListAppAdapter);
        recycler_view_all.addItemDecoration(simpleDividerItemDecoration);

        if (getIntent().getStringExtra("DEVID") != null)
            getPresenter().getServerDev(getIntent().getStringExtra("DEVID").replace(" ","+"));
        else getPresenter().getFirebaseDev(getIntent().getStringExtra("DEVID"));

        tv_dev_namel.setText(getIntent().getStringExtra("DEVID"));
    }

    @Override
    protected Class<? extends ActivityPresenter> onRegisterPresenter() {
        return DevPresenter.class;
    }

    @Override
    public void setRelateApp(final List<AppModel> app) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (app != null)
                    mHomeListAppAdapter.setmList1(app);

                progressBar.setVisibility(View.GONE);
            }
        });

    }
}
