package com.paditech.mvpbase.screen.main;

import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;

/**
 * Created by Nha Nha on 9/19/2017.
 */

public class MainPresenter extends ActivityPresenter<MainContact.ViewOps> implements MainContact.PresenterViewOps {
    @Override
    public void getInfo() {
        getView().setview();

    }
}
