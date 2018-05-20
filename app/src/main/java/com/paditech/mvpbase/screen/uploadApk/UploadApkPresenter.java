package com.paditech.mvpbase.screen.uploadApk;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.paditech.mvpbase.common.event.ApkFileInfoEvent;
import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by hung on 5/9/2018.
 */

public class UploadApkPresenter extends FragmentPresenter<UploadApkContact.ViewOps> implements UploadApkContact.PresenterViewOps {
    private String mAppId;
    private boolean avatarSuccess, apkSuccess, screenshotSuccess;

    // storage
    @Override
    public void uploadApk(String path) {
        UploadTask uploadTask;
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        Uri file = Uri.fromFile(new File(path));
        StorageReference riversRef = storageRef.child("apk/" + file.getLastPathSegment());
        uploadTask = riversRef.putFile(file);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                System.out.println("upload fail " + exception);
                getView().onUploadFileFalse(exception);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                System.out.println(taskSnapshot.getDownloadUrl());
                System.out.println("success upload");

                // save to firebase
                Uri linkDownload = taskSnapshot.getDownloadUrl();
                if (linkDownload != null) {
                    apkSuccess = true;
                    FirebaseDatabase.getInstance().getReference().child("apk").child(mAppId).child("linkDownload").setValue(linkDownload.toString());
                    checkSuccessAll();
                }
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                getView().onProgressAPK((int) (taskSnapshot.getBytesTransferred() * 100 / taskSnapshot.getTotalByteCount()));
            }
        });
    }

    @Override
    public void updateAvar(String path) {
        UploadTask uploadTask;
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        Uri file = Uri.fromFile(new File(path));
        StorageReference riversRef = storageRef.child("avartar/" + file.getLastPathSegment());
        uploadTask = riversRef.putFile(file);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                System.out.println("upload fail " + exception);
                getView().onUploadFileFalse(exception);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Uri link = taskSnapshot.getDownloadUrl();
                if (link != null) {
                    FirebaseDatabase.getInstance().getReference().child("apk").child(mAppId).child("avar").setValue(link.toString());
                    avatarSuccess = true;
                    checkSuccessAll();
                }
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                getView().onProgressAvatar((int) (taskSnapshot.getBytesTransferred() * 100 / taskSnapshot.getTotalByteCount()));
            }
        });
    }

    @Override
    public void updateScreenshot(final ArrayList<String> path) {
        final ArrayList<String> url = new ArrayList<>();
        UploadTask uploadTask;
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        for (String a : path) {
            Uri file = Uri.fromFile(new File(a));
            StorageReference riversRef = storageRef.child("screenshot/" + file.getLastPathSegment());
            uploadTask = riversRef.putFile(file);
            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    System.out.println("upload fail " + exception);
                    url.add("");
                    if (url.size() == path.size()) {
                        updateApkScreenshot(url);
                    }

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    System.out.println("success upload");
                    url.add(taskSnapshot.getDownloadUrl().toString());
                    if (url.size() == path.size()) {
                        updateApkScreenshot(url);
                    }
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    getView().onProgressScreens((int) (taskSnapshot.getBytesTransferred() * 100 / taskSnapshot.getTotalByteCount()), url.size() + "/" + path.size());
                }
            });
        }


    }

    @Override
    public void createNewApk(final ApkFileInfoEvent apk) {
        Log.e("APK", new Gson().toJson(apk));
        mAppId = getAppId(apk.getPath());
        if (mAppId == null) return;
        apk.setUid(FirebaseAuth.getInstance().getUid());
        apk.setStatus(ApkFileInfoEvent.STATUS_PENDING);
        FirebaseDatabase.getInstance().getReference().child("apk").child(mAppId).setValue(apk);
        getView().uploadappid(mAppId);
        uploadApk(apk.getPath());
        updateAvar(apk.getAvar());
        updateScreenshot(apk.getScreenshot());
    }

    public void updateApkScreenshot(ArrayList<String> screens) {
        FirebaseDatabase.getInstance().getReference().child("apk").child(mAppId).child("screenshot").setValue(screens);
        screenshotSuccess = true;
        checkSuccessAll();

    }

    @Override
    public String getAppId(String filePath) {
        try {
            final PackageManager pm = getView().getActivityContext().getPackageManager();
            PackageInfo info = pm.getPackageArchiveInfo(filePath, 0);
            Log.e("APK", "VersionCode : " + info.versionCode + ", VersionName : " + info.versionName);
            return info.packageName.replace(".", "_");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void checkSuccessAll() {
        if (avatarSuccess && apkSuccess && screenshotSuccess) {
            getView().onFinishAll();
        }
    }

}
