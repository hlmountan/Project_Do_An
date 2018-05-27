package com.paditech.mvpbase.screen.updateApkActivity;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.gravity.IChildGravityResolver;
import com.google.firebase.database.FirebaseDatabase;
import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.base.BaseDialog;
import com.paditech.mvpbase.common.model.AppModel;
import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;
import com.paditech.mvpbase.common.mvp.activity.MVPActivity;
import com.paditech.mvpbase.common.utils.ImageUtil;
import com.paditech.mvpbase.screen.adapter.ChipCateAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * Created by hung on 5/15/2018.
 */

public class UpdateApkActivity extends MVPActivity<UpdateApkContact.PresenterViewOps> implements
        UpdateApkContact.ViewOps, View.OnClickListener {

    @BindView(R.id.btn_apply)
    Button apply;
    @BindView(R.id.btn_cancel)
    Button cancel;
    @BindView(R.id.img_avar)
    ImageView avar;
    @BindView(R.id.tv_apk_info)
    TextView apkInfo;
    @BindView(R.id.et_title)
    EditText title;
    @BindView(R.id.et_des)
    EditText des;
    @BindView(R.id.tv_cate)
    TextView cate;
    @BindView(R.id.recycler_view_list_cate)
    RecyclerView listCate;
    @BindView(R.id.et_policy)
    EditText policy;
    @BindView(R.id.et_age)
    EditText age;
    @BindView(R.id.tv_require)
    TextView require;
    @BindView(R.id.tv_status_apk)
    TextView statusApk;
    @BindView(R.id.tv_direction)
    TextView direction;
    @BindView(R.id.btn_public_app)
    Button publicApp;
    @BindView(R.id.view_apk_info)
    LinearLayout info;
    @BindView(R.id.recycler_view_list_require)
    RecyclerView listRequire;
    @BindView(R.id.btn_save)
    Button save;
    @BindView(R.id.btn_update_version)
    Button updateApp;
    @BindView(R.id.btn_pending)
    Button pending;

    ChipCateAdapter mListCateAdapter;
    ChipCateAdapter mListrequireAdapter;
    AppModel.SourceBean app;

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void setApkInfo(AppModel.SourceBean sourceBean) {
        app = sourceBean;
        setInfoApp(sourceBean);

         /*
    *** status: 0: public
    *           1: pending
    *           2: missing info
    *           3: unpublic
    *           4: update
    *

     */
        switch (sourceBean.getStatus()) {
            case 0:
                statusApk.setText(R.string.public_apk);
                publicApp.setVisibility(View.GONE);
                updateApp.setVisibility(View.VISIBLE);
                pending.setVisibility(View.VISIBLE);
                direction.setText(R.string.public_direction);
                break;
            case 1:
                statusApk.setText(getResources().getText(R.string.pending));
                break;
            case 2:
                statusApk.setText(R.string.missing_info);
                break;
            case 3:
                break;
            default:
                break;
        }

    }

    @Override
    protected int getContentView() {
        return R.layout.act_update_apk;
    }

    @Override
    protected void initView() {
        setUpCateAdapter();
        apply.setOnClickListener(this);
        cancel.setOnClickListener(this);
        apkInfo.setOnClickListener(this);
        publicApp.setOnClickListener(this);
        save.setOnClickListener(this);
        updateApp.setOnClickListener(this);
        pending.setOnClickListener(this);
    }

    @Override
    protected Class<? extends ActivityPresenter> onRegisterPresenter() {
        return UpdateApkPresenter.class;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_apply:
                finish();
                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.tv_apk_info:
                if (info.getVisibility() == View.GONE) {
                    listRequire.setVisibility(View.VISIBLE);
                    listCate.setVisibility(View.VISIBLE);
                    info.setVisibility(View.VISIBLE);
                } else info.setVisibility(View.GONE);
                break;
            case R.id.btn_public_app:
                if (app.getStatus() == 2) {
                    showConfirmDialog(getString(R.string.missing_info), new BaseDialog.OnPositiveClickListener() {
                        @Override
                        public void onPositiveClick() {
                            info.setVisibility(View.VISIBLE);
                        }
                    }, null);
                } else
                    showConfirmDialog(getString(R.string.public_ask), new BaseDialog.OnPositiveClickListener() {
                        @Override
                        public void onPositiveClick() {
                            app.setStatus(0);
                            statusApk.setText(R.string.public_apk);
                            getPresenter().updateAppStatus(app);
                            publicApp.setVisibility(View.GONE);
                            updateApp.setVisibility(View.VISIBLE);
                            pending.setVisibility(View.VISIBLE);
                            getPresenter().pushNotify(app);
                        }
                    }, null);
                break;
            case R.id.btn_save:
                //save info to app
                if (!checkInfo()) {
                    showConfirmDialog(getString(R.string.saveinfo_false), new BaseDialog.OnPositiveClickListener() {
                        @Override
                        public void onPositiveClick() {
                            setApkInfo(app);
                        }
                    }, null);
                } else {
                    app.setTitle(title.getText().toString());
                    app.setDescription(des.getText().toString());
                    app.setCategory(cate.getText().toString());
                    app.setPolicy(policy.getText().toString());
                    app.setContentrating(age.getText().toString());
                    app.setRequire(require.getText().toString());
                    app.setStatus(1);
                    listCate.setVisibility(View.GONE);
                    listRequire.setVisibility(View.GONE);
                    showAlertDialog(getString(R.string.finish_saving));
                    apply.setVisibility(View.VISIBLE);
                    getPresenter().updateApk(app);
                }
                break;
            case R.id.btn_update_version:
                // if app is public . you can up date new version for it

                break;
            case R.id.btn_pending:
                // change app to pending  or unpublic
                FirebaseDatabase.getInstance().getReference().child("apk").child(app.getAppid()).
                        child("status").setValue(Integer.parseInt("1"));
                publicApp.setVisibility(View.VISIBLE);
                updateApp.setVisibility(View.GONE);
                pending.setVisibility(View.GONE);

                break;
        }
    }

    public boolean checkInfo() {
        if (!title.getText().toString().equals("") && !des.getText().toString().equals("") &&
                !policy.getText().toString().equals("") && !age.getText().toString().equals("")){
            if (!cate.getText().toString().equals(getText(R.string.title_category))&&
                    !require.getText().toString().equals(getString(R.string.android_require)))
                return true;
        }
//            if (!cate.getText().toString().equals(R.string.title_category) && !require.getText().toString().equals(R.string.android_require))
//                return true;
        return false;
    }

    private void setUpCateAdapter() {

        mListCateAdapter = new ChipCateAdapter();
        mListrequireAdapter = new ChipCateAdapter();
        mListrequireAdapter.setmListener(new ChipCateAdapter.OnSelectCateListener() {
            @Override
            public void selectCate(String string) {
                require.setText(string);
            }
        });
        mListCateAdapter.setmListener(new ChipCateAdapter.OnSelectCateListener() {

            @Override
            public void selectCate(String string) {
                //do somethings here
                cate.setText(string);
            }
        });
        ChipsLayoutManager cates = ChipsLayoutManager.newBuilder(getActivityContext())
                //set vertical gravity for all items in a row. Default = Gravity.CENTER_VERTICAL
                .setChildGravity(Gravity.TOP)
                //whether RecyclerView can scroll. TRUE by default
                .setScrollingEnabled(true)
                //set maximum views count in a particular row
                .setMaxViewsInRow(4)
                //set gravity resolver where you can determine gravity for item in position.
                //This method have priority over previous one
                .setGravityResolver(new IChildGravityResolver() {
                    @Override
                    public int getItemGravity(int position) {
                        return Gravity.CENTER;
                    }
                })
                //a layoutOrientation of layout manager, could be VERTICAL OR HORIZONTAL. HORIZONTAL by default
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .build();
        ChipsLayoutManager requires = ChipsLayoutManager.newBuilder(getActivityContext())
                //set vertical gravity for all items in a row. Default = Gravity.CENTER_VERTICAL
                .setChildGravity(Gravity.TOP)
                //whether RecyclerView can scroll. TRUE by default
                .setScrollingEnabled(true)
                //set maximum views count in a particular row
                .setMaxViewsInRow(4)
                //set gravity resolver where you can determine gravity for item in position.
                //This method have priority over previous one
                .setGravityResolver(new IChildGravityResolver() {
                    @Override
                    public int getItemGravity(int position) {
                        return Gravity.CENTER;
                    }
                })
                //a layoutOrientation of layout manager, could be VERTICAL OR HORIZONTAL. HORIZONTAL by default
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .build();

        listRequire.setLayoutManager(requires);
        listRequire.setAdapter(mListrequireAdapter);
        listRequire.setNestedScrollingEnabled(false);

        listCate.setLayoutManager(cates);
        listCate.setAdapter(mListCateAdapter);
        listCate.setNestedScrollingEnabled(false);

        getPresenter().getListCates();


    }

    @Override
    public void updateListCates(List<String> cates, List<String> requires) {
        mListCateAdapter.setmListCates(cates);
        mListrequireAdapter.setmListCates(requires);
    }

    void setInfoApp(AppModel.SourceBean sourceBean) {
        ImageUtil.loadImageRounded(this, sourceBean.getCover(), avar, R.drawable.events_placeholder, R.drawable.image_placeholder_500x500, 20);
        title.setText(sourceBean.getTitle());

        if (app.getDescription() != null) {
            des.setText(app.getDescription());
        } else des.setHint(R.string.des);

        if (app.getCategory() != null) {
            cate.setText(app.getCategory());
        } else cate.setText(R.string.title_category);

        if (app.getPolicy() != null) {
            policy.setHint(app.getPolicy());
        } else policy.setHint(R.string.your_policy);

        if (app.getContentrating() != null) {
            age.setHint(app.getContentrating());
        } else age.setHint(R.string.age);

        if (app.getRequire() != null) {
            require.setText(app.getRequire());
        } else require.setText(R.string.android_require);
    }

}
