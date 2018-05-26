package com.paditech.mvpbase.screen.uploadApk;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.base.BaseDialog;
import com.paditech.mvpbase.common.event.ApkFileInfoEvent;
import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenter;
import com.paditech.mvpbase.common.mvp.fragment.MVPFragment;
import com.paditech.mvpbase.common.utils.CommonUtil;
import com.paditech.mvpbase.common.utils.ImageUtil;
import com.paditech.mvpbase.common.utils.StringUtil;
import com.paditech.mvpbase.common.utils.get_image.GetImageManager;
import com.paditech.mvpbase.common.view.ValueProgressBar;
import com.paditech.mvpbase.screen.adapter.RecyclerViewListScreenshortUploadAdapter;
import com.paditech.mvpbase.screen.apkFile.AllApkFileActivity;
import com.paditech.mvpbase.screen.login.LoginActivity;
import com.paditech.mvpbase.screen.profile.ProfileActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hung on 5/9/2018.
 */

public class UploadApkFragment extends MVPFragment<UploadApkContact.PresenterViewOps> implements
        UploadApkContact.ViewOps, View.OnClickListener, View.OnFocusChangeListener {
    final int APKFILE = 1;
    final int LISTSCREESHOT = 3;
    final int AVARTAR = 2;


    @BindView(R.id.btn_next)
    View btn_next;
    @BindView(R.id.tv_state)
    TextView state;
    @BindView(R.id.ln_step1)
    View step1;
    @BindView(R.id.ln_step2)
    View step2;
    @BindView(R.id.ln_step3)
    View step3;
    @BindView(R.id.ln_step4)
    View step4;
    @BindView(R.id.ln_final)
    View ln_final;
    @BindView(R.id.img_avar)
    ImageView avar;
    @BindView(R.id.checkbox_agreement)
    CheckBox agreement;
    @BindView(R.id.btn_add_image)
    Button btn_add_image;
    @BindView(R.id.btn_upload_local)
    Button btn_upload_local;
    @BindView(R.id.btn_upload_driver)
    Button btn_upload_driver;
    @BindView(R.id.tv_step)
    TextView tvStep;
    @BindView(R.id.tv_title)
    TextView titleApk;
    @BindView(R.id.tv_size)
    TextView sizeApk;
    @BindView(R.id.tv_date)
    TextView lastModify;
    @BindView(R.id.ln_apk_file)
    LinearLayout ln_apk_file;
    @BindView(R.id.tv_percen)
    TextView tv_percen;
    @BindView(R.id.et_title)
    EditText et_title;
    @BindView(R.id.tv_user)
    TextView tv_user;
    @BindView(R.id.btn_previous)
    View btnPrevious;
    @BindView(R.id.btn_reset)
    View btnReset;
    @BindView(R.id.recycler_view_img_screenshot)
    RecyclerView recycler_view_img_screenshot;
    @BindView(R.id.tv_upload_apk)
    TextView tvUploadApk;
    @BindView(R.id.tv_progress_apk)
    TextView tvProgressApk;
    @BindView(R.id.progress_bar_apk)
    ValueProgressBar progressBarApk;
    @BindView(R.id.tv_progress_avatar)
    TextView tvProgressAvatar;
    @BindView(R.id.progress_bar_avatar)
    ValueProgressBar progressBarAvatar;
    @BindView(R.id.tv_progress_screen)
    TextView tvProgressScreen;
    @BindView(R.id.progress_bar_screen)
    ValueProgressBar progressBarScreen;

    Boolean apkState = false;
    int step = 1;
    String path = "";
    Boolean isDev = false;
    RecyclerViewListScreenshortUploadAdapter listScreenshortUploadAdapter;
    GetImageManager getImageManager;
    private ApkFileInfoEvent apkFile = new ApkFileInfoEvent();



    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    private Activity act;

    public Activity getAct() {
        return act;
    }

    public void setAct(Activity act) {
        this.act = act;
    }

    public static UploadApkFragment getInstance(Activity act) {
        UploadApkFragment f = new UploadApkFragment();
        f.setAct(act);
        return f;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            tv_user.setText(getString(R.string.hi) + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        else tv_user.setText(getString(R.string.hi));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetApkFile(ApkFileInfoEvent apk) {
        setApkinfo(apk);
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_upload_apk;
    }

    @Override
    protected void initView(View view) {

        getPresenter().checkDev();
        CommonUtil.dismissSoftKeyboard(view, getActivityReference());
        getImageManager = new GetImageManager(this);

        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            tv_user.setText(getString(R.string.hi) + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        else tv_user.setText(getString(R.string.hi));

        listScreenshortUploadAdapter = new RecyclerViewListScreenshortUploadAdapter(act);
        recycler_view_img_screenshot.setLayoutManager(new GridLayoutManager(act, 3, LinearLayoutManager.VERTICAL, false));
        recycler_view_img_screenshot.setAdapter(listScreenshortUploadAdapter);

        btn_next.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
        btn_upload_local.setOnClickListener(this);
        btn_upload_driver.setOnClickListener(this);
        btn_add_image.setOnClickListener(this);
        step3.setOnClickListener(this);
        agreement.setOnClickListener(this);
        et_title.setOnFocusChangeListener(this);
        changeStep();
    }

    @Override
    protected Class<? extends FragmentPresenter> onRegisterPresenter() {
        return UploadApkPresenter.class;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_previous:
                step--;
                changeStep();
                break;
            case R.id.btn_next:
                if (isDev) {
                    if (agreement.isChecked()) {
                        switch (step) {
                            case 2:
                                if (StringUtil.isEmpty(et_title.getText().toString().trim())) {
                                    showToast(getString(R.string.please_enter_title));
                                    return;
                                }
                                if (StringUtil.isEmpty(path)) {
                                    showToast(getString(R.string.please_choose_apk));
                                    return;
                                }
                                break;
                            case 3:
                                if (StringUtil.isEmpty(apkFile.getAvar())) {
                                    showToast(getString(R.string.please_choose_avatar));
                                    return;
                                }
                                break;
                            case 4:
                                if (apkFile.getScreenshot() == null || apkFile.getScreenshot().isEmpty()) {
                                    showToast(getString(R.string.please_choose_screenshot));
                                    return;
                                }
                                break;
                        }
                        step++;
                        changeStep();
                    }
                } else {
                    showConfirmDialog(getString(R.string.not_dev), new BaseDialog.OnPositiveClickListener() {
                        @Override
                        public void onPositiveClick() {
                            if (FirebaseAuth.getInstance().getCurrentUser() != null)
                                startActivity(new Intent(getActivityContext(), ProfileActivity.class));
                            else startActivity(new Intent(getActivityContext(), LoginActivity.class));
                        }
                    }, null);
                }
                break;
            case R.id.btn_upload_local:
                startActivity(new Intent(act, AllApkFileActivity.class));
                break;
            case R.id.btn_upload_driver:
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), APKFILE);
                break;
            case R.id.ln_step3:
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), AVARTAR);
                avar.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_add_image:
                getImageManager.setSelectType(GetImageManager.SelectType.MULTIPLE);
                getImageManager.onAddImage();
                break;

        }
    }

    private void changeStep() {
        state.setVisibility(View.VISIBLE);
        state.setText(getString(R.string.step_format, step));
        step1.setVisibility(View.GONE);
        step2.setVisibility(View.GONE);
        step3.setVisibility(View.GONE);
        step4.setVisibility(View.GONE);
        ln_final.setVisibility(View.GONE);
        if (step > 1) {
            btnPrevious.setVisibility(View.VISIBLE);
            btnPrevious.setEnabled(true);
        } else {
            btnPrevious.setVisibility(View.INVISIBLE);
            btnPrevious.setEnabled(false);
        }
        if (step < 5) {
            btn_next.setVisibility(View.VISIBLE);
            btn_next.setEnabled(true);
        } else {
            btn_next.setVisibility(View.INVISIBLE);
            btn_next.setEnabled(false);
        }
        switch (step) {
            case 1:
                tvStep.setText(R.string.step_1);
                step1.setVisibility(View.VISIBLE);
                break;
            case 2:
                tvStep.setText(R.string.step_2);
                step2.setVisibility(View.VISIBLE);
                requirePermission();
                break;
            case 3:
                tvStep.setText(R.string.step_3);
                step3.setVisibility(View.VISIBLE);
                break;
            case 4:
                tvStep.setText(R.string.step_4);
                step4.setVisibility(View.VISIBLE);
                break;
            case 5:
                if (apkState){
                    apkFile.setTitle(et_title.getText().toString());
                    getPresenter().createNewApk(apkFile);
                    tvStep.setText(R.string.step_5);
                    ln_final.setVisibility(View.VISIBLE);
                    btn_next.setVisibility(View.GONE);
                    btnPrevious.setVisibility(View.GONE);
                }else showAlertDialog(getString(R.string.exis_apk), new BaseDialog.OnPositiveClickListener() {
                    @Override
                    public void onPositiveClick() {
                        resetToStep1();
                    }
                });


                break;
        }
    }

    @OnClick({R.id.btn_reset})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_reset:
                resetToStep1();
                break;
        }
    }

    private void resetToStep1() {
        step = 1;
        changeStep();
        agreement.setChecked(false);
        agreement.setVisibility(View.VISIBLE);
        ln_final.setVisibility(View.GONE);
        ln_apk_file.setVisibility(View.GONE);
        path = "";
        et_title.setText("");
        ImageUtil.loadImage(act, R.drawable.image_placeholder_500x500, avar);
        listScreenshortUploadAdapter.setListImg(new ArrayList<String>());
        getImageManager.setmListPhotoSelected(new ArrayList<String>());
        apkFile = new ApkFileInfoEvent();
        tvProgressApk.setVisibility(View.VISIBLE);
        tvProgressScreen.setVisibility(View.VISIBLE);
        tvProgressAvatar.setVisibility(View.VISIBLE);
        progressBarApk.setVisibility(View.VISIBLE);
        progressBarScreen.setVisibility(View.VISIBLE);
        progressBarAvatar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case APKFILE:
                try {
                    File file = new File(converUri(data));
                    int file_size = Integer.parseInt(String.valueOf(file.length() / 1024));
                    String name = file.getName();
                    if (name.indexOf("apk") > 0) {
                        String date = String.valueOf(file.lastModified());
                        name = name.replace("_", ".");
                        name = name.replace(".apk", "");
                        apkFile.setAppid(getPresenter().getAppId(apkFile.getPath()));
                        apkFile.setTitle(name);
                        apkFile.setSize(String.valueOf(file_size / 1024));
                        apkFile.setDateModify(date);
                        apkFile.setPath(converUri(data));
                        apkFile.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        setApkinfo(apkFile);
                    } else showToast(getString(R.string.error_file_type));

                } catch (Exception e) {
                    showToast(getString(R.string.error_file_type));
                }

                break;
            case AVARTAR:
                try {
                    Uri targetUri = data.getData();
                    apkFile.setAvar(converUri(data));
                    ImageUtil.loadImage(act, targetUri, avar, R.drawable.events_placeholder, R.drawable.image_placeholder_500x500);
                } catch (Exception ex) {
                    System.out.println(ex);
                }

                break;
            default:
                recycler_view_img_screenshot.setVisibility(View.VISIBLE);
                getImageManager.onActivityResult(requestCode, resultCode, data);
                listScreenshortUploadAdapter.setListImg(getImageManager.getmListPhotoSelected());
                apkFile.setScreenshot(getImageManager.getmListPhotoSelected());
                break;

        }


    }

    public String converUri(Intent data) {
        try {
            Uri uri = data.getData();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            String type = mime.getExtensionFromMimeType(getContext().getContentResolver().getType(uri));
            String path;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                path = ImageUtil.getRealPathFromURI(uri, getContext());
            } else {
                path = ImageUtil.getImagePath(uri, getContext());
            }
            if (StringUtil.isEmpty(path)) {

                path = ImageUtil.getFileFromStream(getContext(), uri, "test_file" + CommonUtil.SIMPLE_DATE_FORMAT.format(new Date()) + "." + type);
            }

            Log.e("PATH", path + "----");
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Err", e.getMessage());
        }
        return "";
    }

    public void setApkinfo(ApkFileInfoEvent apk) {
        ln_apk_file.setVisibility(View.VISIBLE);
        titleApk.setText(apk.getTitle());
        sizeApk.setText(apk.getSize() + " Mb");
        Date mDate = new Date();
        DateFormat mDataFormat = new SimpleDateFormat("MMM-dd-yy");
        mDate.setTime(Long.parseLong(apk.getDateModify()) / 1000);
        lastModify.setText(mDataFormat.format(mDate));
        setPath(apk.getPath());
        apkFile = apk;
        apkFile.setTitle(et_title.getText().toString());
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) act.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        state.setVisibility(View.VISIBLE);
        btn_next.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        apkFile.setTitle(et_title.getText().toString());
        if (!b) {
            hideKeyboard(view);
        } else {
            state.setVisibility(View.GONE);
            btn_next.setVisibility(View.GONE);
        }
    }

    @Override
    public void onUploadFileFalse(@NonNull Exception exception) {
        showToast(exception.getMessage());
    }

    @Override
    public void uploadappid(String appid) {
        apkFile.setAppid(appid);
    }

    @Override
    public void onProgressAvatar(final int percent) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvProgressAvatar.setText(getString(R.string.avatar_uploading_d, percent));
                progressBarAvatar.setProgress(percent);
            }
        });
    }

    @Override
    public void onProgressScreens(final int percent, final String doneCont) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvProgressScreen.setText(getString(R.string.screen_uploading, percent, doneCont));
                progressBarScreen.setProgress(percent);
            }
        });
    }

    @Override
    public void onProgressAPK(final int percent) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvProgressApk.setText(getString(R.string.apk_uploading_d, percent));
                progressBarApk.setProgress(percent);
            }
        });
    }

    @Override
    public void onFinishAll() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvProgressApk.setVisibility(View.GONE);
                tvProgressScreen.setVisibility(View.GONE);
                tvProgressAvatar.setVisibility(View.GONE);
                progressBarApk.setVisibility(View.GONE);
                progressBarScreen.setVisibility(View.GONE);
                progressBarAvatar.setVisibility(View.GONE);
                btnReset.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void isDev(Boolean check) {
        this.isDev = check;
    }

    @Override
    public void setApkState(Boolean state) {
        this.apkState = state;
    }


    public void requirePermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivityReference(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(act,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        0);
            } else {
                //
            }

        }
    }
}


