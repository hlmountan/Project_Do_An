package com.paditech.mvpbase.screen.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.gravity.IChildGravityResolver;
import com.google.firebase.auth.FirebaseAuth;
import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.event.ChipCateTagEvent;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenter;
import com.paditech.mvpbase.common.mvp.fragment.MVPFragment;
import com.paditech.mvpbase.common.utils.CommonUtil;
import com.paditech.mvpbase.common.view.TranslationNestedScrollView;
import com.paditech.mvpbase.screen.adapter.RecyclerViewSliderHome;
import com.paditech.mvpbase.screen.main.HomeActivity;
import com.paditech.mvpbase.screen.main.ScrollTopEvent;
import com.paditech.mvpbase.screen.main.adapter.ChipCateAdapter;
import com.paditech.mvpbase.screen.profile.ProfileActivity;
import com.paditech.mvpbase.screen.showMoreApp.ShowMoreActicity;
import com.paditech.mvpbase.screen.user.UserActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * Created by hung on 1/2/2018.
 */

public class HomeFragment extends MVPFragment<HomeContact.PresenterViewOsp> implements HomeContact.ViewOsp,View.OnClickListener {

    HomeRecyclerViewAdapter mHomeRecyclerViewAdapterOnSale;
    HomeRecyclerViewAdapter mHomeRecyclerViewAdapterGameGrossing;
    HomeRecyclerViewAdapter mHomeRecyclerViewAdapterAllGrossing;
    HomeRecyclerViewAdapter mHomeRecyclerViewAdapterUserUpload;
    HomeRecyclerViewAdapter mHomeRecyclerViewAdapter5;

    RecyclerViewSliderHome homeSlider;

    private ChipCateAdapter mChipCateAdapter;
    @BindView(R.id.recycler_view_app_onsale)
    RecyclerView recycler_view_app_onsale;
    @BindView(R.id.recycler_view_game_grossing)
    RecyclerView recycler_view_game_grossing;
    @BindView(R.id.btn_see_more)
    Button btn_see_more;
    @BindView(R.id.recycler_view_slider)
    RecyclerView recycler_view_slider;
    @BindView(R.id.recycler_view_user_upload)
    RecyclerView recycler_view_user_upload;
    @BindView(R.id.recycler_view_topic)
    RecyclerView recycler_view_topic;
    @BindView(R.id.recycler_view_grossing)
    RecyclerView recycler_view_grossing;
    @BindView(R.id.scrollView_home)
    TranslationNestedScrollView scrollView_home;
    @BindView(R.id.recycler_view_list_cate)
    RecyclerView recycler_view_list_cate;

    @BindView(R.id.btn_profile)
    Button btn_profile;
    @BindView(R.id.btn_notification)
    Button btn_notification;
    @BindView(R.id.view_user_upload)
    LinearLayout view_user_upload;
    private boolean mRunned;

    SnapHelper snapHelper = new StartSnapHelper();
    SnapHelper snapHelper1 = new StartSnapHelper();
    SnapHelper snapHelper2 = new StartSnapHelper();
    SnapHelper snapHelper3 = new StartSnapHelper();
    SnapHelper snapHelperSlider = new StartSnapHelper();


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
//        if (!mRunned) {
//            Timer timer = new Timer();
//            timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);
//        }

