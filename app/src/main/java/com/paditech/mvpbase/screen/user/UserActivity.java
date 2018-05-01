package com.paditech.mvpbase.screen.user;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.mvp.activity.MVPActivity;
import com.paditech.mvpbase.screen.setting.SettingActivity;

import butterknife.BindView;

/**
 * Created by hung on 4/30/2018.
 */

public class UserActivity extends MVPActivity<UserContact.PresenterViewOps> implements UserContact.ViewOps,View.OnClickListener {
    @BindView(R.id.btn_return)
    Button btn_return;
    @BindView(R.id.btn_setting)
    Button btn_setting;
    @Override
    protected int getContentView() {
        return R.layout.act_user;
    }

    @Override
    protected void initView() {

        btn_return.setOnClickListener(this);
        btn_setting.setOnClickListener(this);
    }

    @Override
    protected Class<? extends ActivityPresenter> onRegisterPresenter() {
        return UserPresenter.class;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_return:
                finish();
                break;
            case  R.id.btn_setting:
                this.startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }
}
