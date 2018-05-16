package com.paditech.mvpbase.screen.detail;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.paditech.mvpbase.common.event.ApkFileInfoEvent;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.model.AppPriceHistory;
import com.paditech.mvpbase.common.model.Appsxyz;
import com.paditech.mvpbase.common.model.Cmt;
import com.paditech.mvpbase.common.model.Notification;
import com.paditech.mvpbase.common.model.UserProfile;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.service.APIClient;
import com.paditech.mvpbase.common.service.ICallBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hung on 4/13/2018.
 */

public class DetailPresenter extends ActivityPresenter<DetailContact.ViewOps> implements DetailContact.PresenterViewOps {
    @Override
    public void cURLFromApi(String appid, int isHistory) {
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
                if (app != null) {
                    //return something by call back to UI thread
                    getView().setAppInfo(app);

                }


            }
        });

        if (isHistory == 1) {
            APIClient.getInstance().execGet("https://appsxyz.com/api/apk/price_history/?appid=" + appid, null, new ICallBack() {
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
        String url = href + "&token=" + jwt + "&x=" + x + "&y=" + y;
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
    public boolean isInstall(String appid) {
        boolean check = false;
        final PackageManager pm = getView().getApplicationContext().getPackageManager();
//get a list of installed apps.
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (int i = 0; i < packages.size(); i++) {
            if (packages.get(i).packageName.equals(appid)) {
                check = true;
                System.out.println(" app  installed");
                break;
            }
        }
        return check;
    }

    @Override
    public void getUserFollowApp() {
        FirebaseDatabase.getInstance().getReference().child("user").
                child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);

                            if (userProfile.getFollowApp() != null)
                                getView().setFollowApp(userProfile.getFollowApp());
                            else getView().setFollowApp(new ArrayList<ArrayList<String>>());

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void updateFollowApp(ArrayList<ArrayList<String>> listApp) {
        FirebaseDatabase.getInstance().getReference().child("user").
                child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("followApp").setValue(listApp);

    }

    @Override
    public void pushCmt(final Cmt cmt, final AppModel.SourceBean ownApp) {

        //add notify
        FirebaseDatabase.getInstance().getReference().child("apk").child(cmt.getAppid().
                replace(".","-")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    ApkFileInfoEvent apk = dataSnapshot.getValue(ApkFileInfoEvent.class);
                    // app co thang nao follow hay k
                    if (apk.getNotification() != null){
                        for (final ArrayList<String> usernoti: apk.getNotification()) {
                            //lay user  de them notify
                            FirebaseDatabase.getInstance().getReference().child("user").child(usernoti.get(0)).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue() != null){
                                        ArrayList<Notification> notify = new ArrayList<>();
                                        Notification noti = new Notification(3,cmt.getTitle(),
                                                cmt.getContent(),cmt.getDate(),cmt.getAppid(),false,"","");
                                        noti.setAppAvar(ownApp.getCover());
                                        UserProfile user = dataSnapshot.getValue(UserProfile.class);
                                        // kiem tra da co notify hay chua
                                        if (user.getNotify() !=null){
                                            notify = user.getNotify();
                                            notify.add(noti);

                                        }else notify.add(noti);
                                       // update notify
                                        FirebaseDatabase.getInstance().getReference().child("user").child(usernoti.get(0)).child("notify").setValue(notify);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // add nitify for author
        if (ownApp.isUserUpload()){
            FirebaseDatabase.getInstance().getReference().child("user").child(ownApp.getDevId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null){
                        ArrayList<Notification> notify = new ArrayList<>();
                        Notification noti = new Notification(3,cmt.getTitle(),
                                cmt.getContent(),cmt.getDate(),cmt.getAppid(),false,"","");
                        noti.setAppAvar(ownApp.getCover());
                        noti.setOwnApp(true);
                        UserProfile user = dataSnapshot.getValue(UserProfile.class);
                        // kiem tra da co notify hay chua
                        if (user.getNotify() !=null){
                            notify = user.getNotify();
                            notify.add(noti);

                        }else notify.add(noti);
                        // update notify
                        FirebaseDatabase.getInstance().getReference().child("user").child(ownApp.getDevId()).child("notify").setValue(notify);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        // add cmt
        FirebaseDatabase.getInstance().getReference().child("cmt").push().setValue(cmt);
    }

    @Override
    public void getUserCmt(String appid, boolean isFirebase) {

//        if (isFirebase)
            FirebaseDatabase.getInstance().getReference().child("cmt").
                    orderByChild("appid").equalTo(appid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        final List<Cmt> cmts = new ArrayList<>();
                        for (DataSnapshot a : dataSnapshot.getChildren()) {
                            final Cmt cmt = a.getValue(Cmt.class);
                            cmts.add(cmt);
                        }
                        getView().setCmt(cmts);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
//        else
//            APIClient.getInstance().execGet("https://apprevi.com/api/apk/review-daily?appid=" + appid + "&time=7&star=5&language=en-US&page=1&size=6", null, new ICallBack() {
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
//                    final CmtGp cmt = new Gson().fromJson(response, CmtGp.class);
//                    if (cmt != null) {
//                        //return something by call back to UI thread
////                    getView().setCmt(cmt.getComments());
//
//                    }
//
//
//                }
//            });

    }

    @Override
    public void notify(final AppModel.SourceBean app,int status) {
        final ArrayList<ArrayList<String>> notify = new ArrayList<>();
        final ArrayList<String> noti = new ArrayList<>();
        noti.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
        noti.add(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString());
        noti.add(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        notify.add(noti);
        // check user upload hay khong
        if (status == 1){
            if (app.isUserUpload()) {

                //lay thong tin apk
                FirebaseDatabase.getInstance().getReference().child("apk").child(app.getAppid()).
                        addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            ApkFileInfoEvent apk = dataSnapshot.getValue(ApkFileInfoEvent.class);
                            // da co ai follow hay chua
                            apk.setAppid(app.getAppid());
                            if (apk.getNotification() == null) {
                                apk.setNotification(notify);

                            } else {
                                ArrayList<ArrayList<String>> notifyInner = apk.getNotification();
                                notifyInner.add(noti);
                                apk.setNotification(notifyInner);
                            }
                            FirebaseDatabase.getInstance().getReference().child("apk").child(app.getAppid()).setValue(apk);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            } else {
                // checnk  exeis
                FirebaseDatabase.getInstance().getReference().child("apk").child(app.getAppid().
                        replace(".", "-")).addListenerForSingleValueEvent
                        (new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() == null) {
                            ApkFileInfoEvent apk = new ApkFileInfoEvent();
                            apk.setAppid(app.getAppid().replace(".", "-"));
                            apk.setNotification(notify);
                            FirebaseDatabase.getInstance().getReference().child("apk").
                                    child(app.getAppid().replace(".", "-")).setValue(apk);
                        } else {
                            ApkFileInfoEvent apk = dataSnapshot.getValue(ApkFileInfoEvent.class);
                            ArrayList<ArrayList<String>> notifyInner;
                              if (apk.getNotification() != null){
                                  notifyInner = apk.getNotification();
                              }else notifyInner  = new ArrayList<>();
                            notifyInner.add(noti);
                            FirebaseDatabase.getInstance().getReference().child("apk").child(app.getAppid().
                                    replace(".", "-")).
                                    child("notification").setValue(notifyInner);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        }else {
            // remove follow
            FirebaseDatabase.getInstance().getReference().child("apk").child(app.getAppid().
                    replace(".","-")).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null){
                        ApkFileInfoEvent apk  = dataSnapshot.getValue(ApkFileInfoEvent.class);
                        ArrayList<ArrayList<String>> notify = apk.getNotification();
                        for (ArrayList<String> no: notify) {
                            if (no.get(0).equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                                notify.remove(no);
                                break;
                            }
                        }
                        FirebaseDatabase.getInstance().getReference().child("apk").child(app.getAppid().
                                replace(".","-")).child("notification").setValue(notify);

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }



    }

}
