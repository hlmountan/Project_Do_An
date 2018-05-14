package com.paditech.mvpbase.screen.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.utils.ImageUtil;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by hung on 5/10/2018.
 */

public class RecyclerViewListScreenshortUploadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Activity act;

    public ArrayList<String> getListImg() {
        return listImg;
    }

    public void setListImg(ArrayList<String> listImg) {
        this.listImg = listImg;
        notifyDataSetChanged();
    }

    ArrayList<String> listImg;

    public RecyclerViewListScreenshortUploadAdapter(Activity act) {
        this.act = act;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int padding = act.getResources().getDimensionPixelOffset(R.dimen.padding_default);
        View view = new ImageView(act);
        view.setLayoutParams(new RelativeLayout.LayoutParams(act.getResources().getDimensionPixelSize(R.dimen.app_img_size), act.getResources().getDimensionPixelSize(R.dimen.app_img_size)));
        view.setPadding(padding,padding,padding,padding);

        return new com.paditech.mvpbase.screen.adapter.RecyclerViewListScreenshortUploadAdapter.RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageUtil.loadImage(act, listImg.get(position), (ImageView) holder.itemView);
    }

    @Override
    public int getItemCount() {

        if (listImg != null) {
            return listImg.size();
        } else {
            return 0;
        }
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {


        public RecyclerHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }


}
