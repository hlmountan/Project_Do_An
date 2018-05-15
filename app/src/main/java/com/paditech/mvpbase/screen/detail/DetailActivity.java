package com.paditech.mvpbase.screen.detail;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.Html;
import android.transition.Explode;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.gravity.IChildGravityResolver;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.base.BaseDialog;
import com.paditech.mvpbase.common.dialog.MessageDialog;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.model.Cmt;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.mvp.activity.MVPActivity;
import com.paditech.mvpbase.common.utils.CommonUtil;
import com.paditech.mvpbase.common.utils.ImageUtil;
import com.paditech.mvpbase.common.view.FadeToolbarScrollView;
import com.paditech.mvpbase.common.view.SimpleDividerItemDecoration;
import com.paditech.mvpbase.screen.adapter.RecyclerViewCmtAdapter;
import com.paditech.mvpbase.screen.adapter.RecyclerViewScreenShortAdapter;
import com.paditech.mvpbase.screen.adapter.RecyclerViewVersionAdapter;
import com.paditech.mvpbase.screen.cmt.CommentActivity;
import com.paditech.mvpbase.screen.dev.DevActivity;
import com.paditech.mvpbase.screen.home.HomeRecyclerViewAdapter;
import com.paditech.mvpbase.screen.home.StartSnapHelper;
import com.paditech.mvpbase.screen.main.adapter.ChipCateAdapter;
import com.paditech.mvpbase.screen.report.ReportActivity;
import com.paditech.mvpbase.screen.user.UserActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;


/**
 * Created by hung on 1/5/2018.
 */

