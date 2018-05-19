package com.paditech.mvpbase.screen.search;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.gravity.IChildGravityResolver;
import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.event.ChipCateTagEvent;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenter;
import com.paditech.mvpbase.common.mvp.fragment.MVPFragment;
import com.paditech.mvpbase.common.utils.CommonUtil;
import com.paditech.mvpbase.common.view.LoadMoreRecyclerView;
import com.paditech.mvpbase.screen.home.HomeListAppAdapter;
import com.paditech.mvpbase.screen.main.ScrollTopEvent;
import com.paditech.mvpbase.screen.main.adapter.ChipCateAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Nha Nha on 1/2/2018.
 */

public class SearchFragment extends MVPFragment<SearchContact.PresenterViewOps> implements SwipeRefreshLayout.OnRefreshListener,
        LoadMoreRecyclerView.LoadMoreListener, SearchContact.ViewOps,View.OnClickListener,View.OnFocusChangeListener,TextWatcher {

    @BindView(R.id.edit_text_search)
    EditText edit_text_search;
    @BindView(R.id.search_view)
    NestedScrollView search_view;
    @BindView(R.id.btn_cancel_search)
    Button btn_cancel_search;
    @BindView(R.id.recycler_view_list_cate)
    RecyclerView recyclerViewCategory;

    @BindView(R.id.recycler_view_search_item)
    LoadMoreRecyclerView recycler_view_search_item;
    @BindView(R.id.progressBar_search)
    ProgressBar progressBarSearch;

    HomeListAppAdapter mHomeListAppAdapter;

    int isTextSearch =0 ;
    private ChipCateAdapter mChipCateAdapter;
    Activity act;

    public Activity getAct() {
        return act;
    }

    public void setAct(Activity act) {
        this.act = act;
    }

    public static SearchFragment getInstance(Activity act) {
        SearchFragment f = new SearchFragment();
        f.setAct(act);
        return f;

    }

    public String getmAppAPI() {
        return mAppAPI;
    }

    public void setmAppAPI(String mAppAPI) {
        this.mAppAPI = mAppAPI;
    }

    private String mAppAPI;

    private int mPage = 1;

    @Override
    protected int getContentView() {
        return R.layout.frag_search;
    }

    @Override
    protected void initView(View view) {

        setRecyclerViewCategory();
        setUpAdapter();


        CommonUtil.dismissSoftKeyboard(view, getActivityReference());

        edit_text_search.setOnFocusChangeListener(this);
        edit_text_search.addTextChangedListener(this);
        btn_cancel_search.setOnClickListener(this);

        search_view.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            public static final String TAG = "";

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    System.out.println("adqwd asxc aw ");
                }
                if (scrollY < oldScrollY) {
                    System.out.println("Scroll UP");
                    Log.i(TAG, "Scroll UP");
                }

                if (scrollY == 0) {
                    System.out.println("TOP SCROLL");
                    Log.i(TAG, "TOP SCROLL");
                }

                if (scrollY == ( v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight() )) {
                    if (!recycler_view_search_item.ismIsLoading()) {
                        System.out.println("bottom SCROLL");
//                        recycler_view_search_item.showLoadMoreIndicator();
                        onLoadMore();

                    }
                }
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    @Override
    protected Class<? extends FragmentPresenter> onRegisterPresenter() {
        return SearchPresenter.class;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void scrolltop(ScrollTopEvent event) {
        search_view.smoothScrollTo(0,0);
        search_view.scrollTo(0,0);

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setTag(ChipCateTagEvent tag) {
        edit_text_search.setText(tag.getTag());
    }



    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        mPage +=1;
        getPresenter().cURLSearchData(mPage,getmAppAPI());
    }





    @Override
    public void updateApps(final List<AppModel> appModels) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mHomeListAppAdapter.setmList(appModels);
            }
        });
    }

    @Override
    public void addApps(final List<AppModel> appModels) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mHomeListAppAdapter.addMoremList(appModels);
            }
        });
    }

    @Override
    public void onSearching() {
        btn_cancel_search.setVisibility(View.INVISIBLE);
        progressBarSearch.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSearchDone() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btn_cancel_search.setVisibility(View.VISIBLE);
                progressBarSearch.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public void setSearchResult(final List<AppModel> listApp) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mHomeListAppAdapter.setmList(listApp);
            }
        });
    }

    @Override
    public void loadMore(final List<AppModel> listApp) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mHomeListAppAdapter.addMoremList(listApp);
            }
        });
    }

    @Override
    public void updateListCates(final List<String> strings) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mChipCateAdapter.setmListCates(strings);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onFocusChange(View view, boolean b) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!charSequence.toString().equals("")){
            mPage = 1;
            setmAppAPI("https://appsxyz.com/api/apk/search/?q=" + charSequence.toString() + "&page=%s&size=15");
            getPresenter().cURLSearchData(mPage,getmAppAPI());
            search_view.setSmoothScrollingEnabled(true);
            search_view.smoothScrollTo(0,0);
            recycler_view_search_item.smoothScrollBy(0,0);
            if (isTextSearch == 1){
                CommonUtil.dismissSoftKeyboard(act.findViewById(R.id.drawer), act);
                isTextSearch = 0 ;
            }

        }else{
            search_view.setSmoothScrollingEnabled(true);
            search_view.smoothScrollTo(0,0);
            recycler_view_search_item.smoothScrollBy(0,0);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        search_view.setSmoothScrollingEnabled(true);
        search_view.smoothScrollTo(0,0);
        recycler_view_search_item.smoothScrollBy(0,0);
    }
    private void setUpAdapter(){
        mHomeListAppAdapter = new HomeListAppAdapter(act);
        recycler_view_search_item.setLayoutManager(new GridLayoutManager(act, 3));
        recycler_view_search_item.setAdapter(mHomeListAppAdapter);
        recycler_view_search_item.setNestedScrollingEnabled(false);
    }
    private void setRecyclerViewCategory() {
        mChipCateAdapter = new ChipCateAdapter();
        mChipCateAdapter.setAct(act);
        mChipCateAdapter.setmListener(new ChipCateAdapter.OnSelectCateListener() {
            @Override
            public void selectCate(String string) {
                edit_text_search.setText(string);
                isTextSearch = 1;
                setmAppAPI("https://appsxyz.com/api/apk/search/?q=" + string + "&page=%s&size=15");


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
        recyclerViewCategory.setLayoutManager(chipsLayoutManager);
        recyclerViewCategory.setAdapter(mChipCateAdapter);
        recyclerViewCategory.setNestedScrollingEnabled(false);
        getPresenter().getListCates();
    }
}
