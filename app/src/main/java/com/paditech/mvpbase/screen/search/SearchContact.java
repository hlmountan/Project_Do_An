package com.paditech.mvpbase.screen.search;

import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenterViewOps;
import com.paditech.mvpbase.common.mvp.fragment.FragmentViewOps;

import java.util.List;

/**
 * Created by nhapcs on 4/20/18.
 */

public interface SearchContact {
    interface ViewOps extends FragmentViewOps {
        void updateApps(List<AppModel> appModels);
        void addApps(List<AppModel> appModels);

        void onSearching();

        void onSearchDone();

        void setSearchResult(List<AppModel> listApp);

        void loadMore(List<AppModel> listApp);

        void updateListCates(List<String> strings);
    }

    interface PresenterViewOps extends FragmentPresenterViewOps {
        void getMoreApp(int page, String api);
        void getListCates();
        void cURLSearchData(int mPage, String s);
    }
}
