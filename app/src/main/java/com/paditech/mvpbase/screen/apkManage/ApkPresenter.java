package com.paditech.mvpbase.screen.apkManage;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.google.gson.Gson;
import com.paditech.mvpbase.common.model.Appsxyz;
import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenter;
import com.paditech.mvpbase.common.service.APIClient;
import com.paditech.mvpbase.common.service.ICallBack;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
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
        List<ApplicationInfo> packages_coppy = new ArrayList<>();
//        System.out.println(pm.getpackage+" packer");
        System.out.println(packages.size()+"before");

        for (int i=0;i<packages.size();i++){
            if (packages.get(i).packageName.indexOf("google")>=0){
            }else if (packages.get(i).packageName.indexOf("ndroid")>=0){

            }else packages_coppy.add(packages.get(i));
        }

        System.out.println(packages_coppy.size()+"after");

        getView().loadApk(packages_coppy);
        APIClient.getInstance().execGet("http://appsxyz.com/api/apk/search_related/?q=" + URLEncoder.encode("Free Fire") + "&page=1&size=20", null, new ICallBack() {
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
                    getView().loadLikeApp(result.getResult());
                }
            }
        });
    }
}
