package com.paditech.mvpbase.screen.home;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.paditech.mvpbase.common.event.ApkFileInfoEvent;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.model.Appsxyz;
import com.paditech.mvpbase.common.model.UserProfile;
import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenter;
import com.paditech.mvpbase.common.service.APIClient;
import com.paditech.mvpbase.common.service.ICallBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hung on 4/13/2018.
 */

public class HomePresenter extends FragmentPresenter<HomeContact.ViewOsp> implements HomeContact.PresenterViewOsp {

    @Override
    public void getAppFromApi() {
        //onsale
        APIClient.getInstance().execGet("http://appsxyz.com/api/apk/googleplay-onsale/?page=1&size=6&installs=1000", null, new ICallBack() {
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
                    getView().loadChildOnSale(result.getResult());

                }


                // and then callback to the UI thread

            }
        });
        //action grossing
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
                    getView().loadChildGameGrossing(result.getResult());

                }


                // and then callback to the UI thread

            }
        });
       // all grossing
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
                    getView().loadChildAllGrossing(result.getResult());

                }


                // and then callback to the UI thread

            }
        });


        // slider home
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

    @Override
    public void getUserApk() {
        FirebaseDatabase.getInstance().getReference().child("apk").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.getValue() != null) {

                        final List<AppModel> listApk = new ArrayList<>();
                        for (DataSnapshot a : dataSnapshot.getChildren()) {

                            final ApkFileInfoEvent apk = a.getValue(ApkFileInfoEvent.class);
                            if (!apk.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                apk.setAppid(a.getKey());

                                //get user name
                                FirebaseDatabase.getInstance().getReference().child("user").
                                        child(apk.getUid()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.getValue() != null) {
                                            UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                                            apk.setOfferby(userProfile.getName());
                                            AppModel app = new AppModel(apk);
                                            listApk.add(app);
                                            getView().loadChildUserUpload(listApk);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        System.out.println(databaseError + "eror day ne");
                                    }
                                });
                            }


                        }
                    }
                }catch (Exception e){
                    System.out.println(e+"error");
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