public class DetailActivity extends MVPActivity<DetailContact.PresenterViewOps> implements DetailContact.ViewOps,
        View.OnClickListener, FadeToolbarScrollView.ObservableScrollViewCallbacks, RatingBar.OnRatingBarChangeListener,
        View.OnFocusChangeListener {
    boolean isInstall= false;
    List<Entry> entries = new ArrayList<Entry>();
    private ArrayList<ArrayList<String>> screenShot = null;
    private ArrayList<ArrayList<String>> listApp;
    int watch = 0;
    ProgressDialog mProgressDialog;
    File file;
    final int IMGCMT = 2;
    ArrayList<ArrayList<String>> priceHistory = null;
    ArrayList<ArrayList<String>> priceHistory_trans_date = null;
    HomeRecyclerViewAdapter mHomeListAppAdapter = new HomeRecyclerViewAdapter(this);
    RecyclerViewVersionAdapter mListVersionAdapter = new RecyclerViewVersionAdapter(this);
    DetailViewPagerAdapter mDetailViewPagerAdapter;
    ChipCateAdapter mListCateAdapte;
    private ArrayList<String> mList = new ArrayList<>();
    String title;
    String appid;
    ImageView imgCover;
    boolean showdes = false;
    public int des_lines;
    private String urlInstall;
    private boolean animing;
    SnapHelper snapHelper = new StartSnapHelper();
    SnapHelper snapHelper1 = new StartSnapHelper();
    SnapHelper snapHelper3 = new LinearSnapHelper();
    SnapHelper snapHelperCmt = new LinearSnapHelper();
    Integer isHistory;
    AppModel.SourceBean ownApp;
    long fileLength;

    @BindView(R.id.et_title)
    TextView et_title;
    @BindView(R.id.view_rating)
    LinearLayout view_rating;
    @BindView(R.id.tv_rating_success)
    TextView tv_rating_success;
    @BindView(R.id.et_cmt)
    EditText et_cmt;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.btn_user_name)
    Button btn_user_name;
    @BindView(R.id.recycler_view_relate_app)
    RecyclerView recycler_view_relate_app;
    @BindView(R.id.tv_title)
    TextView textView_title;
    @BindView(R.id.tv_offerby)
    TextView tv_offerby;
    @BindView(R.id.tv_version)
    TextView tv_version;
    @BindView(R.id.tv_description)
    TextView tv_description;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.btn_install_app)
    Button btn_install_app;
    @BindView(R.id.lc_price_history)
    LineChart chart;
    @BindView(R.id.gp_info)
    TextView tv_gp_info;
    private boolean checkPriceHistory = true;
    @BindView(R.id.progressBar_chart)
    ProgressBar progressBar_chart;
    @BindView(R.id.img_avar)
    ImageView imgAvatar;
    @BindView(R.id.card_image)
    View avatarLayout;
    @BindView(R.id.recycler_view_versions)
    RecyclerView recycler_view_versions;
    @BindView(R.id.recycler_view_list_cate)
    RecyclerView recycler_view_list_cate;
    @BindView(R.id.btn_share)
    Button btn_share;
    @BindView(R.id.fr_chart_and_pager)
    FrameLayout fr_chart_and_pager;
    @BindView(R.id.btn_add)
    Button btn_add;
    @BindView(R.id.tv_title_scroll)
    TextView tv_title_scroll;
    @BindView(R.id.scrollView)
    FadeToolbarScrollView scrollView;
    View infoLayout;
    @BindView(R.id.toolbar)
    View titleLayout;
    @BindView(R.id.recycler_view_screenshoot)
    RecyclerView recycler_view_screenshoot;
    @BindView(R.id.tv_score)
    TextView tv_score;
    @BindView(R.id.tv_numberrate)
    TextView tv_numberrate;
    @BindView(R.id.tv_category)
    TextView tv_category;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.ratingbar_your)
    RatingBar ratingbar_your;
    @BindView(R.id.recycler_view_cmt)
    RecyclerView recycler_view_cmt;
    @BindView(R.id.btn_report)
    Button btn_report;
    @BindView(R.id.tv_show_cmt)
    TextView tv_show_cmt;
    @BindView(R.id.tv_score_your)
    TextView tv_score_your;
    @BindView(R.id.btn_see_more_version)
    Button btn_see_more_version;
    @BindView(R.id.btn_see_more_app_relate)
    Button btn_see_more_app_relate;
    @BindView(R.id.rlt_dev)
    RelativeLayout rlt_dev;

    RecyclerViewScreenShortAdapter mRecyclerViewScreenShortAdapter;
    RecyclerViewCmtAdapter mRecyclerViewCmtAdapter;

    public ArrayList<ArrayList<String>> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(ArrayList<ArrayList<String>> priceHistory) {
        this.priceHistory = priceHistory;
    }

    public ArrayList<ArrayList<String>> getListApp() {
        return listApp;
    }

    public void setListApp(ArrayList<ArrayList<String>> listApp) {
        this.listApp = listApp;
    }

    @Override
    protected int getContentView() {
        return R.layout.act_detail_new;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        // use to do transition throw each screen
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Explode());
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        avatarLayout.setTransitionName("image_avatar");

        // install app apk
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected Class<? extends ActivityPresenter> onRegisterPresenter() {
        return DetailPresenter.class;
    }

    @Override
    protected void onRestart() {
        progressBar_chart.setVisibility(View.GONE);
        super.onRestart();
    }

    @Override
    protected void initView() {
        // be called  when click on viewpager
        SimpleDividerItemDecoration simpleDividerItemDecoration = new SimpleDividerItemDecoration(this, ContextCompat.getColor(this, R.color.gray_line), 120, 20);
        simpleDividerItemDecoration.setHasLastLine(false);

        snapHelper1.attachToRecyclerView(recycler_view_relate_app);


        mHomeListAppAdapter.setItemId(R.layout.item_related_app);
        recycler_view_relate_app.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false));
        recycler_view_relate_app.setAdapter(mHomeListAppAdapter);
        recycler_view_relate_app.addItemDecoration(simpleDividerItemDecoration);

        //btn  way back to the previous screen

        // when click on text description this text will show all it have
        tv_description.setOnClickListener(this);
        // download apk file
        btn_install_app.setOnClickListener(this);
        //setup fade when scrolling ScrollView
//        scrollView.setScrollViewCallbacks(this);
        tv_offerby.setOnClickListener(this);

