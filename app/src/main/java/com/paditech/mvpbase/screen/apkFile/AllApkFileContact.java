package com.paditech.mvpbase.screen.apkFile;

import com.paditech.mvpbase.common.mvp.activity.ActivityPresenterViewOps;
import com.paditech.mvpbase.common.mvp.activity.ActivityViewOps;

import java.util.ArrayList;

/**
 * Created by hung on 5/10/2018.
 */

public interface AllApkFileContact {
    interface ViewOps extends ActivityViewOps{
        void loadApkFile(ArrayList<ArrayList<String>> listApk);
    }
    interface PresenterViewOps extends ActivityPresenterViewOps{
        void getListApkFile();
    }
}
