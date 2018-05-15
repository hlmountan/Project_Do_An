package com.paditech.mvpbase.screen.updateApkActivity;

import com.google.firebase.database.FirebaseDatabase;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;

import java.util.Arrays;

/**
 * Created by hung on 5/15/2018.
 */

public class UpdateApkPresenter extends ActivityPresenter<UpdateApkContact.ViewOps> implements
        UpdateApkContact.PresenterViewOps {

    @Override
    public void getListCates() {
        String[] cates = new String[]{"Game", "Sport", "Funny", "English", "Education", "Music",
                "Language", "Star", "Food", "Cooking", "Car"};
        String[] requires = new String[]{"Android 2.0 – 2.1 ", "Android 2.2 – 2.2.3", "Android 2.3 – 2.3.7",
                "Android 3.0 – 3.2.6", "Android 4.0 – 4.0.4", "Android 4.1 – 4.3.1",
                "Android 4.4 – 4.4.4", "Android 5.0 – 5.1.1", "Android 6.0 – 6.0.1", "Android 7.0 – 7.1.2", "Android 8.0 – 8.1", "Android 9"};
        getView().updateListCates(Arrays.asList(cates),Arrays.asList(requires));
    }

    @Override
    public void updateApk(AppModel.SourceBean sourceBean) {
        FirebaseDatabase.getInstance().getReference().child("apk").child(sourceBean.getAppid()).
                child("title").setValue(sourceBean.getTitle());
        FirebaseDatabase.getInstance().getReference().child("apk").child(sourceBean.getAppid()).
                child("description").setValue(sourceBean.getDescription());
        FirebaseDatabase.getInstance().getReference().child("apk").child(sourceBean.getAppid()).
                child("cate").setValue(sourceBean.getCategory());
        FirebaseDatabase.getInstance().getReference().child("apk").child(sourceBean.getAppid()).
                child("policy").setValue(sourceBean.getPolicy());
        FirebaseDatabase.getInstance().getReference().child("apk").child(sourceBean.getAppid()).
                child("age").setValue(sourceBean.getContentrating());
        FirebaseDatabase.getInstance().getReference().child("apk").child(sourceBean.getAppid()).
                child("require").setValue(sourceBean.getRequire());
    }

    @Override
    public void updateAppStatus(AppModel.SourceBean sourceBean) {
        FirebaseDatabase.getInstance().getReference().child("apk").child(sourceBean.getAppid()).
                child("status").setValue(sourceBean.getStatus());
    }

    @Override
    public void pushNotify() {

    }
}