// set on click scroll to rate
        ratingbar.setOnClickListener(this);
        // set on click show form report an app
        btn_report.setOnClickListener(this);


        rlt_dev.setOnClickListener(this);
        et_cmt.setOnFocusChangeListener(this);
        btn_see_more_app_relate.setOnClickListener(this);
        tv_show_cmt.setOnClickListener(this);
        tv_score.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_share.setOnClickListener(this);
        btn_see_more_version.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        et_title.setOnFocusChangeListener(this);

        ratingbar_your.setOnRatingBarChangeListener(this);

        snapHelper.attachToRecyclerView(recycler_view_versions);
        mListVersionAdapter.setItemNumber(10);
        mListVersionAdapter.setItemId(R.layout.item_version);
        recycler_view_versions.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler_view_versions.setAdapter(mListVersionAdapter);


        snapHelperCmt.attachToRecyclerView(recycler_view_cmt);
        mRecyclerViewCmtAdapter = new RecyclerViewCmtAdapter(this);
        recycler_view_cmt.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler_view_cmt.setAdapter(mRecyclerViewCmtAdapter);

    }

    private void downloadApkFile(String url) {

        mProgressDialog = new ProgressDialog(DetailActivity.this);
        mProgressDialog.setMessage("Download thread are running . . .");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

        //check permission to access external storage
        if (ContextCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // guide to allow app permission to access the external storage or ask the opinion if this is the first time
            if (!ActivityCompat.shouldShowRequestPermissionRationale(DetailActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(DetailActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        100);
            } else {
                // show dialog yeu cau vao setting cua app de cho phep quyen
                MessageDialog messageDialog = MessageDialog.newInstance(false, getString(R.string.mess_permission_storage),
                        getString(R.string.ok), getString(R.string.cancel));
                messageDialog.setmOnPositiveClickListener(new BaseDialog.OnPositiveClickListener() {
                    @Override
                    public void onPositiveClick() {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, 0);
                    }
                });
                messageDialog.show(getSupportFragmentManager(), messageDialog.getClass().getSimpleName());
            }
            return;
        }

        // execute this when the downloader must be fired
        final DownloadTask downloadTask = new DownloadTask(DetailActivity.this);
        downloadTask.setFileLength(fileLength);
        downloadTask.execute(url);

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask.cancel(true);
                String path = Environment.getExternalStorageDirectory().toString() + "/com.lenam.appstore/" + appid + ".apk";
                System.out.println(path + " path cancel ");
                Log.d("Files", "Path: " + path);
                File directory = new File(path);
                boolean d0 = directory.delete();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetAppEvent(AppModel.SourceBean app) {
        ownApp = app;
        title = app.getTitle();
        textView_title.setText(app.getTitle());
        appid = app.getAppid();
        isInstall = getPresenter().isInstall(appid);
    // follow app
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            getPresenter().getUserFollowApp();
        }


        if (!getIntent().getBooleanExtra("is_cover", false))
            ImageUtil.loadImage(DetailActivity.this, app.getCover(), imgAvatar, R.drawable.events_placeholder, R.drawable.image_placeholder_500x500);
        // visible in cmt box
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            btn_user_name.setText(R.string.anonymous);
        else btn_user_name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        //
        if (app.isUserUpload()) {
            tv_offerby.setText(app.getOfferby());
            setUpScreenShort(app.getScreenshotUserUpload());
            fr_chart_and_pager.setVisibility(View.GONE);
            btn_install_app.setText(R.string.install);

        } else {
            if ((app.getAll_price() == null) || (app.getAll_price().size() != 2)) {

                isHistory = 0;
                getPresenter().cURLFromApi(app.getAppid(), 0);
            } else {
                isHistory = 1;

                fr_chart_and_pager.setVisibility(View.VISIBLE);
                getPresenter().cURLFromApi(app.getAppid(), 1);
            }

        }
        getPresenter().getUserCmt(appid,app.isFirebaseCmt());
        getPresenter().getRelateApp("http://appsxyz.com/api/apk/search_related/?q=" + URLEncoder.encode(app.getTitle()) + "&page=1&size=20");

    }


    private void setUpScreenShort(ArrayList<String> screenShot) {

        snapHelper3.attachToRecyclerView(recycler_view_screenshoot);
        mRecyclerViewScreenShortAdapter = new RecyclerViewScreenShortAdapter(this);
        mRecyclerViewScreenShortAdapter.setmList(screenShot);
        recycler_view_screenshoot.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler_view_screenshoot.setAdapter(mRecyclerViewScreenShortAdapter);
    }

    // ham count text line  su dung de showmore description
    private void linesCount(String s) {
        int count = 0;
        int position = 0;
        while ((position = s.indexOf("<br>", position)) != -1) {
            count++;
            position++; // Skip this occurrence!
        }
        if (count != 0)
            des_lines = count;
        else
            des_lines = 3;
    }

    private void prepareData() {
        Integer count = 0;
        for (int i = priceHistory.size() - 1; i >= 0; i--) {
            count++;
            Float pri = Float.parseFloat(priceHistory.get(i).get(0));
            entries.add(new Entry(count, pri));
        }
    }

    private void lineChartData() {

        prepareData();
//        chart.setDragEnabled(true);
//        chart.setScaleEnabled(true);
//        chart.setTouchEnabled(true);
//        chart.setScaleEnabled(true);
//        chart.setHighlightPerDragEnabled(true);
//        chart.setPinchZoom(true);

//        chart.setViewPortOffsets(0, 0, 0, 0);
        chart.setVisibleXRangeMaximum(20);
        chart.setBackgroundColor(Color.WHITE);
        chart.setDrawGridBackground(false);
//        chart.setMaxHighlightDistance(300);
        YAxis y = chart.getAxisLeft();
        y.setLabelCount(6, false);
        y.setTextColor(Color.BLUE);
        y.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        y.setAxisLineColor(Color.BLUE);

        chart.getAxisRight().setEnabled(false);

        DateAxisValueFormatter formatter = new DateAxisValueFormatter(priceHistory);
        XAxis x = chart.getXAxis();
        x.setLabelCount(4, false);
        x.setTextColor(Color.BLUE);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
        x.setAxisLineColor(Color.BLUE);
        x.setValueFormatter(formatter);
        // set maker view

        MyMakerView myMakerView = new MyMakerView(this, R.layout.custom_maker_view, priceHistory);

        myMakerView.setChartView(chart);
        chart.setMarker(myMakerView);


        LineDataSet dataSet;


        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            dataSet = (LineDataSet) chart.getData().getDataSetByIndex(0);
            dataSet.setValues(entries);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            dataSet = new LineDataSet(entries, "Google Play");

            dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            dataSet.setCubicIntensity(0.2f);
            dataSet.setLineWidth(1.8f);
            dataSet.setCircleRadius(4f);
//        dataSet.setCircleColor(Color.WHITE);
//            dataSet.setHighLightColor(Color.rgb(244, 117, 117));
            dataSet.setColor(Color.BLUE);
//            dataSet.setFillColor(Color.BLUE);
//            dataSet.setFillAlpha(100);
//            dataSet.setDrawFilled(true);


            dataSet.setFormLineWidth(1f);
            dataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            dataSet.setFormSize(15.f);


//            dataSet.setDrawHorizontalHighlightIndicator(false);
//            dataSet.setFillFormatter(new IFillFormatter() {
//                @Override
//                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
//                    return -10;
//                }
//            });


            LineData lineData = new LineData(dataSet);
//            lineData.setValueTextSize(9f);
//            lineData.setDrawValues(false);
            chart.setData(lineData);

        }


        chart.getLegend().setEnabled(false);
        chart.animateXY(2000, 3000);
        chart.invalidate();

    }

    @Override
    public void setAppInfo(final AppModel.SourceBean app) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (isHistory != 0)
                        btn_install_app.setText("$" + app.getPrice());
                    else {
                        if (isInstall) {
                            btn_install_app.setText(R.string.open_app);
                        } else
                            btn_install_app.setText("" + getResources().getString(R.string.install));
                        setUrlInstall(app);
                    }

                    setUpCateAdapter(app.getTag());
                    fileLength = app.getSize()*1000;
                    if (app.getTitle() != null) tv_title_scroll.setText(app.getTitle());


                    tv_score.setText(String.valueOf(app.getScore()));
                    tv_numberrate.setText(NumberFormat.getInstance().format(app.getInstalls()) + " Rating");
                    tv_offerby.setText(app.getOfferby());
                    tv_category.setText(app.getCategory());
                    ratingbar.setRating(app.getScore());


                    tv_version.setText("Version: " + app.getVersion());
                    String des = app.getDescription();
                    linesCount(des);
                    String dev;
                    if (app.getOfferby().length() > 13){
                        dev = app.getOfferby().substring(0,13);
                    }else{
                         dev = app.getOfferby();
                    }


                    tv_gp_info.setText(dev + "..\n" + NumberFormat.getInstance().format(app.getSize()/1000000)+ " MB" + "\n" + app.getCategory() + "\n" + app.getRequire() + "\n" + app.getContentrating());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        tv_description.setText(Html.fromHtml(des, Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        tv_description.setText(Html.fromHtml(des));
                    }
                    //set up screenshot data
                    screenShot = app.getScreenShot();
                    if (app.getScreenShot() != null) {
                        for (ArrayList<String> a : app.getScreenShot()) {
                            ;
                            mList.add(a.get(2));
                        }
                        setUpScreenShort(mList);
                    }


                    //set title for download api
                    title = app.getAppid().replace(" ", "_").replace(".", "_");
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });

    }

    @Override
    public void setAppPriceHistory(final ArrayList<ArrayList<String>> priceHistory) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setPriceHistory(priceHistory);
