package com.paditech.mvpbase.screen.updateApkActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paditech.mvpbase.common.event.ApkFileInfoEvent;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.model.Notification;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by hung on 5/15/2018.
 */

public class UpdateApkPresenter extends ActivityPresenter<UpdateApkContact.ViewOps> implements
        UpdateApkContact.PresenterViewOps {

    @Override
    public void getListCates() {
        String[] cates = new String[]{"Game", "Sport", "Funny", "English", "Education", "Music",
                "Language", "Star", "Food", "Cooking", "Car"};
        String[] requires = new String[]{"Android 2.0 – 2.1 ", "Android 2.2 – 2.2.3", "Android 2.3 – 2.3.7",
                "Android 3.0 – 3.2.6", "Android 4.0 – 4.0.4", "Android 4.1 – 4.3.1",
                "Android 4.4 – 4.4.4", "Android 5.0 – 5.1.1", "Android 6.0 – 6.0.1", "Android 7.0 – 7.1.2", "Android 8.0 – 8.1", "Android 9"};
        getView().updateListCates(Arrays.asList(cates), Arrays.asList(requires));
    }

    @Override
    public void updateApk(AppModel.SourceBean sourceBean) {
        FirebaseDatabase.getInstance().getReference().child("apk").child(sourceBean.getAppid()).
                child("title").setValue(sourceBean.getTitle());
        FirebaseDatabase.getInstance().getReference().child("apk").child(sourceBean.getAppid()).
                child("description").setValue(sourceBean.getDescription());
        FirebaseDatabase.getInstance().getReference().child("apk").child(sourceBean.getAppid()).
                child("cate").setValue(sourceBean.getCategory());
        FirebaseDatabase.getInstance().getReference().child("apk").child(sourceBean.getAppid()).
                child("policy").setValue(sourceBean.getPolicy());
        FirebaseDatabase.getInstance().getReference().child("apk").child(sourceBean.getAppid()).
                child("age").setValue(sourceBean.getContentrating());
        FirebaseDatabase.getInstance().getReference().child("apk").child(sourceBean.getAppid()).
                child("require").setValue(sourceBean.getRequire());
        FirebaseDatabase.getInstance().getReference().child("apk").child(sourceBean.getAppid()).
                child("status").setValue(sourceBean.getStatus());
    }

    @Override
    public void updateAppStatus(AppModel.SourceBean sourceBean) {
        FirebaseDatabase.getInstance().getReference().child("apk").child(sourceBean.getAppid()).
                child("status").setValue(sourceBean.getStatus());
    }

    @Override
    public void pushNotify(AppModel.SourceBean app) {
        /*
        1: new version
        2: app public
        3: new comment
         */
        // add notify
        final Notification notify = new Notification(2, app.getTitle(),
                "This app is now available for you to download",
                System.currentTimeMillis() / 1000, app.getAppid(), false, app.getTitle(), app.getCover());
        // add notify cho user
         String key = FirebaseDatabase.getInstance().getReference().child("notification").
                child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push().getKey();
         notify.setNotifyId(key);
         FirebaseDatabase.getInstance().getReference().child("notification").
                 child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(key).setValue(notify);

         // add notify for user follow
        FirebaseDatabase.getInstance().getReference().child("apk").child(app.getAppid().
                replace(".","_")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    ApkFileInfoEvent apk = dataSnapshot.getValue(ApkFileInfoEvent.class);
                    // app co thang nao follow hay k
                    if (apk.getNotification() != null){
                        for (final ArrayList<String> usernoti: apk.getNotification()) {
                            //push notify for each user
                            String key = FirebaseDatabase.getInstance().getReference().child("notification").
                                    child(usernoti.get(0)).push().getKey();
                            notify.setNotifyId(key);
                            FirebaseDatabase.getInstance().getReference().child("notification").child(usernoti.get(0)).
                                    child(key).setValue(notify);

                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
