package com.paditech.mvpbase.screen.cmt;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.paditech.mvpbase.common.model.Cmt;
import com.paditech.mvpbase.common.model.CmtGp;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.service.APIClient;
import com.paditech.mvpbase.common.service.ICallBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hung on 4/28/2018.
 */

public class CommentPresenter extends ActivityPresenter<CommentContact.ViewOps> implements CommentContact.PresenterViewOps {
    @Override
    public void getCmt(final String appid) {
        APIClient.getInstance().execGet("https://apprevi.com/api/apk/review-daily?appid=" +
                appid.replace("-_",".") + "&time=7&star=5&language=en-US&page=1&size=20",
                null, new ICallBack() {
            //              APIClient.getInstance().execGet("https://apprevi.com/api/apk/review-daily?appid=com.instagram.android&time=7&star=5&language=en-US&page=1&size=6", null, new ICallBack() {
            @Override
            public void onErrorToken() {

            }

            @Override
            public void onFailed(IOException e) {

            }

            @Override
            public void onResponse(String response, boolean isSuccessful) {
                // do something here
                final CmtGp cmt = new Gson().fromJson(response, CmtGp.class);
                final List<Cmt> cmtList;
                if (cmt != null) {
                    //return something by call back to UI thread
                    cmtList = cmt.getComments();

                }else {
                    cmtList  = new ArrayList<>();
                }
                FirebaseDatabase.getInstance().getReference().child("cmt").
                        orderByChild("appid").equalTo(appid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {

                            for (DataSnapshot a : dataSnapshot.getChildren()) {
                                final Cmt cmt = a.getValue(Cmt.class);
                                cmtList.add(cmt);
                            }
                            if (getView()!=null)
                                getView().setCmt(cmtList);
                        }else getView().setCmt(cmtList);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

    }
}
