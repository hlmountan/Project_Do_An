package com.paditech.mvpbase.screen.main;

import com.paditech.mvpbase.common.mvp.activity.ActivityPresenterViewOps;
import com.paditech.mvpbase.common.mvp.activity.ActivityViewOps;

/**
 * Created by Nha Nha on 9/19/2017.
 */

public interface MainContact {
    interface ViewOps extends ActivityViewOps {
        public  void setview() ;
    }

    interface PresenterViewOps extends ActivityPresenterViewOps {
        public void getInfo();
    }
}
