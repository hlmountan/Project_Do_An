package com.paditech.mvpbase.screen.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paditech.mvpbase.R;

import java.util.List;

/**
 * Created by nhapcs on 4/20/18.
 */

public class ChipCateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> mListCates;
    private OnSelectCateListener mListener;
    Activity act;

    public Activity getAct() {
        return act;
    }

    public void setAct(Activity act) {
        this.act = act;
    }

    public void setmListener(OnSelectCateListener mListener) {
        this.mListener = mListener;
    }

    public void setmListCates(List<String> mListCates) {
        this.mListCates = mListCates;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChipHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag_cate, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TextView textView = holder.itemView.findViewById(R.id.tv_tag);
        textView.setText(mListCates.get(position));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) mListener.selectCate(mListCates.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListCates == null ? 0 : mListCates.size();
    }

    private class ChipHolder extends RecyclerView.ViewHolder {

        public ChipHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnSelectCateListener {
        void selectCate(String string);
    }
}