        mRunned = true;
        setUpRecyclerView();
        setRecyclerViewCategory();
        getPresenter().getAppFromApi();
        setUpViewPager();
        btn_notification.setOnClickListener(this);
        btn_profile.setOnClickListener(this);
        btn_see_more.setOnClickListener(this);
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            getPresenter().getUserApk();
        }else view_user_upload.setVisibility(View.GONE);

        
    }

    private void setUpRecyclerView(){
        scrollView_home.setViewPager(recycler_view_slider,CommonUtil.getWidthScreen(getActivityReference())/2);

        snapHelper.attachToRecyclerView(recycler_view_app_onsale);
        snapHelper1.attachToRecyclerView(recycler_view_grossing);
        snapHelper2.attachToRecyclerView(recycler_view_game_grossing);
        snapHelper3.attachToRecyclerView(recycler_view_user_upload);

        snapHelperSlider.attachToRecyclerView(recycler_view_slider);


        mHomeRecyclerViewAdapterOnSale = new HomeRecyclerViewAdapter(act);
        mHomeRecyclerViewAdapterGameGrossing = new HomeRecyclerViewAdapter(act);
        mHomeRecyclerViewAdapterAllGrossing = new HomeRecyclerViewAdapter(act);
        mHomeRecyclerViewAdapterUserUpload = new HomeRecyclerViewAdapter(act);
        mHomeRecyclerViewAdapter5 = new HomeRecyclerViewAdapter(act);

        mHomeRecyclerViewAdapterOnSale.setItemId(R.layout.item_app_horizontal_white_bg);
        mHomeRecyclerViewAdapterOnSale.setItemNumber(15);
        gridLayoutManager = new GridLayoutManager(act, 5, LinearLayoutManager.HORIZONTAL, false);
        recycler_view_app_onsale.setLayoutManager(gridLayoutManager);
        recycler_view_app_onsale.setAdapter(mHomeRecyclerViewAdapterOnSale);
//        recycler_view_app_onsale.setNestedScrollingEnabled(false);


        mHomeRecyclerViewAdapterGameGrossing.setItemNumber(6);
        mHomeRecyclerViewAdapterGameGrossing.setItemId(R.layout.item_app);
        recycler_view_game_grossing.setLayoutManager(new LinearLayoutManager(act, LinearLayoutManager.HORIZONTAL, false));
        recycler_view_game_grossing.setAdapter(mHomeRecyclerViewAdapterGameGrossing);
//        recycler_view_game_grossing.setNestedScrollingEnabled(false);
//

        mHomeRecyclerViewAdapterAllGrossing.setItemNumber(12);
        mHomeRecyclerViewAdapterAllGrossing.setItemId(R.layout.item_app_download);
        recycler_view_grossing.setLayoutManager(new GridLayoutManager(act, 2, LinearLayoutManager.HORIZONTAL, false));
        recycler_view_grossing.setAdapter(mHomeRecyclerViewAdapterAllGrossing);
//        recycler_view_grossing.setNestedScrollingEnabled(false);



        mHomeRecyclerViewAdapterUserUpload.setItemId(R.layout.item_app);
        recycler_view_user_upload.setLayoutManager(new LinearLayoutManager(act, LinearLayoutManager.HORIZONTAL, false));
        recycler_view_user_upload.setAdapter(mHomeRecyclerViewAdapterUserUpload);

        mHomeRecyclerViewAdapter5.setItemNumber(2);
        mHomeRecyclerViewAdapter5.setItemId(R.layout.item_app_haft_screen);
        recycler_view_topic.setLayoutManager(new LinearLayoutManager(act, LinearLayoutManager.HORIZONTAL, false));
        recycler_view_topic.setAdapter(mHomeRecyclerViewAdapter5);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_see_more:
                btn_see_more.getContext().startActivity(new Intent(btn_see_more.getContext(), ShowMoreActicity.class));
                break;
            case R.id.btn_profile:
                if ( FirebaseAuth.getInstance().getCurrentUser() == null){
                    Intent intent = new Intent(btn_profile.getContext(),UserActivity.class);
                    intent.putExtra("SCREEN","HOME");
                    btn_profile.getContext().startActivity(intent);
                } else {
                    // profile
                    btn_profile.getContext().startActivity(new Intent(btn_see_more.getContext(), ProfileActivity.class));
                }
                break;
            case R.id.btn_notification:
                btn_see_more.getContext().startActivity(new Intent(btn_see_more.getContext(), ShowMoreActicity.class));
                break;
        }
    }


    @Override
    public void loadSlider() {

    }

    @Override
    public void loadChildOnSale(final List<AppModel> result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mHomeRecyclerViewAdapterOnSale.setmList1(result);
            }
        });
    }

    @Override
    public void loadChildGameGrossing(final List<AppModel> result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mHomeRecyclerViewAdapterGameGrossing.setmList1(result);
            }
        });
    }

    @Override
    public void loadChildAllGrossing(final List<AppModel> result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mHomeRecyclerViewAdapterAllGrossing.setmList1(result);
            }
        });
    }

    @Override
    public void loadChildUserUpload(final List<AppModel> result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mHomeRecyclerViewAdapterUserUpload.setmList1(result);
                mHomeRecyclerViewAdapterUserUpload.setItemNumber(result.size());
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
        homeSlider = new RecyclerViewSliderHome(act);
        recycler_view_slider.setLayoutManager(new LinearLayoutManager(act,LinearLayoutManager.HORIZONTAL,false));
        recycler_view_slider.setAdapter(homeSlider);
    }
    private void setRecyclerViewCategory() {
        mChipCateAdapter = new ChipCateAdapter();
        mChipCateAdapter.setmListener(new ChipCateAdapter.OnSelectCateListener() {
            @Override
            public void selectCate(String string) {
                ChipCateTagEvent tag = new ChipCateTagEvent(string);
                EventBus.getDefault().post(tag);
                ((HomeActivity) getActivityReference()).setVPitem(1);
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
