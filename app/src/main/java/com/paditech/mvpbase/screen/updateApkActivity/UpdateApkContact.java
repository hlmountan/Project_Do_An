package com.paditech.mvpbase.screen.updateApkActivity;

import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenterViewOps;
import com.paditech.mvpbase.common.mvp.activity.ActivityViewOps;

import java.util.List;

/**
 * Created by hung on 5/15/2018.
 */

public interface UpdateApkContact {
    interface ViewOps extends ActivityViewOps{
        void updateListCates(List<String> cates,List<String> requires);

    }

    interface  PresenterViewOps extends ActivityPresenterViewOps{
        void getListCates();
        void updateApk(AppModel.SourceBean sourceBean);
        void updateAppStatus(AppModel.SourceBean sourceBean);
        void pushNotify(AppModel.SourceBean app);

    }
}
