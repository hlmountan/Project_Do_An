package com.paditech.mvpbase.screen.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paditech.mvpbase.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hung on 5/10/2018.
 */

public class RecyclerViewAllApkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ArrayList<String>> listApk;

    public Activity getAct() {
        return act;
    }

    public void setAct(Activity act) {
        this.act = act;
    }

    public ArrayList<ArrayList<String>> getListApk() {

        return listApk;
    }

    public void setListApk(ArrayList<ArrayList<String>> listApk) {
        this.listApk = listApk;
        notifyDataSetChanged();
    }

    Activity act;

    public RecyclerViewAllApkAdapter(Activity act) {
        this.act = act;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_apk_file, parent, false);
        return new com.paditech.mvpbase.screen.adapter.RecyclerViewAllApkAdapter.RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        com.paditech.mvpbase.screen.adapter.RecyclerViewAllApkAdapter.RecyclerHolder recyclerHolder = (com.paditech.mvpbase.screen.adapter.RecyclerViewAllApkAdapter.RecyclerHolder) holder;
        recyclerHolder.setData(position);
    }

    @Override
    public int getItemCount() {

        if (listApk != null) {
            return listApk.size();
        } else {
            return 0;
        }
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder  {

        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_size)
        TextView tv_size;
        @BindView(R.id.tv_date)
        TextView tv_date;

        public RecyclerHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(final int pos){
            tv_title.setText(listApk.get(pos).get(0));
            tv_size.setText(listApk.get(pos).get(1)+" Mb");
            tv_date.setText(listApk.get(pos).get(2));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().postSticky(listApk.get(pos));
                    act.finish();
                }
            });
        }



    }


}
