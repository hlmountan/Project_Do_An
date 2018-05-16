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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.event.ApkFileInfoEvent;
import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenter;
import com.paditech.mvpbase.common.mvp.fragment.MVPFragment;
import com.paditech.mvpbase.common.utils.CommonUtil;
import com.paditech.mvpbase.common.utils.ImageUtil;
import com.paditech.mvpbase.common.utils.StringUtil;
import com.paditech.mvpbase.common.utils.get_image.GetImageManager;
import com.paditech.mvpbase.screen.adapter.RecyclerViewListScreenshortUploadAdapter;
import com.paditech.mvpbase.screen.apkFile.AllApkFileActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by hung on 5/9/2018.
 */

public class UploadApkFragment extends MVPFragment<UploadApkContact.PresenterViewOps> implements UploadApkContact.ViewOps, View.OnClickListener, View.OnFocusChangeListener {
    final int APKFILE = 1;
    final int LISTSCREESHOT = 3;
    final int AVARTAR = 2;


    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.tv_state)
    TextView state;
    @BindView(R.id.ln_step1)
    LinearLayout step1;
    @BindView(R.id.ln_step2)
    LinearLayout step2;
    @BindView(R.id.ln_step3)
    LinearLayout step3;
    @BindView(R.id.ln_step4)
    LinearLayout step4;
    @BindView(R.id.ln_final)
    LinearLayout ln_final;
    @BindView(R.id.img_avar)
    ImageView avar;
    @BindView(R.id.checkbox_agreement)
    CheckBox agreement;
    @BindView(R.id.btn_add_image)
    Button btn_add_image;
    ApkFileInfoEvent apkFile = new ApkFileInfoEvent();
    @BindView(R.id.btn_upload_local)
    Button btn_upload_local;
    @BindView(R.id.btn_upload_driver)
    Button btn_upload_driver;

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
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.et_title)
    EditText et_title;
    @BindView(R.id.tv_user)
    TextView tv_user;

    int step = 1;
    String path = "";
    RecyclerViewListScreenshortUploadAdapter listScreenshortUploadAdapter;
    @BindView(R.id.recycler_view_img_screenshot)
    RecyclerView recycler_view_img_screenshot;

    GetImageManager getImageManager;

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
        CommonUtil.dismissSoftKeyboard(view, getActivityReference());
        getImageManager = new GetImageManager(this);
        tv_user.setText(getString(R.string.hi) + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        listScreenshortUploadAdapter = new RecyclerViewListScreenshortUploadAdapter(act);
        recycler_view_img_screenshot.setLayoutManager(new GridLayoutManager(act, 3, LinearLayoutManager.VERTICAL, false));
        recycler_view_img_screenshot.setAdapter(listScreenshortUploadAdapter);

        btn_next.setOnClickListener(this);
        btn_upload_local.setOnClickListener(this);
        btn_upload_driver.setOnClickListener(this);
        btn_add_image.setOnClickListener(this);
        step3.setOnClickListener(this);
        agreement.setOnClickListener(this);
        et_title.setOnFocusChangeListener(this);


    }

    @Override
    protected Class<? extends FragmentPresenter> onRegisterPresenter() {
        return UploadApkPresenter.class;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_next:
                switch (step) {
                    case 1:
                        step = 2;
                        state.setText(R.string.step_2);
                        step1.setVisibility(View.GONE);
                        step2.setVisibility(View.VISIBLE);
                        requirePermission();
                        break;
                    case 2:
                        if (!path.equals("")) {
                            if (!et_title.getText().toString().equals("")) {
                                getPresenter().uploadApk(path);
                                state.setText(R.string.step_3);
                                progressBar.setVisibility(View.VISIBLE);
                                step2.setVisibility(View.GONE);
                                step3.setVisibility(View.VISIBLE);
                                step = 3;
                            } else {
                                System.out.println(et_title.getText().toString());
                            }
                        } else {
                        }
                        // update avar link


                        break;
                    case 3:
                        if (!StringUtil.isEmpty(apkFile.getAvar())) {
                            getPresenter().updateAvar(apkFile.getAvar());
                            state.setText(R.string.step_4);
                            step = 4;
                            step3.setVisibility(View.GONE);
                            step4.setVisibility(View.VISIBLE);
                        }

                        break;
                    case 4:
                        if (apkFile.getScreenshot() != null) {
                            getPresenter().updateScreenshot(apkFile.getScreenshot());
                            state.setText(R.string.step_5);
                            step = 5;
                            step4.setVisibility(View.GONE);
                            ln_final.setVisibility(View.VISIBLE);
                        }
                        btn_next.setVisibility(View.GONE);
                        break;
                    default:

                        state.setText(R.string.step_1);
                        state.setVisibility(View.GONE);
                        agreement.setChecked(false);
                        btn_next.setVisibility(View.GONE);
                        ln_final.setVisibility(View.GONE);
                        step1.setVisibility(View.VISIBLE);
                        ln_apk_file.setVisibility(View.GONE);
                        step = 1;
                        path = "";
                        et_title.setText("");
                        ImageUtil.loadImage(act, R.drawable.image_placeholder_500x500, avar);
                        listScreenshortUploadAdapter.setListImg(new ArrayList<String>());
                        getImageManager.setmListPhotoSelected(new ArrayList<String>());
                        apkFile = new ApkFileInfoEvent();
                        break;

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
            case R.id.checkbox_agreement:
                if (agreement.isChecked()) {
                    state.setText(R.string.step_1);
                    btn_next.setVisibility(View.VISIBLE);
                    state.setVisibility(View.VISIBLE);
                } else {
                    btn_next.setVisibility(View.GONE);
                    state.setVisibility(View.GONE);
                }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case APKFILE:

                File file = new File(converUri(data));
                int file_size = Integer.parseInt(String.valueOf(file.length() / 1024));
                String name = file.getName();
                String date = String.valueOf(file.lastModified());
                name = name.replace("_", ".");
                name = name.replace(".apk", "");
                apkFile.setTitle(name);
                apkFile.setSize(String.valueOf(file_size / 1024));
                apkFile.setDateModify(date);
                apkFile.setPath(converUri(data));
                apkFile.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                setApkinfo(apkFile);
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
    public void onUploadFileSuccess(String url) {
        apkFile.setLinkDownload(url);
        progressBar.setVisibility(View.GONE);
        tv_percen.setVisibility(View.GONE);
        apkFile.setTitle(et_title.getText().toString());
        apkFile.setStatus(2);
        getPresenter().createNewApk(apkFile);
    }

    @Override
    public void onUploadFileFalse(@NonNull Exception exception) {
        System.out.println(exception);
    }

    @Override
    public void onUploading(String percent) {
        tv_percen.setVisibility(View.VISIBLE);
        tv_percen.setText(percent);
    }

    @Override
    public void onAvarLoadSuccess(String url) {
        apkFile.setAvar(url);
        getPresenter().updateApkAvar(apkFile);
    }

    @Override
    public void onScreenshotLoadSuccess(ArrayList<String> url) {
        apkFile.setScreenshot(url);
        getPresenter().updateApkScreenshot(apkFile);
    }

    @Override
    public void uploadappid(String appid) {
        apkFile.setAppid(appid);
    }

    @Override
    public void visibleSuccessUpload() {
        btn_next.setVisibility(View.VISIBLE);
    }

    public void requirePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?

            if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
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


