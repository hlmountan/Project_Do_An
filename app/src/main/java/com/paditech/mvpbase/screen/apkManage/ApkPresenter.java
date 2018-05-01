package com.paditech.mvpbase.screen.apkManage;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenter;

import java.util.List;

/**
 * Created by hung on 4/14/2018.
 */

public class ApkPresenter extends FragmentPresenter<ApkContact.ViewOps> implements ApkContact.PresenterViewOps {
    @Override
    public void getApkInfo() {
        final PackageManager pm = getView().getApplicationContext().getPackageManager();
//get a list of installed apps.
        List<ApplicationInfo> packages =  pm.getInstalledApplications(PackageManager.GET_META_DATA);

        getView().loadApk(packages);
    }
}
