package com.paditech.mvpbase.screen.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.model.Cmt;
import com.paditech.mvpbase.common.utils.ImageUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hung on 4/26/2018.
 */

public class RecyclerViewCmtAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Activity act;
    List<Cmt> cmt;

    public List<Cmt> getCmt() {
        return cmt;
    }

    public void setCmt(List<Cmt> cmt) {
        this.cmt = cmt;
        notifyDataSetChanged();
    }

    public Activity getAct() {
        return act;
    }

    public void setAct(Activity act) {
        this.act = act;
        notifyDataSetChanged();
    }

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
        recyclerHolder.setData(position);
    }

    @Override
    public int getItemCount() {
        if (cmt != null) {
            return cmt.size();
        } else {
            return 0;
        }
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
        @BindView(R.id.img_avar)
        ImageView img_avar;

        public RecyclerHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int pos){
            if (cmt != null){
                tv_user.setText(cmt.get(pos).getAuthorName());
                tv_title.setText(cmt.get(pos).getTitleComment());
                tv_cmt_content.setText(cmt.get(pos).getComment());
                tv_date.setText(convertTime(cmt.get(pos).getTime()));
                ratingbar.setRating(cmt.get(pos).getStarRating());
                ImageUtil.loadImageRounded(itemView.getContext(),cmt.get(pos).getAvatar(),img_avar,R.drawable.events_placeholder,R.drawable.image_placeholder_500x500,20);
            }
        }
        String convertTime(Long timestaim){
            DateFormat mDataFormat= new SimpleDateFormat("MMM-dd-yy");
            Date mDate= new Date();
            mDate.setTime(timestaim * 1000);
            return mDataFormat.format(mDate);
        }

    }


}

