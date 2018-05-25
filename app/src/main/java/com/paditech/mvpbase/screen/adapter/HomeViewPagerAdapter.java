package com.paditech.mvpbase.screen.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.utils.ImageUtil;

import java.util.List;

/**
 * Created by hung on 1/2/2018.
 */

public class HomeViewPagerAdapter extends PagerAdapter {

    private List<AppModel.SourceBean> mList;
    private String activityName;

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setList(List<AppModel.SourceBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override

    public int getCount() {
            return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final ImageView mImageView = new ImageView(container.getContext());
//        mImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        ImageUtil.loadImage(container.getContext(), mList.get(position).getCover(), mImageView);
//        container.addView(mImageView);
//        mImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent;
//                switch (getActivityName()) {
//                    case "HOME":
//                        EventBus.getDefault().postSticky(mList.get(position));
//                        intent = new Intent(view.getContext(), DetailActivity.class);
//                        intent.putExtra("is_cover",true);
//                        mImageView.getContext().startActivity(intent);
//                        break;
//                    default:
//                        EventBus.getDefault().postSticky(mList);
//                        intent = new Intent(view.getContext(), ScreenShotFullScreenActivity.class);
//                        mImageView.getContext().startActivity(intent);
//                        break;
//                }
//
//            }
//
//        });

        switch (position){
            case 0:

//                mImageView.setImageResource(R.drawable.banner);
                ImageUtil.loadImageRounded(container.getContext(),"https://image.winudf.com/v2/image/Y29tLmdhbWVsb2Z0LmFuZHJvaWQuQU5NUC5HbG9mdE1WSE1fYmFubmVyXzE1MjUyMzIyMzFfMDY3/banner.jpg?w=850&fakeurl=1&type=.jpg",mImageView,R.drawable.events_placeholder, R.drawable.image_placeholder_500x500);
                break;
            case 1:
//                mImageView.setImageResource(R.drawable.banner1);
                ImageUtil.loadImageRounded(container.getContext(),"https://image.winudf.com/v2/image/Y29tLmdhbWVsb2Z0LmFuZHJvaWQuQU5NUC5HbG9mdE1WSE1fYmFubmVyXzE1MjUyMzIyMzFfMDY3/banner.jpg?w=850&fakeurl=1&type=.jpghttps://image.winudf.com/v2/image/Y29tLm5ldG1hcmJsZS5taGVyb3NnYl9iYW5uZXJfMTUyNDYyMTk0M18wODM/banner.jpg?w=850&fakeurl=1&type=.jpg",mImageView,R.drawable.events_placeholder, R.drawable.image_placeholder_500x500);

                break;
            default:
//                mImageView.setImageResource(R.drawable.banner2);
                ImageUtil.loadImageRounded(container.getContext(),"https://image.winudf.com/v2/image/Y29tLm5leG9ubS5zcHJvdXRzX2Jhbm5lcl8xNTI0MjcyOTY3XzA4MA/banner.jpg?w=850&fakeurl=1&type=.jpg",mImageView,R.drawable.events_placeholder, R.drawable.image_placeholder_500x500);

                break;
        }
        container.addView(mImageView);
        return mImageView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


}