//                System.out.println(this.priceHistory.size()+"size");
                lineChartData();
                chart.setVisibility(View.VISIBLE);
                progressBar_chart.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void setRelateApp(final List<AppModel> app) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mHomeListAppAdapter.setmList1(app);
                progressBar.setVisibility(View.GONE);
            }
        });

    }


    @Override
    public void setDevApp() {

    }

    @Override
    public void setUrlDownload(String url) {
        this.urlInstall = url;
        System.out.println(" url day " + this.urlInstall);
    }

    @Override
    public void setFollowApp(ArrayList<ArrayList<String>> listApp) {
        this.listApp = listApp;
        btn_add.setVisibility(View.VISIBLE);
        btn_share.setVisibility(View.VISIBLE);
        if (this.listApp != null) {
            for (ArrayList<String> a : listApp) {
                if (a.get(0).equals(ownApp.getAppid())) {
                    btn_add.setBackgroundResource(R.drawable.ic_check);
                    watch = 1;
                }
            }
        }

    }

    @Override
    public void setCmt(final List<Cmt> cmts) {

        mRecyclerViewCmtAdapter.setCmt(cmts);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getPresenter().updateFollowApp(listApp);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_description:
                final int currSize = des_lines - 3;
                Animation animation;
                if (showdes) {
                    animation = new Animation() {
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            super.applyTransformation(interpolatedTime, t);
                            tv_description.setLines((int) (currSize * (1 - interpolatedTime)) + 3);
                            showdes = false;
                            tv_description.requestLayout();
                        }
                    };
                } else {
                    animation = new Animation() {
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            super.applyTransformation(interpolatedTime, t);
                            tv_description.setLines((int) (currSize * (interpolatedTime)) + 3);
                            showdes = true;
                            tv_description.requestLayout();
                        }
                    };
                }

                animation.setDuration(300);
                animation.setInterpolator(new DecelerateInterpolator());
                tv_description.clearAnimation();
                tv_description.startAnimation(animation);
                break;
            case R.id.btn_install_app:
                // check xem app co gia hay free
                if (isHistory == 0) {
                    // heck xm trong may da co apk cua ap chua
//                    if (getPresenter().checkIsApk(appid)){
//                        //co roi thi chi install
//                    }else{
                    // chua co thi tai va install
                    System.out.println(urlInstall);
                    downloadApkFile(this.urlInstall);
//                    }

                } else {
                    Log.e("", "You need buy App first");
                }
                break;
            case R.id.btn_add:

                if (watch == 0) {
                    btn_add.setBackgroundResource(R.drawable.ic_check);
                    ArrayList<String> follow = new ArrayList<>();
                    follow.add(ownApp.getAppid());
                    follow.add(ownApp.getCover());
                    follow.add(ownApp.getTitle());
                    follow.add(String.valueOf(ownApp.isUserUpload()));

                    listApp.add(follow);
                    watch = 1;
                } else {
                    btn_add.setBackgroundResource(R.drawable.btn_add);
                    for (ArrayList<String> a : listApp) {
                        if (a.get(0).equals(ownApp.getAppid())) {
                            listApp.remove(a);
                            break;
                        }
                    }
                    watch = 0;
                }

                break;
            case R.id.btn_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Your body here";
                String shareSub = "Your subject here";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                break;
            case R.id.tv_score:
                recycler_view_cmt.getParent().requestChildFocus(recycler_view_cmt, recycler_view_cmt);
                break;
            case R.id.btn_report:
                final Intent intent = new Intent(this, ReportActivity.class);
                this.startActivity(intent);
                break;
            case R.id.tv_offerby:
                System.out.println(title + " asdwcsf ");
                Intent intent1 = new Intent(this, DevActivity.class);
