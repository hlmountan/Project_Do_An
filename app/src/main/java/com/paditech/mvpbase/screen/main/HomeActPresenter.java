package com.paditech.mvpbase.screen.main;

import android.view.View;

import com.google.gson.Gson;
import com.paditech.mvpbase.common.model.Appsxyz;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.service.APIClient;
import com.paditech.mvpbase.common.service.ICallBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hung on 4/17/2018.
 */

public class HomeActPresenter extends ActivityPresenter<HomeActContact.ViewOps> implements HomeActContact.PresenterViewOps {
    @Override
    public void cURLSearchData(final int page, String api) {
        getView().onSearching();
        APIClient.getInstance().execGet(String.format(api, page), null, new ICallBack() {
            @Override
            public void onErrorToken() {

            }

            @Override
            public void onFailed(IOException e) {
                getView().onSearchDone();
            }

            @Override
            public void onResponse(String response, boolean isSuccessful) {
                // do something here
                getView().onSearchDone();
                final Appsxyz result = new Gson().fromJson(response, Appsxyz.class);
                if (result != null) {
                    if (page ==1){
                        getView().setSearchResult(result.getResult());
                    }else{
                        getView().loadMore(result.getResult());
                    }

                }
            }
        });
    }

    @Override
    public void getListCates() {
        String[] array = new String[]{"Game", "Sport", "Funny", "English", "Education", "Music",
                "Language", "Star", "Food", "Cooking", "Car"};
        getView().updateListCates(Arrays.asList(array));
    }
}
