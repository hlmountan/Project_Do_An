package com.paditech.mvpbase.screen.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.model.Notification;
import com.paditech.mvpbase.common.utils.CommonUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hung on 4/19/2018.
 */

public class NotificationRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Notification> listNotify;

    public List<Notification> getListNotify() {
        return listNotify;
    }

    public void setListNotify(List<Notification> listNotify) {
        this.listNotify = listNotify;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new com.paditech.mvpbase.screen.main.NotificationRecycleViewAdapter.RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        com.paditech.mvpbase.screen.main.NotificationRecycleViewAdapter.RecyclerHolder recyclerHolder = (com.paditech.mvpbase.screen.main.NotificationRecycleViewAdapter.RecyclerHolder) holder;
        recyclerHolder.setData(position);
    }

    @Override
    public int getItemCount() {
        if (listNotify != null)
            return listNotify.size();
        else return 0;

    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_notifi_status)
        ImageView img_notifi_status;
        @BindView(R.id.tv_status)
        TextView tv_status;
        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.tv_isread)
        TextView tv_isread;


        public RecyclerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int pos) {
            if (listNotify.get(pos) != null) {
                switch (listNotify.get(pos).getStatus()) {
                    case 1:
                        tv_status.setText(R.string.notify_status_1);
                        break;
                    case 2:
                        tv_status.setText(R.string.notify_status_2);
                        break;
                    default:
                        tv_status.setText(R.string.notify_status_3);
                        break;
                }
                title.setText(listNotify.get(pos).getTitle());
                tv_content.setText(listNotify.get(pos).getContent());
                if (listNotify.get(pos).getRead()){
                    tv_isread.setText(R.string.notify_read);
                }else tv_isread.setText(R.string.notify_new);
                tv_date.setText(CommonUtil.convertTime(listNotify.get(pos).getDate()));
            }
        }
    }

}