//                intent1.putExtra("DEVNAME", title);
                this.startActivity(intent1);
                break;
            case R.id.rlt_dev:
                Intent intent2 = new Intent(this, DevActivity.class);
//                intent2.putExtra("DEVNAME", title);
                this.startActivity(intent2);
                break;

            case R.id.tv_show_cmt:
                this.startActivity(new Intent(this, CommentActivity.class));
                break;
            case R.id.btn_submit:
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    if (isInstall){
                        if (ratingbar_your.getRating() != 0 && !et_cmt.getText().toString().equals("")&&!et_title.getText().toString().equals("")) {
                            // push cmt
                            Cmt cmt = new Cmt();
                            cmt.setAppid(appid);
                            cmt.setAuthorName(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                            cmt.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            cmt.setContent(et_cmt.getText().toString());
                            cmt.setRate(ratingbar_your.getRating());
                            cmt.setDate(System.currentTimeMillis()/1000);
                            cmt.setTitle(et_title.getText().toString());
                            cmt.setAvar(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString());
                            getPresenter().pushCmt(cmt);

                            showAlertDialog(getString(R.string.cmt_success));
                            view_rating.setVisibility(View.GONE);
                            tv_rating_success.setVisibility(View.VISIBLE);

                        } else showAlertDialog(getString(R.string.empty_context_cmt));
                    }else showAlertDialog(getString(R.string.install_condition));


                } else {
                    showConfirmDialog(getString(R.string.cmt_login_alert), new BaseDialog.OnPositiveClickListener() {
                        @Override
                        public void onPositiveClick() {
                            Intent i = new Intent(btn_submit.getContext(), UserActivity.class);
                            i.putExtra("SCREEN", "DETAIL");
                            btn_submit.getContext().startActivity(i);
                        }
                    }, null);
                }
                break;

        }
    }

    @Override
    public void showAlertDialog(String msg) {
        super.showAlertDialog(msg);
    }

    @Override
    public void showConfirmDialog(String msg, BaseDialog.OnPositiveClickListener positiveListener, BaseDialog.OnNegativeClickListener negativeListener) {
        super.showConfirmDialog(msg, positiveListener, negativeListener);
    }

    // fadetoolbarscrollview
    @Override
    public void onScrollChanged(int scrollY, int oldScollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.colorPrimary);
        float alpha = Math.min(1, (float) scrollY / infoLayout.getMeasuredHeight());
        int color = CommonUtil.getColorWithAlpha(alpha, baseColor);
        int color_2 = CommonUtil.getColorWithAlpha(1 - alpha, baseColor);
        titleLayout.setBackgroundColor(color);
        //infoLayout.setTranslationY(scrollY / 2);
        tv_title_scroll.setAlpha(alpha);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(FadeToolbarScrollView.ScrollState scrollState) {

    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        tv_score_your.setTextColor(getResources().getColor(R.color.orange));
        tv_score_your.setText(String.valueOf(v) + " Score");
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (!b) {
            hideKeyboard(view);
        }
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;
        private long fileLength;

        public long getFileLength() {
            return fileLength;
        }

        public void setFileLength(long fileLength) {
            this.fileLength = fileLength;
        }

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setInstanceFollowRedirects(true);
                HttpURLConnection.setFollowRedirects(true);
                connection.connect();
                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length cái getcontentlenght

//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                    fileLength = connection.getContentLengthLong();
//                } else fileLength = connection.getContentLength();


                // download the file
                File folder = new File(Environment.getExternalStorageDirectory(), context.getPackageName());
                System.out.println(folder.getName() + " name");
                if (!folder.exists() && !folder.mkdirs()) {
                    return "";
                }

                file = new File(folder, title + ".apk");
                input = connection.getInputStream();
                output = new FileOutputStream(file);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    System.out.println(total);
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / this.fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            // đây là hàm khi chạy xong luôn download, nen m install ngay cho nay
            // phia tren install the la no chua chay xong luong download
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null) {
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
                installApk();
            }
            // install apk when app download finish

        }
    }

    private void setUrlInstall(AppModel.SourceBean app) {

        if ((app != null) && (app.getPrice() == 0)) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String salt = "axq appnaz.com";
            String hasd = salt + timestamp.getTime();

            // create md5 code with the merging of salt and current timestamp
            final String MD5 = "MD5";
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance(MD5);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            md.update(hasd.getBytes(), 0, hasd.length());
            String newHasd = new BigInteger(1, md.digest()).toString(16);
            while (newHasd.length() < 32)
                newHasd = "0" + newHasd;
            String titleUrl = app.getTitle() == null ? "" : app.getTitle().replace(" ", "-");

            if (titleUrl.indexOf("(") >= 0) {
                String trash = "";
                trash = titleUrl.substring(titleUrl.indexOf("("), titleUrl.indexOf(")") + 1);
                titleUrl = titleUrl.replace(trash, "");
            }
            String fieldId = app.getAppid() + "_appnaz.com_" + app.getVersion();
            String field = "app=" + titleUrl + "&appid=" + fieldId + "&s=" + newHasd + "&t=" + timestamp.getTime();
            System.out.println(field);
            getPresenter().encodeDownloadUrl("http://choilieng.com/download?" + field);

        } else {
            this.urlInstall = "Tin Nguoi VCL!!!";
        }
    }

    private String getUrlInstall() {
        return this.urlInstall;
//       return "https://firebasestorage.googleapis.com/v0/b/fir-c26af.appspot.com/o/app-envReal-release.apk?alt=media&token=fce366de-fce6-4e84-9e8c-866ab2712211";
    }

    private void installApk() {
        try {
            Uri uri = Uri.fromFile(file);
            System.out.println(uri + " urlasasd ");
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            install.setDataAndType(uri,
                    "application/vnd.android.package-archive");
            startActivity(install);
            String path = Environment.getExternalStorageDirectory().toString() + "/com.lenam.appstore/" + appid + ".apk";
            System.out.println(path + " path");
            Log.d("Files", "Path: " + path);
            File directory = new File(path);
            boolean d0 = directory.delete();
            isInstall = true;
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void setUpCateAdapter(String tag) {

        String[] array = tag.split(",");
        mListCateAdapte = new ChipCateAdapter();
        mListCateAdapte.setmListener(new ChipCateAdapter.OnSelectCateListener() {

            @Override
            public void selectCate(String string) {
                //do somethings here

            }
        });
        ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(getActivityContext())
                //set vertical gravity for all items in a row. Default = Gravity.CENTER_VERTICAL
                .setChildGravity(Gravity.TOP)
                //whether RecyclerView can scroll. TRUE by default
                .setScrollingEnabled(true)
                //set maximum views count in a particular row
                .setMaxViewsInRow(4)
                //set gravity resolver where you can determine gravity for item in position.
                //This method have priority over previous one
                .setGravityResolver(new IChildGravityResolver() {
                    @Override
                    public int getItemGravity(int position) {
                        return Gravity.CENTER;
                    }
                })
                //a layoutOrientation of layout manager, could be VERTICAL OR HORIZONTAL. HORIZONTAL by default
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .build();

        mListCateAdapte.setmListCates(Arrays.asList(array));
        recycler_view_list_cate.setLayoutManager(chipsLayoutManager);
        recycler_view_list_cate.setAdapter(mListCateAdapte);


    }

    void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
