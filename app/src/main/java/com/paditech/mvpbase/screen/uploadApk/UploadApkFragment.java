package com.paditech.mvpbase.screen.uploadApk;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenter;
import com.paditech.mvpbase.common.mvp.fragment.MVPFragment;
import com.paditech.mvpbase.common.utils.ImageUtil;
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

import static android.app.Activity.RESULT_OK;

/**
 * Created by hung on 5/9/2018.
 */

public class UploadApkFragment extends MVPFragment<UploadApkContact.PresenterViewOps> implements UploadApkContact.ViewOps, View.OnClickListener {
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

    @BindView(R.id.checkbox_agreement)
    CheckBox agreement;
    @BindView(R.id.tv_upload_apk)
    TextView upload;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.btn_add_image)
    Button btn_add_image;

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

    @BindView(R.id.tv_finish)
    TextView tv_finish;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.img_avar)
    ImageView avar;
    String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    FirebaseStorage storage = FirebaseStorage.getInstance("gs://rugged-scion-129008.appspot.com/");
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetApkFile(ArrayList<String> apk) {
        ln_apk_file.setVisibility(View.VISIBLE);
        titleApk.setText(apk.get(0));
        sizeApk.setText(apk.get(1) + " Mb");
        Date mDate = new Date();
        DateFormat mDataFormat = new SimpleDateFormat("MMM-dd-yy");
        mDate.setTime(Long.parseLong(apk.get(2))/1000);
        lastModify.setText(mDataFormat.format(mDate));
        setPath(apk.get(3));
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_upload_apk;
    }

    @Override
    protected void initView(View view) {

        agreement.setOnClickListener(this);
        btn_upload_local.setOnClickListener(this);
        btn_upload_driver.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        btn_add_image.setOnClickListener(this);
        upload.setOnClickListener(this);
        tv_finish.setOnClickListener(this);
    }

    @Override
    protected Class<? extends FragmentPresenter> onRegisterPresenter() {
        return UploadApkPresenter.class;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.checkbox_agreement:
                step1.setVisibility(View.GONE);
                step2.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_upload_local:
                startActivity(new Intent(act, AllApkFileActivity.class));
                break;
            case R.id.btn_upload_driver:
                 intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(Intent.normalizeMimeType(".apk -> application/vnd.android.package-archive"));
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                break;
            case R.id.tv_upload_apk:
                uploadApk();
                progressBar.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_submit:
                step3.setVisibility(View.GONE);
                step4.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_add_image:
//                Intent intent = new Intent(Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, 0);

                 intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                avar.setVisibility(View.VISIBLE);
//                step4.setVisibility(View.GONE);
//                ln_final.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_finish:
                ln_final.setVisibility(View.GONE);
                step1.setVisibility(View.VISIBLE);
        }
    }

    private void uploadApk() {
        UploadTask uploadTask;
        String path = getPath();
        StorageReference storageRef = storage.getReference();

        Uri file = Uri.fromFile(new File(path));
        StorageReference riversRef = storageRef.child("apk/" + file.getLastPathSegment());
        uploadTask = riversRef.putFile(file);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                System.out.println("upload fail " + exception);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                System.out.println("success upload");
                step2.setVisibility(View.GONE);
                step3.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();
//            textTargetUri.setText(targetUri.toString());
            ImageUtil.loadImage(act,targetUri,avar);
//            Bitmap bitmap;
//            try {
//                bitmap = BitmapFactory.decodeStream(act.getContentResolver().openInputStream(targetUri));
//                avar.setImageBitmap(bitmap);
//            } catch (FileNotFoundException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
        }
    }
}


