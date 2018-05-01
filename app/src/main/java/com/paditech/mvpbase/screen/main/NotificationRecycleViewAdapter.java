package com.paditech.mvpbase.screen.main;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.utils.ImageUtil;
import com.paditech.mvpbase.screen.detail.DetailActivity;
import com.paditech.mvpbase.screen.home.HomeRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hung on 4/19/2018.
 */

public class NotificationRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
            return new com.paditech.mvpbase.screen.main.NotificationRecycleViewAdapter.RecyclerHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            com.paditech.mvpbase.screen.main.NotificationRecycleViewAdapter.RecyclerHolder recyclerHolder = (com.paditech.mvpbase.screen.main.NotificationRecycleViewAdapter.RecyclerHolder) holder;

        }

        @Override
        public int getItemCount() {
                return 10 ;

        }

        public class RecyclerHolder extends RecyclerView.ViewHolder {
            public RecyclerHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

}
