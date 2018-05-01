package com.paditech.mvpbase.screen.main;

import android.widget.Toast;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.mvp.activity.MVPActivity;

/**
 * Created by Nha Nha on 9/19/2017.
 */

public class MainActivity extends MVPActivity<MainContact.PresenterViewOps> implements MainContact.ViewOps {



    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView() {

        getPresenter().getInfo();

    }

    @Override
    protected Class<? extends ActivityPresenter> onRegisterPresenter() {
        return MainPresenter.class;
    }

    @Override
    public void setview() {

    }
}
