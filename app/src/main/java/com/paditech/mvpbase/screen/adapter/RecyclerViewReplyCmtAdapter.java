package com.paditech.mvpbase.screen.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.model.Cmt;
import com.paditech.mvpbase.common.utils.CommonUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hung on 5/9/2018.
 */

public class RecyclerViewReplyCmtAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Cmt> cmt;

    public List<Cmt> getCmt() {
        return cmt;
    }

    public void setCmt(List<Cmt> cmt) {
        this.cmt = cmt;
    }

    public Activity getAct() {
        return act;
    }

    public void setAct(Activity act) {
        this.act = act;
    }

    Activity act;

        public RecyclerViewReplyCmtAdapter(Activity act) {
            this.act = act;
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cmt_reply, parent, false);
            return new com.paditech.mvpbase.screen.adapter.RecyclerViewReplyCmtAdapter.RecyclerHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            com.paditech.mvpbase.screen.adapter.RecyclerViewReplyCmtAdapter.RecyclerHolder recyclerHolder = (com.paditech.mvpbase.screen.adapter.RecyclerViewReplyCmtAdapter.RecyclerHolder) holder;
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

        public class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnFocusChangeListener {

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
            @BindView(R.id.btn_sent)
            Button btn_sent;
            @BindView(R.id.et_dev_reply)
            EditText et_dev_reply;

            public RecyclerHolder(View itemView) {

                super(itemView);
                ButterKnife.bind(this, itemView);
                btn_sent.setOnClickListener(this);
                et_dev_reply.setOnFocusChangeListener(this);
            }
            public void setData(int pos){
                if (cmt != null){
                    tv_user.setText(cmt.get(pos).getAuthorName());
                    tv_title.setText(cmt.get(pos).getTitle());
                    tv_cmt_content.setText(cmt.get(pos).getContent());
                    tv_date.setText(convertTime(cmt.get(pos).getDate()));
                    ratingbar.setRating(cmt.get(pos).getRate());
                     }
            }
            String convertTime(Long timestaim){
                DateFormat mDataFormat= new SimpleDateFormat("MMM-dd-yy");
                Date mDate= new Date();
                mDate.setTime(timestaim * 1000);
                return mDataFormat.format(mDate);
            }
            @Override
            public void onClick(View view) {
                et_dev_reply.setText("");

            }

            @Override
            public void onFocusChange(View view, boolean b) {
                CommonUtil.dismissSoftKeyboard(view,act);
            }
        }





}
