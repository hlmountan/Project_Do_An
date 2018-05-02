package com.paditech.mvpbase.screen.apkManage;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenter;
import com.paditech.mvpbase.common.mvp.fragment.MVPFragment;
import com.paditech.mvpbase.common.view.SimpleDividerItemDecoration;
import com.paditech.mvpbase.screen.adapter.RecyclerViewApkAdapter;
import com.paditech.mvpbase.screen.adapter.RecyclerViewCmtAdapter;
import com.paditech.mvpbase.screen.home.HomeFragment;
import com.paditech.mvpbase.screen.home.HomeRecyclerViewAdapter;
import com.paditech.mvpbase.screen.user.UserActivity;

import java.util.List;

import butterknife.BindView;

/**
 * Created by hung on 4/14/2018.
 */

public class ApkFragment extends MVPFragment<ApkContact.PresenterViewOps> implements ApkContact.ViewOps,View.OnClickListener {
    private static final String TAG = "No thing";
    @BindView(R.id.recycler_view_apk)
    RecyclerView recycler_view_apk;
    @BindView(R.id.recycler_view_like_app)
    RecyclerView recycler_view_like_app;

    @BindView(R.id.btn_profile)
    Button btn_profile;
    Activity act;

    SnapHelper snapHelperCmt = new LinearSnapHelper();
    SnapHelper snapHelperLike = new LinearSnapHelper();
    RecyclerViewApkAdapter mRecyclerViewApkAdapter;
    HomeRecyclerViewAdapter mLikeAppAdapter ;
    public Activity getAct() {
        return act;
    }

    public void setAct(Activity act) {
        this.act = act;
    }

    public static ApkFragment getInstance(Activity act) {
        ApkFragment f = new ApkFragment();
        f.setAct(act);
        return f;

    }
    @Override
    protected int getContentView() {
        return R.layout.fragment_apk;
    }

    @Override
    protected void initView(View view) {
        btn_profile.setOnClickListener(this);

        SimpleDividerItemDecoration simpleDividerItemDecoration = new SimpleDividerItemDecoration(act, ContextCompat.getColor(act, R.color.gray_line), 120, 20);
        simpleDividerItemDecoration.setHasLastLine(false);

        mRecyclerViewApkAdapter = new RecyclerViewApkAdapter();
        mRecyclerViewApkAdapter.setAct(act);
        snapHelperCmt.attachToRecyclerView(recycler_view_apk);
        recycler_view_apk.setLayoutManager(new GridLayoutManager(view.getContext(),5,LinearLayoutManager.HORIZONTAL,false));
        recycler_view_apk.setAdapter(mRecyclerViewApkAdapter);
        recycler_view_apk.addItemDecoration(simpleDividerItemDecoration);
        getPresenter().getApkInfo();

        mLikeAppAdapter = new HomeRecyclerViewAdapter(act);
        mLikeAppAdapter.setItemId(R.layout.item_app_horizontal_white_bg);
        snapHelperLike.attachToRecyclerView(recycler_view_like_app);
        recycler_view_like_app.setLayoutManager(new GridLayoutManager(view.getContext(),2,LinearLayoutManager.HORIZONTAL,false));
        recycler_view_like_app.setAdapter(mLikeAppAdapter);
        recycler_view_like_app.addItemDecoration(simpleDividerItemDecoration);


    }

    @Override
    protected Class<? extends FragmentPresenter> onRegisterPresenter() {
        return ApkPresenter.class;
    }



    private void getListApk(){

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_profile:
                this.startActivity(new Intent(act, UserActivity.class));
                break;
        }
    }

    @Override
    public void loadApk(List<ApplicationInfo> packages) {
        mRecyclerViewApkAdapter.setPackages(packages);
    }

    @Override
    public void loadLikeApp(final List<AppModel> app) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLikeAppAdapter.setmList1(app);
            }
        });

    }
}
