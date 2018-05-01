package com.paditech.mvpbase.screen.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.gravity.IChildGravityResolver;
import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.model.Appsxyz;
import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenter;
import com.paditech.mvpbase.common.mvp.fragment.MVPFragment;
import com.paditech.mvpbase.common.utils.CommonUtil;
import com.paditech.mvpbase.common.utils.crypto.Crypto;
import com.paditech.mvpbase.common.view.TranslationNestedScrollView;
import com.paditech.mvpbase.screen.main.ScrollTopEvent;
import com.paditech.mvpbase.screen.main.adapter.ChipCateAdapter;
import com.paditech.mvpbase.screen.showMoreApp.ShowMoreActicity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * Created by hung on 1/2/2018.
 */

public class HomeFragment extends MVPFragment<HomeContact.PresenterViewOsp> implements HomeContact.ViewOsp {

    HomeRecyclerViewAdapter mHomeRecyclerViewAdapter;
    HomeRecyclerViewAdapter mHomeRecyclerViewAdapter2;
    HomeRecyclerViewAdapter mHomeRecyclerViewAdapter3;
    HomeRecyclerViewAdapter mHomeRecyclerViewAdapter4;
    HomeRecyclerViewAdapter mHomeRecyclerViewAdapter5;
    HomeViewPagerAdapter mHomeViewPagerAdapter;
    private ChipCateAdapter mChipCateAdapter;
    @BindView(R.id.recycler_view_recentely)
    RecyclerView recycler_view_recentely;
    @BindView(R.id.recycler_view_on_sale)
    RecyclerView recycler_view_on_sale;
    @BindView(R.id.btn_see_more)
    Button btn_see_more;
    @BindView(R.id.vp_main)
    ViewPager vp_main;
    @BindView(R.id.recycler_view_top_download)
    RecyclerView recycler_view_top_download;
    @BindView(R.id.recycler_view_topic)
    RecyclerView recycler_view_topic;
    @BindView(R.id.recycler_view_grossing)
    RecyclerView recycler_view_grossing;
    @BindView(R.id.scrollView_home)
    TranslationNestedScrollView scrollView_home;
    @BindView(R.id.recycler_view_list_cate)
    RecyclerView recycler_view_list_cate;
    private boolean mRunned;

    SnapHelper snapHelper = new StartSnapHelper();
    SnapHelper snapHelper1 = new StartSnapHelper();
    SnapHelper snapHelper2 = new StartSnapHelper();
    SnapHelper snapHelper3 = new StartSnapHelper();
    SnapHelper snapHelper4 = new StartSnapHelper();
    Activity act;
    private GridLayoutManager gridLayoutManager;


    public static HomeFragment getInstance(Activity act) {
        HomeFragment f = new HomeFragment();
        f.setAct(act);
        return f;

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
        scrollView_home.smoothScrollTo(0,0);
        scrollView_home.scrollTo(0,0);

    }

    public Activity getAct() {
        return act;
    }

