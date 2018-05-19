package com.paditech.mvpbase.screen.apkFile;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.mvp.activity.MVPActivity;
import com.paditech.mvpbase.screen.adapter.RecyclerViewAllApkAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by hung on 5/10/2018.
 */

public class AllApkFileActivity extends MVPActivity<AllApkFileContact.PresenterViewOps> implements AllApkFileContact.ViewOps {

    @BindView(R.id.recycler_view_all_apk)
    RecyclerView recycler_view_all_apk;
    RecyclerViewAllApkAdapter apkAdapter = new RecyclerViewAllApkAdapter(this);
    @Override
    public void loadApkFile(ArrayList<ArrayList<String>> listApk) {
        apkAdapter.setListApk(listApk);
    }

    @Override
    protected int getContentView() {
        return R.layout.act_all_apk_file;
    }

    @Override
    protected void initView() {

        recycler_view_all_apk.setLayoutManager(new GridLayoutManager(this,3, LinearLayoutManager.HORIZONTAL,false));
        recycler_view_all_apk.setAdapter(apkAdapter);
        getPresenter().getListApkFile();
    }

    @Override
    protected Class<? extends ActivityPresenter> onRegisterPresenter() {
        return AllApkFilePresenter.class;
    }
}
