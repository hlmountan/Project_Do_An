package com.paditech.mvpbase.screen.updateApkActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paditech.mvpbase.common.event.ApkFileInfoEvent;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.model.UserProfile;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
        final com.paditech.mvpbase.common.model.Notification notify = new com.paditech.mvpbase.common.model.Notification(
                2, app.getTitle(), "This app is now available for you to download",
                System.currentTimeMillis()/1000,app.getAppid(),false,app.getTitle(),app.getCover()
                );
        final String key = FirebaseDatabase.getInstance().getReference().child("notifycation").push().getKey();
        FirebaseDatabase.getInstance().getReference().child("notifycation").child(key).setValue(notify);

        // add notify cho user
        FirebaseDatabase.getInstance().getReference().child("apk").child(app.getAppid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    ApkFileInfoEvent apk = dataSnapshot.getValue(ApkFileInfoEvent.class);
                    // co ai follow k ?

                    if (apk.getNotification() != null){
                        for (final ArrayList<String> usernoti:apk.getNotification()) {
                            FirebaseDatabase.getInstance().getReference().child("user").child(usernoti.get(0)).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue() != null){
                                        UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                                        ArrayList<Map<String,String>> notify = new ArrayList<>();
                                        Map<String, String> map = new HashMap<String, String>();
                                        map.put("key",key);
                                        map.put("status","new");
                                        map.put("yourapp","true");
                                        // co notify chua ?
                                        if (userProfile.getNotify() != null){
                                            notify = userProfile.getNotify();
                                            notify.add(map);
                                        }else notify.add(map);
                                        FirebaseDatabase.getInstance().getReference().child("user").
                                                child(usernoti.get(0)).child("notify").setValue(notify);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
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
