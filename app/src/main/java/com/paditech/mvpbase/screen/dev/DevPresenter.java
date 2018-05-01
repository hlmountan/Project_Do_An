package com.paditech.mvpbase.screen.dev;

import com.google.gson.Gson;
import com.paditech.mvpbase.common.model.Appsxyz;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.service.APIClient;
import com.paditech.mvpbase.common.service.ICallBack;

import java.io.IOException;

/**
 * Created by hung on 4/27/2018.
 */

public class DevPresenter extends ActivityPresenter<DevContact.ViewOps> implements DevContact.PresenterViewOps {
    @Override
    public void cURLApi(String url) {
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
