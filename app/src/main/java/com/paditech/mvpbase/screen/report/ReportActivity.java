package com.paditech.mvpbase.screen.report;

import android.view.View;
import android.widget.Button;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.mvp.activity.MVPActivity;

import butterknife.BindView;

/**
 * Created by hung on 4/28/2018.
 */

public class ReportActivity extends MVPActivity<ReportContact.PresenterViewOps> implements ReportContact.ViewOps, View.OnClickListener {
    @BindView(R.id.btn_submit)
    Button btn_submit;

    @Override
    protected int getContentView() {
        return R.layout.act_report_app;
    }

    @Override
    protected void initView() {
        btn_submit.setOnClickListener(this);
    }

    @Override
    protected Class<? extends ActivityPresenter> onRegisterPresenter() {
        return ReportPresenter.class;
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
