package com.paditech.mvpbase.screen.profile;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paditech.mvpbase.common.event.ApkFileInfoEvent;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.model.Cmt;
import com.paditech.mvpbase.common.model.UserProfile;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hung on 5/7/2018.
 */

public class ProfilePresenter extends ActivityPresenter<ProfileContact.ViewOps> implements ProfileContact.PresenterViewOps {
   @Override
    public void setChartData() {
        getView().setAppDownload();
    }

    @Override
    public void getUserData() {
        final FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getUid()).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                            if (getView()!= null){
                                getView().setUserData(userProfile);
                            }

                        } else if (getView()!= null) getView().setUserData(new UserProfile());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    public void getUserApk() {
        FirebaseDatabase.getInstance().getReference().child("apk").orderByChild("uid").equalTo(FirebaseAuth.
                getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.getValue() != null) {
                        final List<AppModel> listApk = new ArrayList<>();
                        for (DataSnapshot a : dataSnapshot.getChildren()) {
                            final ApkFileInfoEvent apk = a.getValue(ApkFileInfoEvent.class);
                            apk.setAppid(a.getKey());

                            //get user name
                            FirebaseDatabase.getInstance().getReference().child("user").
                                    child(apk.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue() != null) {
                                        UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                                        apk.setOfferby(userProfile.getName());
                                        AppModel app = new AppModel(apk);
                                        listApk.add(app);
                                        getView().loadChildUserUpload(listApk);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    System.out.println(databaseError + "eror day ne");
                                }
                            });

                        }
                    } else {
                        getView().loadChildUserUpload(null);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    getView().loadChildUserUpload(null);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getView().loadChildUserUpload(null);
            }
        });

    }

    @Override
    public void getAppCmt() {
        final List<Cmt> cmtList = new ArrayList<>();
        final DatabaseReference data = FirebaseDatabase.getInstance().getReference();
        data.child("apk").orderByChild("uid").equalTo(FirebaseAuth.getInstance().
                getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    for (DataSnapshot dt : dataSnapshot.getChildren()) {
                        data.child("cmt").orderByChild("appid").equalTo(dt.getKey().
                                replace("_-", ".")).addListenerForSingleValueEvent
                                (new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.getValue() != null) {
                                            for (DataSnapshot dts : dataSnapshot.getChildren()) {
                                                Cmt cmt = dts.getValue(Cmt.class);
                                                cmtList.add(cmt);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void getFollowApp() {
        final List<AppModel> followApps = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("user").
                child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent
                (new ValueEventListener() {
                     @Override
                     public void onDataChange(DataSnapshot dataSnapshot) {
                         if (dataSnapshot.getValue() !=null){
                             UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                             if (userProfile.getFollowApp() != null)
                                 for (ArrayList<String> appfl: userProfile.getFollowApp()) {
                                     AppModel app = new AppModel();
                                     AppModel.SourceBean sourceBean = new AppModel.SourceBean();
                                     sourceBean.setAppid(appfl.get(0));
                                     sourceBean.setCover(appfl.get(1));
                                     sourceBean.setTitle(appfl.get(2));
                                     sourceBean.setUserUpload(Boolean.parseBoolean(appfl.get(3)));
                                     app.setSource(sourceBean);
                                     followApps.add(app);
                                 }
                             getView().loadFollowApp(followApps);
                         }else{

                         }

                     }

                     @Override
                     public void onCancelled(DatabaseError databaseError) {

                     }
                 }
                );
    }
}
