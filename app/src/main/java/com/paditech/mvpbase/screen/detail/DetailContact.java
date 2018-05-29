package com.paditech.mvpbase.screen.detail;

import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.model.Cmt;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenterViewOps;
import com.paditech.mvpbase.common.mvp.activity.ActivityViewOps;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hung on 4/13/2018.
 */

public interface DetailContact {
    interface ViewOps extends ActivityViewOps {
        void setAppInfo(AppModel.SourceBean app);

        void setAppPriceHistory(ArrayList<ArrayList<String>> priceHistory);

        void setRelateApp(List<AppModel> app);

        void appNotAvailable();
        void setDevApp();
        void setUrlDownload(String url);
        void setFollowApp(ArrayList<ArrayList<String>> listApp);
        void setCmt(List<Cmt> cmt);

    }

    interface PresenterViewOps extends ActivityPresenterViewOps {
        void cURLFromApi(String appid);

        void getRelateApp(String url);

        void encodeDownloadUrl(String href);
        boolean checkIsApk(String appid);
        void downloadTask(String url);
        boolean isInstall(String appid);
        void getUserFollowApp();
        void updateFollowApp(ArrayList<ArrayList<String>> listApp);
        void pushCmt(Cmt cmt,AppModel.SourceBean ownApp);

        void getUserCmt(String appid);
        void notify(AppModel.SourceBean app,int status);

        void getPriceHistory(String appid);


    }
}
