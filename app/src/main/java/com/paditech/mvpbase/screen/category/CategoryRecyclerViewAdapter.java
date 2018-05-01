package com.paditech.mvpbase.screen.category;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.model.Cate;
import com.paditech.mvpbase.common.utils.ImageUtil;
import com.paditech.mvpbase.screen.showMoreApp.ShowMoreActicity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hung on 1/5/2018.
 */

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private List<Cate> mList;
    private Context context;


    public void setmList(List<Cate> mList) {
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerHolder recyclerHolder = (RecyclerHolder) holder;
        recyclerHolder.setData(position);


    }


    @Override
    public int getItemCount() {

        System.out.println(mList.size());
        return mList.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.image_cate)
        ImageView imageView_cate;
        @BindView(R.id.tv_category)
        TextView textView_cate;


        public RecyclerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener((View.OnClickListener) this);
        }

        private void setData(final int pos){
            Cate result = mList.get(pos);
            if(result !=null) {
                ImageUtil.loadImageRounded(itemView.getContext(),mList.get(pos).getCateImageSc(),imageView_cate,5);
                textView_cate.setText(mList.get(pos).getCateName());
            } else {
                textView_cate.setText("Category Here");
                imageView_cate.setImageResource(R.color.gray_light);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(),ShowMoreActicity.class);
                    intent.putExtra("CATE_NAME",mList.get(pos).getCateName());
                    intent.putExtra("CHECKCATE","TRUE");
                    itemView.getContext().startActivity(intent);
                }
            });
        }


        @Override
        public void onClick(View view) {
            System.out.println("you click");
        }
    }
}
