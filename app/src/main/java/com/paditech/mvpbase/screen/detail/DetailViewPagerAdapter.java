package com.paditech.mvpbase.screen.detail;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.utils.ImageUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by hung on 2/8/2018.
 */

public class DetailViewPagerAdapter extends PagerAdapter {

    List<AppModel.SourceBean> mList;
    String actName = "";

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public List<AppModel.SourceBean> getmList() {
        return mList;
    }

    public void setmList(List<AppModel.SourceBean> mList) {
        this.mList = mList;
    }

    public void setList(List<AppModel.SourceBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override

    public int getCount() {
        if (mList != null)
            return mList.size();
        else
            return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final ImageView mImageView = new ImageView(container.getContext());
        mImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageUtil.loadImage(container.getContext(), mList.get(position).getCover(), mImageView, R.drawable.events_placeholder,R.drawable.image_placeholder_500x500);
        container.addView(mImageView);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actName.equals("detail")){
                    Intent intent;
                    EventBus.getDefault().postSticky(mList);
                    intent = new Intent(view.getContext(), ScreenShotFullScreenActivity.class);
                    mImageView.getContext().startActivity(intent);
                }

            }
        });
        return mImageView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
