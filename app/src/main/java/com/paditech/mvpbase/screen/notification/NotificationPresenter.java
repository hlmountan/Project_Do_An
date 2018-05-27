package com.paditech.mvpbase.screen.notification;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paditech.mvpbase.common.event.NewNotificationEvent;
import com.paditech.mvpbase.common.model.Notification;
import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hung on 5/15/2018.
 */

public class NotificationPresenter extends FragmentPresenter<NotificationContact.ViewOps> implements NotificationContact.PresenterViewOps {
    @Override
    public void getListNotify() {

        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            FirebaseDatabase.getInstance().getReference().child("notification").
                    child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                    addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            List<Notification> notifications = new ArrayList<>();
                            if (dataSnapshot.getValue() != null)
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    Notification notification = data.getValue(Notification.class);
                                    if (!notification.getRead()) {
                                        notifications.add(notification);

                                        // show notify in home button
                                        if (getView() != null)
                                            getView().hasNew();
                                        EventBus.getDefault().post(new NewNotificationEvent());

                                    }


                                }
                            if (getView() != null) getView().setListNotify(notifications);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        else getView().setListNotify(null);
    }
}
