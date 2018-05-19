package com.paditech.mvpbase.screen.notification;

import com.paditech.mvpbase.common.model.Notification;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenterViewOps;
import com.paditech.mvpbase.common.mvp.activity.ActivityViewOps;

import java.util.List;

/**
 * Created by hung on 5/15/2018.
 */

public interface NotificationContact {
    interface ViewOps extends ActivityViewOps{
        void setListNotify(List<Notification> listNotify);
    }
    interface PresenterViewOps extends ActivityPresenterViewOps{

        void getListNotify();
    }
}
