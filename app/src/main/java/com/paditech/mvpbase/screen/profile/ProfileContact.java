package com.paditech.mvpbase.screen.profile;

import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenterViewOps;
import com.paditech.mvpbase.common.mvp.activity.ActivityViewOps;

import java.util.List;

/**
 * Created by hung on 5/7/2018.
 */

public interface ProfileContact {
    interface ViewOps extends ActivityViewOps{
        void setAppDownload();
        void setListAppData(List<AppModel> app);
    }

    interface PresenterViewOps extends ActivityPresenterViewOps{
    void setChartData();
    void setListApp(String url);
    }
}
