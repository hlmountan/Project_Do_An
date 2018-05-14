package com.paditech.mvpbase.screen.profile;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paditech.mvpbase.common.event.ApkFileInfoEvent;
import com.paditech.mvpbase.common.model.AppModel;
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
        UserProfile userProfile = new UserProfile(FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                FirebaseAuth.getInstance().getCurrentUser().getEmail(), FirebaseAuth.getInstance().getCurrentUser().
                getPhotoUrl().toString(), "Nam", 23, "009",FirebaseAuth.getInstance().getUid());
        getView().setUserData(userProfile);
    }

    public void getUserApk() {
        FirebaseDatabase.getInstance().getReference().child("apk").orderByChild("uid").equalTo(FirebaseAuth.
                getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    final List<AppModel> listApk = new ArrayList<>();
                    for (DataSnapshot a : dataSnapshot.getChildren()) {
                        final ApkFileInfoEvent apk = a.getValue(ApkFileInfoEvent.class);
                        apk.setAppid(a.getKey());

                        //get user name
                        FirebaseDatabase.getInstance().getReference().child("user").
                                child(apk.getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue() != null){
                                    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                                    apk.setOfferby(userProfile.getName());
                                    AppModel app = new AppModel(apk);
                                    listApk.add(app);
                                    getView().loadChildUserUpload(listApk);
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println(databaseError+ "eror day ne");
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
}