    public void setAct(Activity act) {
        this.act = act;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        if (!mRunned) {
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);
        }
        mRunned = true;
        setUpRecyclerView();
        setRecyclerViewCategory();
        getPresenter().getAppFromApi();
        setUpViewPager();
        btn_see_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_see_more.getContext().startActivity(new Intent(btn_see_more.getContext(), ShowMoreActicity.class));
            }
        });
        
    }

    private void setUpRecyclerView(){
        scrollView_home.setViewPager(vp_main,CommonUtil.getWidthScreen(getActivityReference())/2);
        snapHelper.attachToRecyclerView(recycler_view_recentely);
        snapHelper1.attachToRecyclerView(recycler_view_grossing);
        snapHelper2.attachToRecyclerView(recycler_view_on_sale);
        snapHelper3.attachToRecyclerView(recycler_view_top_download);
        snapHelper3.attachToRecyclerView(recycler_view_topic);

        mHomeRecyclerViewAdapter = new HomeRecyclerViewAdapter(act);
        mHomeRecyclerViewAdapter2 = new HomeRecyclerViewAdapter(act);
        mHomeRecyclerViewAdapter3 = new HomeRecyclerViewAdapter(act);
        mHomeRecyclerViewAdapter4 = new HomeRecyclerViewAdapter(act);
        mHomeRecyclerViewAdapter5 = new HomeRecyclerViewAdapter(act);

        mHomeRecyclerViewAdapter.setItemId(R.layout.item_app_small);
        mHomeRecyclerViewAdapter.setItemNumber(12);
        gridLayoutManager = new GridLayoutManager(act, 2, LinearLayoutManager.HORIZONTAL, false);
        recycler_view_recentely.setLayoutManager(gridLayoutManager);
        recycler_view_recentely.setAdapter(mHomeRecyclerViewAdapter);
//        recycler_view_recentely.setNestedScrollingEnabled(false);


        mHomeRecyclerViewAdapter2.setItemNumber(6);
        mHomeRecyclerViewAdapter2.setItemId(R.layout.item_app);
        recycler_view_on_sale.setLayoutManager(new LinearLayoutManager(act, LinearLayoutManager.HORIZONTAL, false));
        recycler_view_on_sale.setAdapter(mHomeRecyclerViewAdapter2);
//        recycler_view_on_sale.setNestedScrollingEnabled(false);
//

        mHomeRecyclerViewAdapter3.setItemNumber(12);
        mHomeRecyclerViewAdapter3.setItemId(R.layout.item_app_download);
        recycler_view_grossing.setLayoutManager(new GridLayoutManager(act, 2, LinearLayoutManager.HORIZONTAL, false));
        recycler_view_grossing.setAdapter(mHomeRecyclerViewAdapter3);
//        recycler_view_grossing.setNestedScrollingEnabled(false);



        mHomeRecyclerViewAdapter4.setItemId(20);
        mHomeRecyclerViewAdapter4.setItemId(R.layout.item_app_download);
        recycler_view_top_download.setLayoutManager(new GridLayoutManager(act, 2, LinearLayoutManager.HORIZONTAL, false));
        recycler_view_top_download.setAdapter(mHomeRecyclerViewAdapter4);

        mHomeRecyclerViewAdapter5.setItemNumber(2);
        mHomeRecyclerViewAdapter5.setItemId(R.layout.item_app_haft_screen);
        recycler_view_topic.setLayoutManager(new LinearLayoutManager(act, LinearLayoutManager.HORIZONTAL, false));
        recycler_view_topic.setAdapter(mHomeRecyclerViewAdapter5);
    }
    public class MyTimerTask extends TimerTask {

        @Override

        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (vp_main.getCurrentItem() != (2))
                            vp_main.setCurrentItem(vp_main.getCurrentItem() + 1);
                        else
                            vp_main.setCurrentItem(0);
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                }
            });

        }
    }
    @Override
    public void loadSlider() {

    }

    @Override
    public void loadChild1(final List<AppModel> result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mHomeRecyclerViewAdapter.setmList1(result);
            }
        });
    }

    @Override
    public void loadChild2(final List<AppModel> result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mHomeRecyclerViewAdapter2.setmList1(result);
            }
        });
    }

    @Override
    public void loadChild3(final List<AppModel> result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mHomeRecyclerViewAdapter3.setmList1(result);
            }
        });
    }

    @Override
    public void loadChild4(final List<AppModel> result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mHomeRecyclerViewAdapter4.setmList1(result);
            }
        });
    }

    @Override
    public void loadChild5(List<AppModel> result) {
        mHomeRecyclerViewAdapter5.setmList1(result);
    }


    @Override
    public void reloadSlider() {

    }

    @Override
    public void reloadListApp() {

    }

    @Override
    public void updateListCates(final List<String> strings) {
                mChipCateAdapter.setmListCates(strings);
    }


    @Override
    protected Class<? extends FragmentPresenter> onRegisterPresenter() {
        return HomePresenter.class;
    }


    private void setUpViewPager() {
        mHomeViewPagerAdapter = new HomeViewPagerAdapter();
        vp_main.setAdapter(mHomeViewPagerAdapter);
    }
    private void setRecyclerViewCategory() {
        mChipCateAdapter = new ChipCateAdapter();
        mChipCateAdapter.setmListener(new ChipCateAdapter.OnSelectCateListener() {
            @Override
            public void selectCate(String string) {
                EventBus.getDefault().post(string);
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
        recycler_view_list_cate.setLayoutManager(chipsLayoutManager);
        recycler_view_list_cate.setAdapter(mChipCateAdapter);
        recycler_view_list_cate.setNestedScrollingEnabled(false);
        getPresenter().getListCates();
    }
}
