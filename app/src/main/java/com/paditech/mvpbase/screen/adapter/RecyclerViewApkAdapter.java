package com.paditech.mvpbase.screen.adapter;

import android.app.Activity;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.paditech.mvpbase.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hung on 4/28/2018.
 */

public class RecyclerViewApkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        Activity act;



        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_apk, parent, false);
            return new com.paditech.mvpbase.screen.adapter.RecyclerViewApkAdapter.RecyclerHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            com.paditech.mvpbase.screen.adapter.RecyclerViewApkAdapter.RecyclerHolder recyclerHolder = (com.paditech.mvpbase.screen.adapter.RecyclerViewApkAdapter.RecyclerHolder) holder;
        }

        @Override
        public int getItemCount() {
            return 20;
//        if (mList1 != null) {
//            return mList1.size();
//        } else {
//            return 0;
//        }
        }

        public class RecyclerHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.tv_title)
            TextView tv_title;
            @BindView(R.id.img_avar)
            ImageView img_avar;

            public RecyclerHolder(View itemView) {

                super(itemView);
                ButterKnife.bind(this, itemView);
            }

        }





}
