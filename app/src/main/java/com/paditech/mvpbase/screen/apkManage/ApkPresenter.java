package com.paditech.mvpbase.screen.apkManage;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hung on 4/14/2018.
 */

public class ApkPresenter extends ActivityPresenter<ApkContact.ViewOps> implements ApkContact.PresenterViewOps {
    @Override
    public void getApkInfo() {
        final PackageManager pm = getView().getApplicationContext().getPackageManager();
        //get a list of installed apps.
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        List<ApplicationInfo> packages_coppy = new ArrayList<>();
        //        System.out.println(pm.getpackage+" packer");
        System.out.println(packages.size() + "before");

        for (int i = 0; i < packages.size(); i++) {
            if (packages.get(i).packageName.indexOf("google") >= 0) {
            } else if (packages.get(i).packageName.indexOf("ndroid") >= 0) {

            } else packages_coppy.add(packages.get(i));
        }

        System.out.println(packages_coppy.size() + "after");

        getView().loadApk(packages_coppy);
    }
}
