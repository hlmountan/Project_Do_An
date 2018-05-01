package com.paditech.mvpbase.screen.category;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.base.BaseFragment;
import com.paditech.mvpbase.common.model.Cate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hung on 1/5/2018.
 */

public class CategoryFragment extends BaseFragment {
    CategoryRecyclerViewAdapter categoryRecyclerViewAdapter;
    @BindView(R.id.recycler_view_category)
    RecyclerView recyclerView_cate;

    List<Cate> mList = new ArrayList<>();
    String[] mListCate  ;
    String[] mListAvar;
    @Override
    protected int getContentView() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initView(View view) {
        setRecyclerAdapter();

    }

    public void setRecyclerAdapter(){
        mListCate = getResources().getStringArray(R.array.category_name);
        mListAvar = getResources().getStringArray(R.array.cate_avar);
        setData();
        categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter();
        recyclerView_cate.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoryRecyclerViewAdapter.setmList(mList);
        recyclerView_cate.setAdapter(categoryRecyclerViewAdapter);
    }
    public void setData(){
        for (int i =0;i<52;i++){
            Cate cate = new Cate();
            cate.setCateImageSc(mListAvar[i]);
            cate.setCateName(mListCate[i]);
            mList.add(cate);
        }


    }
}
