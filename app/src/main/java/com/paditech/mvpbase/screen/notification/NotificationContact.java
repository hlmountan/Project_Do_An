package com.paditech.mvpbase.screen.notification;

import com.paditech.mvpbase.common.model.Notification;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenterViewOps;
import com.paditech.mvpbase.common.mvp.activity.ActivityViewOps;
import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenterViewOps;
import com.paditech.mvpbase.common.mvp.fragment.FragmentViewOps;

import java.util.List;

/**
 * Created by hung on 5/15/2018.
 */

public interface NotificationContact {
    interface ViewOps extends FragmentViewOps {
        void setListNotify(List<Notification> listNotify);
        void hasNew();
    }

    interface PresenterViewOps extends FragmentPresenterViewOps {

        void getListNotify();
    }
}
