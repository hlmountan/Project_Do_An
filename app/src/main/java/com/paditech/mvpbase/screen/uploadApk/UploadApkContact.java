package com.paditech.mvpbase.screen.uploadApk;

import android.support.annotation.NonNull;

import com.paditech.mvpbase.common.event.ApkFileInfoEvent;
import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenterViewOps;
import com.paditech.mvpbase.common.mvp.fragment.FragmentViewOps;

import java.util.ArrayList;

/**
 * Created by hung on 5/9/2018.
 */

public interface UploadApkContact {
    interface ViewOps extends FragmentViewOps{
        void onUploadFileFalse(@NonNull Exception exception);
        void uploadappid(String appid);
        void onProgressAvatar(int percent);
        void onProgressScreens(int percent, String doneCont);
        void onProgressAPK(int percent);
        void onFinishAll();
        void isDev(Boolean check);
        void setApkState(Boolean state);
    }

    interface  PresenterViewOps extends FragmentPresenterViewOps{
        void uploadApk(String path);
        void createNewApk( ApkFileInfoEvent apk);
        void updateAvar(String path);
        void updateScreenshot(ArrayList<String> path);
        String getAppId(String filePath);
        void checkSuccessAll();
        void checkDev();
        void checkApk(String appid);
    }
}
