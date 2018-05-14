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
        void onUploadFileSuccess(String url);
        void onUploadFileFalse(@NonNull Exception exception);
        void onUploading(String percent);
        void onAvarLoadSuccess(String url);
        void onScreenshotLoadSuccess(ArrayList<String> url);
        void uploadappid(String appid);
        void visibleSuccessUpload();
    }

    interface  PresenterViewOps extends FragmentPresenterViewOps{
        void uploadApk(String path);
        void createNewApk( ApkFileInfoEvent apk);
        void updateAvar(String path);
        void updateScreenshot(ArrayList<String> path);
        void updateApkAvar(final ApkFileInfoEvent apk);
        void updateApkScreenshot(final ApkFileInfoEvent apk);
    }
}
