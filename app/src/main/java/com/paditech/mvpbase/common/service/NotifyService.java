package com.paditech.mvpbase.common.service;

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
import com.paditech.mvpbase.screen.detail.DetailActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hung on 5/28/2018.
 */

public class NotifyService extends Service {

    List<String> notifyId = new ArrayList<>();
    NotificationManager notificationManager ;
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
        EventBus.getDefault().register(this);
        notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        System.out.println(" onstart ajksndawndkawndjbawkd");
        FirebaseDatabase.getInstance().getReference().child("notification").child(FirebaseAuth.getInstance().
                getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            // Prepare intent which is triggered if the
                            // notification is selected
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                com.paditech.mvpbase.common.model.Notification notification = data.
                                        getValue(com.paditech.mvpbase.common.model.Notification.class);
                                if (!notification.getRead() && !checkInList(notification.getNotifyId())) {
                                    notifyId.add(notification.getNotifyId());
                                    Intent intent = new Intent(getBaseContext(), DetailActivity.class);
                                    intent.putExtra("NOTIFY", notification.getAppid() + "/"
                                            + notification.getNotifyId());
                                    PendingIntent pIntent = PendingIntent.getActivity(getBaseContext(),
                                            (int) System.currentTimeMillis(), intent, 0);

                                    // Build notification
                                    // Actions are just fake
                                    Notification noti;
                                    if (notification.getStatus() == 3) {
                                        noti = new Notification.Builder(getBaseContext())
                                                .setContentTitle("New comment  " + notification.getTitle())
                                                .setContentText(notification.getContent()).setSmallIcon(R.drawable.notifi_icon)
                                                .setContentIntent(pIntent)
                                                .addAction(R.drawable.notifi_icon, "See", pIntent)
                                                .addAction(R.drawable.notifi_icon, "Ignore", pIntent).build();
                                    } else {
                                        noti = new Notification.Builder(getBaseContext())
                                                .setContentTitle("New app Publiced  " + notification.getTitle())
                                                .setContentText(notification.getContent()).setSmallIcon(R.drawable.notifi_icon)
                                                .setContentIntent(pIntent)
                                                .addAction(R.drawable.notifi_icon, "See", pIntent)
                                                .addAction(R.drawable.notifi_icon, "Ignore", pIntent).build();
                                    }


                                    // hide the notification after its selected
                                    noti.flags |= Notification.FLAG_AUTO_CANCEL;

                                    notificationManager.notify((int) System.currentTimeMillis(), noti);
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
        EventBus.getDefault().unregister(this);
        System.out.println(" stop");
        notificationManager.cancelAll();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void removeItem(com.paditech.mvpbase.common.model.Notification item) {
        for (int i = 0; i < notifyId.size(); i++)
            if (notifyId.get(i).equals(item.getNotifyId())) {
                notifyId.remove(i);
                break;
            }

    }

    public Boolean checkInList(String notiId) {
        if (notifyId.size() > 0)
            for (int i = 0; i < notifyId.size(); i++)
                if (notifyId.get(i).equals(notiId))
                    return true;

        return false;
    }


}
