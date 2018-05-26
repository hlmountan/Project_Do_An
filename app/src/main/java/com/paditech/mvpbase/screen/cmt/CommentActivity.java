package com.paditech.mvpbase.screen.cmt;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.model.Cmt;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.mvp.activity.MVPActivity;
import com.paditech.mvpbase.screen.adapter.RecyclerViewCmtAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * Created by hung on 4/28/2018.
 */

public class CommentActivity extends MVPActivity<CommentContact.PresenterViewOps> implements CommentContact.ViewOps {
    @BindView(R.id.recycler_view_cmt)
    RecyclerView recycler_view_cmt;
    RecyclerViewCmtAdapter mRecyclerViewCmtAdapter;
    SnapHelper snapHelperCmt = new LinearSnapHelper();


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

    @Subscribe (threadMode = ThreadMode.MAIN, sticky = true)
    public void setUpInfo(AppModel.SourceBean app){
        getPresenter().getCmt(app.getAppid());
    }

    @Override
    protected int getContentView() {
        return R.layout.act_comment;
    }

    @Override
    protected void initView() {

        snapHelperCmt.attachToRecyclerView(recycler_view_cmt);
        mRecyclerViewCmtAdapter = new RecyclerViewCmtAdapter(this);
        recycler_view_cmt.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler_view_cmt.setAdapter(mRecyclerViewCmtAdapter);
    }

    @Override
    protected Class<? extends ActivityPresenter> onRegisterPresenter() {
        return CommentPresenter.class;
    }

    @Override
    public void setCmt(List<Cmt> cmtList) {
        if (cmtList != null)
            mRecyclerViewCmtAdapter.setCmt(cmtList);
    }
}
