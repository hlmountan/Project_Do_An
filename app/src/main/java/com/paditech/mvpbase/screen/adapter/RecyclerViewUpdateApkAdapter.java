package com.paditech.mvpbase.screen.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.utils.ImageUtil;
import com.paditech.mvpbase.screen.updateApkActivity.UpdateApkActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hung on 5/15/2018.
 */

public class RecyclerViewUpdateApkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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

        public RecyclerViewUpdateApkAdapter(Activity act) {
            this.act = act;
        }

        public void setmList1(List<AppModel> mList1) {
            this.mList1 = mList1;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(getItemId(), parent, false);
            return new com.paditech.mvpbase.screen.adapter.RecyclerViewUpdateApkAdapter.RecyclerHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            com.paditech.mvpbase.screen.adapter.RecyclerViewUpdateApkAdapter.RecyclerHolder recyclerHolder = (com.paditech.mvpbase.screen.adapter.RecyclerViewUpdateApkAdapter.RecyclerHolder) holder;
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

            @BindView(R.id.img_avar)
            ImageView imageView;
            @BindView(R.id.tv_title)
            TextView textView_title;


            public RecyclerHolder(View itemView) {

                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            private void setData(int pos) {
                final AppModel result = mList1.get(pos);
                if (result.getSource() != null) {
                    AppModel.SourceBean sourceBean = result.getSource();
                    textView_title.setText(sourceBean.getTitle());
                    int radius = itemView.getResources().getDimensionPixelSize(R.dimen.radius_big);
                    if (sourceBean.getCover() == null) {
                        ImageUtil.loadImageRounded(itemView.getContext(), sourceBean.getThumbnail(), imageView,
                                R.drawable.events_placeholder, R.drawable.image_placeholder_500x500);

                    } else {
                        ImageUtil.loadImageRounded(itemView.getContext(), sourceBean.getCover(),
                                imageView, R.drawable.events_placeholder, R.drawable.image_placeholder_500x500);
                    }

                } else {
                    textView_title.setText("");
                    imageView.setImageResource(R.color.gray_light);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AppModel.SourceBean sourceBean = result.getSource();
                        sourceBean.setFirebaseCmt(true);

                        EventBus.getDefault().postSticky(sourceBean);
                        Intent intent = new Intent(itemView.getContext(), UpdateApkActivity.class);
                        itemView.getContext().startActivity(intent);


                    }
                });
            }


        }


}
