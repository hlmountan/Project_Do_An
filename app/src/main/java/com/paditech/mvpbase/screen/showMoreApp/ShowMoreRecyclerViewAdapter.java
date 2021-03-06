package com.paditech.mvpbase.screen.showMoreApp;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.utils.ImageUtil;
import com.paditech.mvpbase.screen.detail.DetailActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hung on 1/19/2018.
 */

public class ShowMoreRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<AppModel> mList;
    Activity act;

    public ShowMoreRecyclerViewAdapter(Activity act){
        this.act = act;
    }
    public void setmList(List<AppModel> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void addMoremList(List<AppModel> newList) {
        for (AppModel apps : newList) {
            this.mList.add(apps);
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app, parent, false);
        return new ShowMoreRecyclerViewAdapter.AppHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ShowMoreRecyclerViewAdapter.AppHolder appHolder = (ShowMoreRecyclerViewAdapter.AppHolder) holder;
        appHolder.setData(position);
//        progressBar_sm.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        } else {
            return 0;
        }
    }

    public class AppHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_avar)
        ImageView imageView;
//        @BindView(R.id.tv_price)
        TextView textView_price;
        @BindView(R.id.tv_title)
        TextView textView_title;
//        @BindView(R.id.tv_lastest_price)
        TextView tv_lastest_price;
        String appid = "";

        public AppHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }

        private void setData(int pos) {

            final AppModel result = mList.get(pos);
            if (result.getSource() != null) {
                AppModel.SourceBean sourceBean = result.getSource();
                textView_title.setText(sourceBean.getTitle());
                ImageUtil.loadImageRounded(itemView.getContext(), sourceBean.getCover(), imageView, R.drawable.events_placeholder,R.drawable.image_placeholder_500x500);
                appid = sourceBean.getAppid();
//                if (sourceBean.getAllPrice() != null) {
//                    textView_price.setText("$" + sourceBean.getAllPrice().get(1) + " ");
//                    tv_lastest_price.setText("$" + sourceBean.getAllPrice().get(0));
//
//                } else {
//                    tv_lastest_price.setText("Free");
//                }


            } else {
                textView_title.setText("");
                imageView.setImageResource(R.color.gray_light);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageView.setTransitionName("image_avatar");
                    EventBus.getDefault().postSticky(result.getSource());
                    Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(act,imageView,"image_avatar" );
                    itemView.getContext().startActivity(intent,optionsCompat.toBundle());
                }
            });
        }


    }
}
