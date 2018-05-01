package com.paditech.mvpbase.screen.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paditech.mvpbase.common.model.AppModel;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by hung on 5/1/2018.
 */

public class RecyclerViewLanguageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        List<AppModel> mList1;
        private int itemNumber;
        private int itemId;

        public int getItemId() {
            return itemId;
        }

        public void setItemId(int itemId) {
            this.itemId = itemId;
            notifyDataSetChanged();
        }

        public int getItemNumber() {
            return itemNumber;
        }

        public void setItemNumber(int itemNumber) {
            this.itemNumber = itemNumber;
        }

        Activity act;

        public RecyclerViewLanguageAdapter(Activity act) {
            this.act = act;
        }

        public void setmList1(List<AppModel> mList1) {
            this.mList1 = mList1;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(getItemId(), parent, false);
            return new com.paditech.mvpbase.screen.adapter.RecyclerViewLanguageAdapter.RecyclerHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            com.paditech.mvpbase.screen.adapter.RecyclerViewLanguageAdapter.RecyclerHolder recyclerHolder = (com.paditech.mvpbase.screen.adapter.RecyclerViewLanguageAdapter.RecyclerHolder) holder;
        }

        @Override
        public int getItemCount() {
            return getItemNumber();
//        if (mList1 != null) {
//            return mList1.size();
//        } else {
//            return 0;
//        }
        }

        public class RecyclerHolder extends RecyclerView.ViewHolder {

            public RecyclerHolder(View itemView) {

                super(itemView);
                ButterKnife.bind(this, itemView);
            }

        }




}
