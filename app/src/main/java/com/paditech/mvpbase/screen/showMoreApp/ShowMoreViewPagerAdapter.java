package com.paditech.mvpbase.screen.showMoreApp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.model.Appsxyz;
import com.paditech.mvpbase.common.service.APIClient;
import com.paditech.mvpbase.common.service.ICallBack;
import com.paditech.mvpbase.common.utils.ImageUtil;
import com.paditech.mvpbase.screen.detail.DetailActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hung on 1/19/2018.
 */

public class ShowMoreViewPagerAdapter extends PagerAdapter {
    List<AppModel.SourceBean> mList;
    Activity act;
    Appsxyz result;

    public Activity getAct() {
        return act;
    }

    public void setAct(Activity act) {
        this.act = act;
    }

    public Appsxyz getResult() {
        return result;
    }

    public void setResult(Appsxyz result) {
        this.result = result;
        mList = new ArrayList<>();
        setViewPagerAdapter();
    }

    public void setmList(List<AppModel.SourceBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        if (mList != null )
            return mList.size();
        else
            return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final ImageView mImageView = new ImageView(container.getContext());
        mImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageUtil.loadImage(container.getContext(),mList.get(position).getCover(),mImageView);
        container.addView(mImageView);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().postSticky(mList.get(position));
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                mImageView.getContext().startActivity(intent);
            }
        });
        return mImageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    private void setViewPagerAdapter(){
        for (int i=0;i<5;i++){
            APIClient.getInstance().execGet("https://appsxyz.com/api/apk/detailApp/?appid=" + result.getResult().get(i).getSource().getAppid(), null, new ICallBack() {
                @Override
                public void onErrorToken() {

                }

                @Override
                public void onFailed(IOException e) {

                }

                @Override
                public void onResponse(String response, boolean isSuccessful) {
                    final AppModel.SourceBean app = new Gson().fromJson(response, AppModel.SourceBean.class);
                    if (app != null) {
                        act.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    AppModel.SourceBean sourceBean = new AppModel.SourceBean();
//                                    sourceBean.setCover(app.getScreenShot().get(0).get(2));
                                    sourceBean.setAppid(app.getAppid());
                                    mList.add(sourceBean);
                                    notifyDataSetChanged();
                                }catch (Exception e){
                                    System.out.println(e);
                                }
                            }
                        });


                    }
                }
            });


        }
    }
}
