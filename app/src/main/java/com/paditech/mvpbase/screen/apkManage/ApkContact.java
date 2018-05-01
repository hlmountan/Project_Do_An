package com.paditech.mvpbase.screen.apkManage;

import android.content.pm.ApplicationInfo;

import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenterViewOps;
import com.paditech.mvpbase.common.mvp.fragment.FragmentViewOps;

import java.util.List;

/**
 * Created by hung on 4/14/2018.
 */

public interface ApkContact {

    interface ViewOps extends FragmentViewOps{
        void loadApk(List<ApplicationInfo> packages);
    }

    interface PresenterViewOps extends FragmentPresenterViewOps{
        void getApkInfo();
    }
}
