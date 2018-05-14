package com.paditech.mvpbase.screen.home;

import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenterViewOps;
import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenterViewOps;
import com.paditech.mvpbase.common.mvp.fragment.FragmentViewOps;

import java.util.List;

/**
 * Created by hung on 4/13/2018.
 */

public interface HomeContact {

    interface ViewOsp extends FragmentViewOps{
        void loadSlider();
        void loadChildOnSale(List<AppModel> result);
        void loadChildGameGrossing(List<AppModel> result);
        void loadChildAllGrossing(List<AppModel> result);
        void loadChildUserUpload(List<AppModel> result);
        void loadChild5(List<AppModel> result);

        void reloadSlider();
        void reloadListApp();
        void updateListCates(List<String> strings);



    }
    interface PresenterViewOsp extends ActivityPresenterViewOps, FragmentPresenterViewOps {

        void getAppFromApi();
        void getListCates();
        void getUserApk();
    }
}
