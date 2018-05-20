package com.paditech.mvpbase.screen.apkManage;

import android.content.pm.ApplicationInfo;

import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenterViewOps;
import com.paditech.mvpbase.common.mvp.activity.ActivityViewOps;
import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenterViewOps;
import com.paditech.mvpbase.common.mvp.fragment.FragmentViewOps;

import java.util.List;

/**
 * Created by hung on 4/14/2018.
 */

public interface ApkContact {

    interface ViewOps extends ActivityViewOps{
        void loadApk(List<ApplicationInfo> packages);
        void loadLikeApp(List<AppModel> app);
    }

    interface PresenterViewOps extends ActivityPresenterViewOps{
        void getApkInfo();
    }
}
