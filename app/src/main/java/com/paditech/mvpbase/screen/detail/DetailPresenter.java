package com.paditech.mvpbase.screen.detail;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.google.gson.Gson;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.model.AppPriceHistory;
import com.paditech.mvpbase.common.model.Appsxyz;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.service.APIClient;
import com.paditech.mvpbase.common.service.ICallBack;

import java.io.IOException;
import java.util.List;

/**
 * Created by hung on 4/13/2018.
 */

public class DetailPresenter extends ActivityPresenter<DetailContact.ViewOps> implements DetailContact.PresenterViewOps
{
    @Override
    public void cURLFromApi(String appid,int isHistory) {
        APIClient.getInstance().execGet("https://appsxyz.com/api/apk/detailApp/?appid=" + appid, null, new ICallBack() {
            @Override
            public void onErrorToken() {

            }

            @Override
            public void onFailed(IOException e) {

            }

            @Override
            public void onResponse(String response, boolean isSuccessful) {
                // do something here
                System.out.println(response.indexOf("all_price"));
                final AppModel.SourceBean app = new Gson().fromJson(response, AppModel.SourceBean.class);
                if (app != null){
                    //return something by call back to UI thread
                    getView().setAppInfo(app);

                }


            }
        });

        if (isHistory == 1 ){
            APIClient.getInstance().execGet("https://appsxyz.com/api/apk/price_history/?appid="+ appid, null, new ICallBack() {
                @Override
                public void onErrorToken() {

                }

                @Override
                public void onFailed(IOException e) {

                }

                @Override
                public void onResponse(String response, boolean isSuccessful) {
                    // do something here
                    final AppPriceHistory appPriceHistory = new Gson().fromJson(response, AppPriceHistory.class);
                    if (appPriceHistory != null) {
                        getView().setAppPriceHistory(appPriceHistory.getPriceHistory());
                    }
                }
            });
        }
    }

    @Override
    public void getRelateApp(String url) {

        APIClient.getInstance().execGet(url, null, new ICallBack() {
            @Override
            public void onErrorToken() {

            }

            @Override
            public void onFailed(IOException e) {

            }

            @Override
            public void onResponse(String response, boolean isSuccessful) {
                final Appsxyz result = new Gson().fromJson(response, Appsxyz.class);
                if (result != null) {
                    getView().setRelateApp(result.getResult());
                }
            }
        });
    }

    @Override
    public void encodeDownloadUrl(String href) {
        int x = 110;
        int y = 220;
        String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MzMwLCJ4IjoxMTAsInkiOjIyMH0.yc4EHAz6LDXIYPO8VlENJGjnVmsV5aVyghYbbhtX3dM=";
        String url = href +"&token="+jwt+"&x="+x+"&y="+y;
        getView().setUrlDownload(url);
    }

    @Override
    public boolean checkIsApk(String appid) {
        return false;
    }

    @Override
    public void downloadTask(String url) {

    }
    @Override
    public boolean isInstall(String appid){
        boolean check = false;
        final PackageManager pm = getView().getApplicationContext().getPackageManager();
//get a list of installed apps.
        List<ApplicationInfo> packages =  pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (int i=0;i<packages.size();i++){
            if (packages.get(i).packageName.equals(appid)){
                check = true;
                System.out.println(" app  installed");
            }
        }
        return check;
    }

}
