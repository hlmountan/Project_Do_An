package com.paditech.mvpbase.screen.dev;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.widget.TextView;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.mvp.activity.MVPActivity;
import com.paditech.mvpbase.common.view.SimpleDividerItemDecoration;
import com.paditech.mvpbase.screen.adapter.HomeRecyclerViewAdapter;
import com.paditech.mvpbase.screen.adapter.StartSnapHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.URLEncoder;
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

    SnapHelper snapHelper = new StartSnapHelper();
    HomeRecyclerViewAdapter mHomeListAppAdapter = new HomeRecyclerViewAdapter(this);
    String url = "";

//    Intent intent = getIntent();


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void setUpInfo(AppModel.SourceBean app) {
        if (!app.isUserUpload())
            getPresenter().getServerDev("http://appsxyz.com/api/apk/search_related/?q=" +
                    URLEncoder.encode("Free Fire") + "&page=1&size=20");
        else getPresenter().getFirebaseDev(app.getDevId());
    }

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
        mHomeListAppAdapter.setItemId(R.layout.item_related_app);
        recycler_view_all.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.HORIZONTAL, false));
        recycler_view_all.setAdapter(mHomeListAppAdapter);
        recycler_view_all.addItemDecoration(simpleDividerItemDecoration);

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
            }
        });

    }
}
