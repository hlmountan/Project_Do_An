package com.paditech.mvpbase.screen.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.screen.detail.DetailActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hung on 4/28/2018.
 */

public class RecyclerViewApkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        Activity act;
    List<ApplicationInfo> packages;

    public List<ApplicationInfo> getPackages() {
        return packages;
    }

    public void setPackages(List<ApplicationInfo> packages) {
        this.packages = packages;
        notifyDataSetChanged();
    }

    public Activity getAct() {
        return act;
    }

    public void setAct(Activity act) {
        this.act = act;
    }

    @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_apk, parent, false);
            return new com.paditech.mvpbase.screen.adapter.RecyclerViewApkAdapter.RecyclerHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            com.paditech.mvpbase.screen.adapter.RecyclerViewApkAdapter.RecyclerHolder recyclerHolder = (com.paditech.mvpbase.screen.adapter.RecyclerViewApkAdapter.RecyclerHolder) holder;
            recyclerHolder.setData(position);
    }

        @Override
        public int getItemCount() {

            if (packages != null) {
                return packages.size();
            } else {
                return 0;
            }
        }


        public class RecyclerHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.tv_title)
            TextView tv_title;
            @BindView(R.id.tv_short_info)
            TextView tv_short_info;

            @BindView(R.id.img_avar)
            ImageView img_avar;
            @BindView(R.id.btn_uninstall)
            Button btn_uninstall;

            public RecyclerHolder(View itemView) {

                super(itemView);
                ButterKnife.bind(this, itemView);


            }

            public void setData(final int pos){
                tv_title.setText(itemView.getContext().getPackageManager().getApplicationLabel(packages.get(pos)));
                img_avar.setImageDrawable(itemView.getContext().getPackageManager().getApplicationIcon(packages.get(pos)));

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        img_avar.setTransitionName("image_avatar");
                        AppModel.SourceBean app = new AppModel.SourceBean();
                        app.setAppid(packages.get(pos).packageName);
                        app.setFirebaseCmt(true);
                        app.setTitle(itemView.getContext().getPackageManager().getApplicationLabel(packages.get(pos)).toString().trim());
                        EventBus.getDefault().postSticky(app);
                        Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                        intent.putExtra("is_cover",false);
                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(act, img_avar, "image_avatar");
                        itemView.getContext().startActivity(intent, optionsCompat.toBundle());
                    }
                });
                btn_uninstall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri packageUri = Uri.parse("package:" + packages.get(pos).packageName);
                        Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE, packageUri);
                        getAct().startActivity(intent);
                        notifyDataSetChanged();
                    }


                });
            }



        }





}
