package com.paditech.mvpbase.screen.adapter;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
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
 * Created by hung on 4/24/2018.
 */

public class RecyclerViewScreenShortAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


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

        public RecyclerViewScreenShortAdapter(Activity act) {
            this.act = act;
        }



        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_screen_short, parent, false);
            return new com.paditech.mvpbase.screen.adapter.RecyclerViewScreenShortAdapter.RecyclerHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            com.paditech.mvpbase.screen.adapter.RecyclerViewScreenShortAdapter.RecyclerHolder recyclerHolder = (com.paditech.mvpbase.screen.adapter.RecyclerViewScreenShortAdapter.RecyclerHolder) holder;
            recyclerHolder.setData(position);
        }

        @Override
        public int getItemCount() {
            if (mList != null) {
                return mList.size();
            } else {
                return 0;
            }
        }

        public class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            @BindView(R.id.img_screen_short)
            ImageView img_screen_short;

            public RecyclerHolder(View itemView) {

                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            private   void setData(int pos){
                img_screen_short.setMaxWidth(CommonUtil.getWidthScreen(act));
                ImageUtil.loadImageRounded(itemView.getContext(), mList.get(pos).getCover(), img_screen_short, R.drawable.events_placeholder,R.drawable.image_placeholder_500x500);
            itemView.setOnClickListener(this);
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
