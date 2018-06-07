package com.paditech.mvpbase.screen.detail;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.utils.ImageUtil;

import java.util.ArrayList;

/**
 * Created by hung on 2/8/2018.
 */

public class DetailViewPagerAdapter extends PagerAdapter {

    ArrayList<String> mList;

    public ArrayList<String> getmList() {
        return mList;
    }

    public void setmList(ArrayList<String> mList) {
        this.mList = mList;
    }

    String actName = "";

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
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
        final PhotoView mImageView = new PhotoView(container.getContext());
        ImageUtil.loadImage(container.getContext(), mList.get(position), mImageView,
                R.drawable.events_placeholder, R.drawable.image_placeholder_500x500);
        container.addView(mImageView);

        return mImageView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
