package com.paditech.mvpbase.screen.detail;

import android.view.View;

import com.google.gson.Gson;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.model.AppPriceHistory;
import com.paditech.mvpbase.common.model.Appsxyz;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.service.APIClient;
import com.paditech.mvpbase.common.service.ICallBack;

import java.io.IOException;

/**
 * Created by hung on 4/13/2018.
 */

public class DetailPresenter extends ActivityPresenter<DetailContact.ViewOps> implements DetailContact.PresenterViewOps {
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
                final AppModel.SourceBean app = new Gson().fromJson(response, AppModel.SourceBean.class);
                if (app != null){
                    //return something by call back to UI thread
                    getView().setAppInfo(app);

                }


            }
        });

//        if (isHistory == 1 ){
//            APIClient.getInstance().execGet("https://appsxyz.com/api/apk/price_history/?appid="+ appid, null, new ICallBack() {
//                @Override
//                public void onErrorToken() {
//
//                }
//
//                @Override
//                public void onFailed(IOException e) {
//
//                }
//
//                @Override
//                public void onResponse(String response, boolean isSuccessful) {
//                    // do something here
//                    final AppPriceHistory appPriceHistory = new Gson().fromJson(response, AppPriceHistory.class);
//                    if (appPriceHistory != null) {
//                        getView().setAppPriceHistory(appPriceHistory.getPriceHistory());
//                    }
//                }
//            });
//        }
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
}
