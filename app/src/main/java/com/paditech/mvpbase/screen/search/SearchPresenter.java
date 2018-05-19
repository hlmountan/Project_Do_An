package com.paditech.mvpbase.screen.search;

import android.view.View;

import com.google.gson.Gson;
import com.paditech.mvpbase.common.model.Appsxyz;
import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenter;
import com.paditech.mvpbase.common.service.APIClient;
import com.paditech.mvpbase.common.service.ICallBack;

import java.io.IOException;
import java.util.Arrays;

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
                    if ( page ==1){
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
