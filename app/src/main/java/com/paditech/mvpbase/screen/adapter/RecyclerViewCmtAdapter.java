package com.paditech.mvpbase.screen.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.model.AppModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hung on 4/26/2018.
 */

public class RecyclerViewCmtAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Activity act;

    public RecyclerViewCmtAdapter(Activity act) {
        this.act = act;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cmt, parent, false);
        return new com.paditech.mvpbase.screen.adapter.RecyclerViewCmtAdapter.RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        com.paditech.mvpbase.screen.adapter.RecyclerViewCmtAdapter.RecyclerHolder recyclerHolder = (com.paditech.mvpbase.screen.adapter.RecyclerViewCmtAdapter.RecyclerHolder) holder;
    }

    @Override
    public int getItemCount() {
        return 6;
//        if (mList1 != null) {
//            return mList1.size();
//        } else {
//            return 0;
//        }
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.ratingbar)
        RatingBar ratingbar;
        @BindView(R.id.tv_cmt_content)
        TextView tv_cmt_content;
        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.tv_user)
        TextView tv_user;

        public RecyclerHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }


}

