package com.paditech.mvpbase.screen.apkManage;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.mvp.activity.MVPActivity;
import com.paditech.mvpbase.common.view.SimpleDividerItemDecoration;
import com.paditech.mvpbase.screen.adapter.RecyclerViewApkAdapter;
import com.paditech.mvpbase.screen.home.HomeRecyclerViewAdapter;
import com.paditech.mvpbase.screen.main.ScrollTopEvent;
import com.paditech.mvpbase.screen.profile.ProfileActivity;
import com.paditech.mvpbase.screen.user.UserActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hung on 4/14/2018.
 */

public class ApkActivity extends MVPActivity<ApkContact.PresenterViewOps> implements ApkContact.ViewOps, View.OnClickListener {
    private static final String TAG = "No thing";
    @BindView(R.id.apk_scroll)
    NestedScrollView apk_scroll;
    @BindView(R.id.recycler_view_apk)
    RecyclerView recycler_view_apk;
    @BindView(R.id.recycler_view_like_app)
    RecyclerView recycler_view_like_app;

    SnapHelper snapHelperCmt = new LinearSnapHelper();
    SnapHelper snapHelperLike = new LinearSnapHelper();
    RecyclerViewApkAdapter mRecyclerViewApkAdapter;
    HomeRecyclerViewAdapter mLikeAppAdapter;


    @Override
    protected int getContentView() {
        return R.layout.fragment_apk;
    }

    @Override
    protected void initView() {
        SimpleDividerItemDecoration simpleDividerItemDecoration = new SimpleDividerItemDecoration(this,
                ContextCompat.getColor(this, R.color.gray_line), 120, 20);
        simpleDividerItemDecoration.setHasLastLine(false);

        mRecyclerViewApkAdapter = new RecyclerViewApkAdapter();
        mRecyclerViewApkAdapter.setAct(this);
        snapHelperCmt.attachToRecyclerView(recycler_view_apk);
        recycler_view_apk.setLayoutManager(new GridLayoutManager(this, 5, LinearLayoutManager.HORIZONTAL, false));
        recycler_view_apk.addItemDecoration(simpleDividerItemDecoration);
        recycler_view_apk.setAdapter(mRecyclerViewApkAdapter);

        getPresenter().getApkInfo();


        SimpleDividerItemDecoration decoration = new SimpleDividerItemDecoration(this, ContextCompat.getColor(this, R.color.gray_line), 120, 20);
        decoration.setHasLastLine(false);

        mLikeAppAdapter = new HomeRecyclerViewAdapter(this);
        mLikeAppAdapter.setItemId(R.layout.item_app_horizontal_white_bg);
        snapHelperLike.attachToRecyclerView(recycler_view_like_app);
        recycler_view_like_app.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false));
        recycler_view_like_app.addItemDecoration(decoration);
        recycler_view_like_app.setAdapter(mLikeAppAdapter);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void scrolltop(ScrollTopEvent event) {
        apk_scroll.smoothScrollTo(0, 0);
        apk_scroll.scrollTo(0, 0);

    }

    @Override
    protected Class<? extends ActivityPresenter> onRegisterPresenter() {
        return ApkPresenter.class;
    }


    private void getListApk() {

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

    private void externalApk() {
//        String path = Environment.getExternalStorageDirectory().toString()+"/com.paditech.mvpbase";
//        System.out.println(path + " pathăđă");
//        Log.d("Files", "Path ădă: " + path);
//        File directory = new File(path);
////        boolean d0 =directory.delete();
////
////        System.out.println("Delete Check File deleted: " + path + "/myFile " + d0);
//
//        File[] files = directory.listFiles();
//        System.out.println("Size: "+ files.length);
//        for (int i = 0; i < files.length; i++)
//        {
//
//            Log.d("Files", "FileName:" + files[i].getName());
//
//            File file = new File(Environment.getExternalStorageDirectory().toString()+"/com.paditech.mvpbase/com_facebook_orca.apk");
//            int file_size = Integer.parseInt(String.valueOf(file.length()/1024));
//            System.out.println(file_size + " size day nay ");
//
//        }
        String path = Environment.getExternalStorageDirectory().toString();
        File directory = new File(path);

        File[] files = directory.listFiles();
        System.out.println("Size: " + files.length);
        for (int i = 0; i < files.length; i++) {
            String pathChild = path + "/" + files[i].getName();

            System.out.println(pathChild);
            File directoryChild = new File(pathChild);
            File[] filesChild = directoryChild.listFiles();
            System.out.println(files[i].getName() + "  Size: " + filesChild.length);

            for (int j = 0; j < filesChild.length; j++) {
                if (filesChild[j].getName().indexOf(".apk") >= 0) {

                    String name = filesChild[j].getName();
                    String date = String.valueOf(filesChild[j].lastModified());

                    String finalpath = path + "/" + files[i].getName() + "/" + name;
                    System.out.println("final path   " + finalpath);
                    File file = new File(finalpath);
                    int file_size = Integer.parseInt(String.valueOf(file.length() / 1024));
                    System.out.println(file_size + " size day nay " + date);
                    name = name.replace("_", ".");
                    name = name.replace(".apk", "");
                } else {
                    System.out.println(filesChild[j].getName() + " not apk file");
                }

            }
        }
    }

    @Override
    public void onClick(View v) {

    }
}
