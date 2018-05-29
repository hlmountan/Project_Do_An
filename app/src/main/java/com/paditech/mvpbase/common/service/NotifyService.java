package com.paditech.mvpbase.common.service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.view.NotificationReceiverActivity;

/**
 * Created by hung on 5/28/2018.
 */

public class NotifyService extends Service {

    Activity act;

    public Activity getAct() {
        return act;
    }

    public void setAct(Activity act) {
        this.act = act;
    }

    public NotifyService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println(" thasjdawndjawdkmawd");
        FirebaseDatabase.getInstance().getReference().child("notification").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null){
                            // Prepare intent which is triggered if the
                            // notification is selected
                            for (DataSnapshot data: dataSnapshot.getChildren()) {
                                com.paditech.mvpbase.common.model.Notification notification = data.
                                        getValue(com.paditech.mvpbase.common.model.Notification.class);
                                if (!notification.getRead()){
                                    Intent intent = new Intent(act, NotificationReceiverActivity.class);
                                    PendingIntent pIntent = PendingIntent.getActivity(act, (int) System.currentTimeMillis(), intent, 0);

                                    // Build notification
                                    // Actions are just fake
                                    Notification noti = new Notification.Builder(act)
                                            .setContentTitle("New comment  " + notification.getTitle())
                                            .setContentText(notification.getContent()).setSmallIcon(R.drawable.notifi_icon)
                                            .setContentIntent(pIntent)
                                            .addAction(R.drawable.notifi_icon, "Call", pIntent)
                                            .addAction(R.drawable.notifi_icon, "More", pIntent).build();
                                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                    // hide the notification after its selected
                                    noti.flags |= Notification.FLAG_AUTO_CANCEL;

                                    notificationManager.notify(0, noti);
                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        System.out.println(" onstart ajksndawndkawndjbawkd");
        FirebaseDatabase.getInstance().getReference().child("notification").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null){
                            // Prepare intent which is triggered if the
                            // notification is selected
                            for (DataSnapshot data: dataSnapshot.getChildren()) {
                                com.paditech.mvpbase.common.model.Notification notification = data.
                                        getValue(com.paditech.mvpbase.common.model.Notification.class);
                                if (!notification.getRead()){
                                    Intent intent = new Intent(act, NotificationReceiverActivity.class);
                                    PendingIntent pIntent = PendingIntent.getActivity(act, (int) System.currentTimeMillis(), intent, 0);

                                    // Build notification
                                    // Actions are just fake
                                    Notification noti = new Notification.Builder(act)
                                            .setContentTitle("New comment  " + notification.getTitle())
                                            .setContentText(notification.getContent()).setSmallIcon(R.drawable.notifi_icon)
                                            .setContentIntent(pIntent)
                                            .addAction(R.drawable.notifi_icon, "Call", pIntent)
                                            .addAction(R.drawable.notifi_icon, "More", pIntent).build();
                                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                    // hide the notification after its selected
                                    noti.flags |= Notification.FLAG_AUTO_CANCEL;

                                    notificationManager.notify(0, noti);
                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
