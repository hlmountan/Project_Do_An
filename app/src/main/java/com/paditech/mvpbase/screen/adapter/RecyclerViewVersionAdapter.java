package com.paditech.mvpbase.screen.adapter;

import android.app.Activity;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.model.AppVersion;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hung on 4/24/2018.
 */

public class RecyclerViewVersionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<AppVersion> mList1;

    Activity act;

    public RecyclerViewVersionAdapter(Activity act) {
        this.act = act;
    }

    public void setmList1(List<AppVersion> mList1) {
        this.mList1 = mList1;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_version, parent, false);
        return new com.paditech.mvpbase.screen.adapter.RecyclerViewVersionAdapter.RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        com.paditech.mvpbase.screen.adapter.RecyclerViewVersionAdapter.RecyclerHolder recyclerHolder = (com.paditech.mvpbase.screen.adapter.RecyclerViewVersionAdapter.RecyclerHolder) holder;
            recyclerHolder.setData(position);
    }

    @Override
    public int getItemCount() {

        if (mList1 != null) {
            return mList1.size();
        } else {
            return 0;
        }
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_version)
        TextView tv_version;
        @BindView(R.id.tv_size)
        TextView tv_size;
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.tv_android)
        TextView tv_android;
        @BindView(R.id.tv_download_number)
        TextView tv_download_number;


        public RecyclerHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int pos){
            tv_version.setText("Version "+mList1.get(pos).getVersion() + " has:");
            tv_size.setText(String.valueOf(mList1.get(pos).getSize()) + " MB");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tv_content.setText(Html.fromHtml(mList1.get(pos).getWhatsnew(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                tv_content.setText(Html.fromHtml(mList1.get(pos).getWhatsnew()));
            }
            tv_android.setText("Android Require: "+mList1.get(pos).getRequiresandroid());

            tv_download_number.setText("Download: "+String.valueOf(mList1.get(pos).getDownloadNumber()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

    }


}
