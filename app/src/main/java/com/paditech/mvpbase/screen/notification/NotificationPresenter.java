package com.paditech.mvpbase.screen.notification;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paditech.mvpbase.common.event.NewNotificationEvent;
import com.paditech.mvpbase.common.model.Notification;
import com.paditech.mvpbase.common.model.UserProfile;
import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hung on 5/15/2018.
 */

public class NotificationPresenter extends FragmentPresenter<NotificationContact.ViewOps> implements NotificationContact.PresenterViewOps {
    @Override
    public void getListNotify() {

        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            FirebaseDatabase.getInstance().getReference().child("user").
                    child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                    addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                        if (userProfile != null && userProfile.getNotify() != null && getView() != null) {
                            //tu list notify id lay notify
                            final List<Notification> listNotify = new ArrayList<>();
                            for (final Map<String, String> a : userProfile.getNotify()) {
                                //laynotify
                                FirebaseDatabase.getInstance().getReference().child("notifycation").child(a.get("key")).
                                        addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.getValue() != null) {
                                                    Notification notification = dataSnapshot.
                                                            getValue(Notification.class);
                                                    // set notify da doc hay chua
                                                    if (a.get("status").equals("new")) {
                                                        notification.setRead(false);
                                                    } else notification.setRead(true);
                                                    // set notify cos phair cuar ung dung cuar minh khong
                                                    if (a.get("yourapp") != null) {
                                                        notification.setOwnApp(true);
                                                    } else notification.setOwnApp(false);
                                                    listNotify.add(notification);
                                                    getView().setListNotify(listNotify);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                            }

                            getView().setListNotify(listNotify);
                            getView().hasNew();
                            //thông báo có notify moi
                            EventBus.getDefault().post(new NewNotificationEvent());
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        else getView().setListNotify(null);
    }
}
