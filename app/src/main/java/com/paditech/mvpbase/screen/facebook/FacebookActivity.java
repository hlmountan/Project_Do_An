package com.paditech.mvpbase.screen.facebook;

import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.mvp.activity.MVPActivity;

/**
 * Created by hung on 5/6/2018.
 */

public class FacebookActivity extends MVPActivity{
    @Override
    protected int getContentView() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected Class<? extends ActivityPresenter> onRegisterPresenter() {
        return null;
    }
}
