package com.paditech.mvpbase.screen.profile;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.model.UserProfile;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.mvp.activity.MVPActivity;
import com.paditech.mvpbase.common.utils.ImageUtil;
import com.paditech.mvpbase.common.view.SimpleDividerItemDecoration;
import com.paditech.mvpbase.screen.adapter.RecyclerViewReplyCmtAdapter;
import com.paditech.mvpbase.screen.home.HomeRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hung on 5/7/2018.
 */

public class
ProfileActivity extends MVPActivity<ProfileContact.PresenterViewOps> implements ProfileContact.ViewOps, View.OnClickListener {
    @BindView(R.id.btn_logout)
    Button btn_logout;
    @BindView(R.id.lc_download_history)
    LineChart mChart;
    @BindView(R.id.progressBar_chart)
    ProgressBar progressBar_chart;
    @BindView(R.id.fr_chart_and_pager)
    FrameLayout fr_chart_and_pager;
    @BindView(R.id.recycler_view_cmt)
    RecyclerView recycler_view_cmt;
    @BindView(R.id.recycler_view_your_app)
    RecyclerView recycler_view_your_app;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    UserProfile userProfile;
    SnapHelper snapHelperCmt = new LinearSnapHelper();
    RecyclerViewReplyCmtAdapter cmtAdapter;

    HomeRecyclerViewAdapter listapp;
    @BindView(R.id.img_avar)
    ImageView avar;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_user_gmail)
    TextView tv_user_gmail;

    List<Entry> entries = new ArrayList<Entry>();
    ArrayList<ArrayList<String>> downloadAnalysis = null;
    ArrayList<ArrayList<String>> priceHistory_trans_date = null;
    protected Typeface mTfLight;

    @Override
    protected int getContentView() {
        return R.layout.act_profile;
    }

    @Override
    protected void initView() {

        getPresenter().getUserData();
        btn_logout.setOnClickListener(this);
        setUpAdapter();
        getPresenter().setChartData();
        getPresenter().getUserApk();
    }

    @Override
    protected Class<? extends ActivityPresenter> onRegisterPresenter() {
        return ProfilePresenter.class;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                break;
        }
    }


    @Override
    public void setAppDownload() {

        mChart.setViewPortOffsets(0, 0, 0, 0);
        mChart.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        mChart.setMaxHighlightDistance(300);
        XAxis x = mChart.getXAxis();
        x.setEnabled(false);

        YAxis y = mChart.getAxisLeft();
        y.setLabelCount(6, false);
        y.setTextColor(Color.BLUE);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.CYAN);
        y.setGridColor(Color.CYAN);

        mChart.getAxisRight().setEnabled(false);
        setData(45, 100);

        mChart.getLegend().setEnabled(false);

        mChart.animateXY(2000, 2000);

        // dont forget to refresh the drawing
        mChart.invalidate();
    }



    public void setUpAdapter() {
        SimpleDividerItemDecoration simpleDividerItemDecoration = new SimpleDividerItemDecoration(this, ContextCompat.getColor(this, R.color.gray_line), 120, 20);
        simpleDividerItemDecoration.setHasLastLine(false);

        snapHelperCmt.attachToRecyclerView(recycler_view_cmt);
        cmtAdapter = new RecyclerViewReplyCmtAdapter(this);
        recycler_view_cmt.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler_view_cmt.setAdapter(cmtAdapter);

        listapp = new HomeRecyclerViewAdapter(this);
        listapp.setItemId(R.layout.item_app_horizontal_white_bg);
        recycler_view_your_app.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.HORIZONTAL, false));
        recycler_view_your_app.setAdapter(listapp);
        recycler_view_your_app.addItemDecoration(simpleDividerItemDecoration);
    }

    private void setData(int count, float range) {

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult) + 20;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals.add(new Entry(i, val));
        }

        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            //set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(Color.WHITE);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(Color.BLUE);
            set1.setFillColor(Color.WHITE);
            set1.setFillAlpha(100);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return -10;
                }
            });

            // create a data object with the datasets
            LineData data = new LineData(set1);
            data.setValueTypeface(mTfLight);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            mChart.setData(data);
        }
    }

    @Override
    public void setUserData(UserProfile user) {
        ImageUtil.loadImage(this, user.getPhoto(), avar);
        tv_user_name.setText(user.getName());
        tv_user_gmail.setText(user.getEmail());
    }

    @Override
    public void loadChildUserUpload(List<AppModel> listApk) {
        listapp.setmList1(listApk);
        progressBar.setVisibility(View.GONE);
    }
}
