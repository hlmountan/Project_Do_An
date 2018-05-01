package com.paditech.mvpbase.screen.search;

import android.view.View;

import com.google.gson.Gson;
import com.paditech.mvpbase.common.model.Appsxyz;
import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenter;
import com.paditech.mvpbase.common.service.APIClient;
import com.paditech.mvpbase.common.service.ICallBack;

import java.io.IOException;

/**
 * Created by nhapcs on 4/20/18.
 */

public class SearchPresenter extends FragmentPresenter<SearchContact.ViewOps> implements SearchContact.PresenterViewOps {
    @Override
    public void getMoreApp(final int page, String api) {
        try {
            getView().onLoading();
            APIClient.getInstance().execGet(String.format(api, page), null, new ICallBack() {
                @Override
                public void onErrorToken() {
                }

                @Override
                public void onFailed(IOException e) {

                }

                @Override
                public void onResponse(final String response, boolean isSuccessful) {
                    final Appsxyz result = new Gson().fromJson(response, Appsxyz.class);
                    if (result != null) {
                        if (result.getResult() != null) {
                            if (page == 1) {
                                getView().updateApps(result.getResult());
                            } else {
                                getView().addApps(result.getResult());
                            }

                        }

                    }
                    getView().onLoadDone();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
