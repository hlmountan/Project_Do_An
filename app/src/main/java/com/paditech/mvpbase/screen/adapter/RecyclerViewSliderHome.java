package com.paditech.mvpbase.screen.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.utils.CommonUtil;
import com.paditech.mvpbase.common.utils.ImageUtil;
import com.paditech.mvpbase.screen.detail.ScreenShotFullScreenActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hung on 5/3/2018.
 */

public class RecyclerViewSliderHome extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<AppModel.SourceBean> mList;

    public List<AppModel.SourceBean> getmList() {
        return mList;
    }

    public void setmList(List<AppModel.SourceBean> mList) {
        this.mList = mList;
    }

    public Activity getAct() {
        return act;
    }

    public void setAct(Activity act) {
        this.act = act;
    }

    Activity act;

    public RecyclerViewSliderHome(Activity act) {
        this.act = act;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider, parent, false);
        return new com.paditech.mvpbase.screen.adapter.RecyclerViewSliderHome.RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        com.paditech.mvpbase.screen.adapter.RecyclerViewSliderHome.RecyclerHolder recyclerHolder = (com.paditech.mvpbase.screen.adapter.RecyclerViewSliderHome.RecyclerHolder) holder;
        recyclerHolder.setData(position);
    }

    @Override
    public int getItemCount() {
        return 3;
//        if (mList != null) {
//            return mList.size();
//        } else {
//            return 0;
//        }
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.img_screen_short)
        ImageView img_screen_short;

        public RecyclerHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setData(int pos) {
            img_screen_short.setMaxWidth(CommonUtil.getWidthScreen(act));
            switch (pos){
                case 0:
                    ImageUtil.loadImageRounded(itemView.getContext(),"https://image.winudf.com/v2/image/Y29tLmdhbWVsb2Z0LmFuZHJvaWQuQU5NUC5HbG9mdE1WSE1fYmFubmVyXzE1MjUyMzIyMzFfMDY3/banner.jpg?w=850&fakeurl=1&type=.jpg",img_screen_short,50);
                    break;
                case 1:
                    ImageUtil.loadImageRounded(itemView.getContext(),"https://image.winudf.com/v2/image/Y29tLm5ldG1hcmJsZS5taGVyb3NnYl9iYW5uZXJfMTUyNDYyMTk0M18wODM/banner.jpg?w=850&fakeurl=1&type=.jpg",img_screen_short,50);

                    break;
                default:
                    ImageUtil.loadImageRounded(itemView.getContext(),"https://image.winudf.com/v2/image/Y29tLm5leG9ubS5zcHJvdXRzX2Jhbm5lcl8xNTI0MjcyOTY3XzA4MA/banner.jpg?w=850&fakeurl=1&type=.jpg",img_screen_short,50);
                    break;
            }
        }

        @Override
        public void onClick(View view) {
            Intent intent;
            EventBus.getDefault().postSticky(mList);
            intent = new Intent(view.getContext(), ScreenShotFullScreenActivity.class);
            itemView.getContext().startActivity(intent);
        }
    }

}
