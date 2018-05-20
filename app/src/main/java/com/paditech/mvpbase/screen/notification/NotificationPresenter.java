package com.paditech.mvpbase.screen.notification;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paditech.mvpbase.common.event.NewNotificationEvent;
import com.paditech.mvpbase.common.model.UserProfile;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenter;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by hung on 5/15/2018.
 */

public class NotificationPresenter extends FragmentPresenter<NotificationContact.ViewOps> implements NotificationContact.PresenterViewOps {
    @Override
    public void getListNotify() {
        FirebaseDatabase.getInstance().getReference().child("user").
                child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                    if (userProfile != null && userProfile.getNotify() != null && getView() != null) {
                        getView().setListNotify(userProfile.getNotify());
                        getView().hasNew();
                        EventBus.getDefault().post(new NewNotificationEvent());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
