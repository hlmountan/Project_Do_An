package com.paditech.mvpbase.screen.dev;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.paditech.mvpbase.common.event.ApkFileInfoEvent;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.model.ListAppFromDev;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.service.APIClient;
import com.paditech.mvpbase.common.service.ICallBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hung on 4/27/2018.
 */

public class DevPresenter extends ActivityPresenter<DevContact.ViewOps> implements DevContact.PresenterViewOps {
    @Override
    public void getServerDev(String devId) {
        APIClient.getInstance().execGet("https://apprevi.com/api/apk/dev?dev_id="+devId+"&size=50&page=1", null, new ICallBack() {
            @Override
            public void onErrorToken() {

            }

            @Override
            public void onFailed(IOException e) {

            }

            @Override
            public void onResponse(String response, boolean isSuccessful) {

                final ListAppFromDev result = new Gson().fromJson(response, ListAppFromDev.class);
                if (result != null) {
                    getView().setRelateApp(result.getAppModels());
                }
            }
        });
    }

    @Override
    public void getFirebaseDev(String uid) {
        final List<AppModel> appModels = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("apk").orderByChild("uid").equalTo(uid).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null)
                            for (DataSnapshot data: dataSnapshot.getChildren()) {
                                ApkFileInfoEvent apk  =  data.getValue(ApkFileInfoEvent.class);
                                AppModel app = new AppModel(apk);
                                appModels.add(app);
                            }
                            if (getView()!= null) getView().setRelateApp(appModels);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
