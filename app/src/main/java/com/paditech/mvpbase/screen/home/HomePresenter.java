package com.paditech.mvpbase.screen.home;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.model.Appsxyz;
import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenter;
import com.paditech.mvpbase.common.service.APIClient;
import com.paditech.mvpbase.common.service.ICallBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by hung on 4/13/2018.
 */

public class HomePresenter extends FragmentPresenter<HomeContact.ViewOsp> implements HomeContact.PresenterViewOsp {

    @Override
    public void getAppFromApi() {
        APIClient.getInstance().execGet("http://appsxyz.com/api/apk/lastes-sale/?page=1&size=12", null, new ICallBack() {
            @Override
            public void onErrorToken() {

            }

            @Override
            public void onFailed(IOException e) {

            }

            @Override
            public void onResponse(String response, boolean isSuccessful) {
                // do something here
                final Appsxyz result = new Gson().fromJson(response, Appsxyz.class);
                if (result != null) {
                    getView().loadChild1(result.getResult());

                }


                // and then callback to the UI thread

            }
        });
        APIClient.getInstance().execGet("https://appsxyz.com/api/apk/apk_category/?cat_name=Action&size=60&order_by=d_rating&installs=10000&page=1", null, new ICallBack() {
            @Override
            public void onErrorToken() {

            }

            @Override
            public void onFailed(IOException e) {

            }

            @Override
            public void onResponse(String response, boolean isSuccessful) {
                // do something here
                final Appsxyz result = new Gson().fromJson(response, Appsxyz.class);
                if (result != null) {
                    getView().loadChild2(result.getResult());

                }


                // and then callback to the UI thread

            }
        });
        APIClient.getInstance().execGet("http://appsxyz.com/api/apk/grossing/?page=1&size=12&installs=1000", null, new ICallBack() {
            @Override
            public void onErrorToken() {

            }

            @Override
            public void onFailed(IOException e) {

            }

            @Override
            public void onResponse(String response, boolean isSuccessful) {
                // do something here
                final Appsxyz result = new Gson().fromJson(response, Appsxyz.class);
                if (result != null) {
                    getView().loadChild3(result.getResult());

                }


                // and then callback to the UI thread

            }
        });
        APIClient.getInstance().execGet("http://appsxyz.com/api/apk/gonefree/?page=1&size=12", null, new ICallBack() {
            @Override
            public void onErrorToken() {

            }

            @Override
            public void onFailed(IOException e) {

            }

            @Override
            public void onResponse(String response, boolean isSuccessful) {
                // do something here
                final Appsxyz result = new Gson().fromJson(response, Appsxyz.class);
                if (result != null) {
                    getView().loadChild4(result.getResult());

                }


                // and then callback to the UI thread

            }
        });

        List<AppModel> app = new ArrayList<>();
        AppModel a = new AppModel();
        AppModel.SourceBean a1 = new AppModel.SourceBean();
        a1.setThumbnail("https://image.winudf.com/v2/image/anAuY28udHJhbnNsaW1pdC5jYXN0bGVfYmFubmVyXzE1MjQyNzM2MzhfMDUx/banner.jpg?w=850&fakeurl=1&type=.jpg");
        a1.setTitle("Craft Warriors");
        a.setSource(a1);
        AppModel b = new AppModel();
        AppModel.SourceBean b1 = new AppModel.SourceBean();
        b1.setThumbnail("https://image.winudf.com/v2/image/Z2xvd2luZ2V5ZS5weXJhbWlkX3NvbGl0YWlyZV9hbmNpZW50X2VneXB0X2Jhbm5lcl8xNTIxODgyNDYwXzA2OA/banner.jpg?w=850&fakeurl=1&type=.jpg");
        b1.setTitle("Harry Potter: Hogwarts Mystery");
        b.setSource(b1);
        app.add(a);
        app.add(b);

        getView().loadChild5(app);
    }

    @Override
    public void getListCates() {
        String[] array = new String[]{"Game", "Sport", "Funny", "English", "Education", "Music",
                "Language", "Star", "Food", "Cooking", "Car"};
        getView().updateListCates(Arrays.asList(array));
    }
}
