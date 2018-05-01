package com.paditech.mvpbase.screen.dev;

import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenterViewOps;
import com.paditech.mvpbase.common.mvp.activity.ActivityViewOps;

import java.util.List;

/**
 * Created by hung on 4/27/2018.
 */

public interface  DevContact {
    interface ViewOps extends ActivityViewOps{
        void setRelateApp(List<AppModel> app);
    }
    interface PresenterViewOps extends ActivityPresenterViewOps{
        void cURLApi(String appid);

    }
}
