package com.paditech.mvpbase.screen.uploadApk;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.paditech.mvpbase.common.event.ApkFileInfoEvent;
import com.paditech.mvpbase.common.mvp.fragment.FragmentPresenter;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by hung on 5/9/2018.
 */

public class UploadApkPresenter extends FragmentPresenter<UploadApkContact.ViewOps> implements UploadApkContact.PresenterViewOps {
    FirebaseStorage storage = FirebaseStorage.getInstance("gs://rugged-scion-129008.appspot.com/");
// storage
    @Override
    public void uploadApk(String path) {
        UploadTask uploadTask;
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
                getView().onUploadFileFalse(exception);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                System.out.println(taskSnapshot.getDownloadUrl());
                System.out.println("success upload");
                getView().onUploadFileSuccess(taskSnapshot.getDownloadUrl().toString());
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                getView().onUploading(String.valueOf(taskSnapshot.getBytesTransferred() * 100 / taskSnapshot.getTotalByteCount()));
            }
        });
    }

    @Override
    public void updateAvar(String path) {
        UploadTask uploadTask;
        StorageReference storageRef = storage.getReference();

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
                getView().onAvarLoadSuccess(taskSnapshot.getDownloadUrl().toString());
            }
        });
    }

    @Override
    public void updateScreenshot(final ArrayList<String> path) {
        final ArrayList<String> url = new ArrayList<>();
        UploadTask uploadTask;
        StorageReference storageRef = storage.getReference();

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
                    if (url.size() == path.size()){
                        getView().onScreenshotLoadSuccess(url);
                    }

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    System.out.println("success upload");
                    url.add(taskSnapshot.getDownloadUrl().toString());
                    if (url.size() == path.size()){
                        getView().onScreenshotLoadSuccess(url);
                    }
                }
            });
        }



    }

//db
    public void createNewApk(final ApkFileInfoEvent apk) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("apk");
        String id = FirebaseDatabase.getInstance().getReference().child("apk").push().getKey();
        FirebaseDatabase.getInstance().getReference().child("apk").child(id).setValue(apk);
        getView().uploadappid(id);
    }

    public void updateApkAvar(final ApkFileInfoEvent apk) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("apk");
        FirebaseDatabase.getInstance().getReference().child("apk").child(apk.getAppid()).child("avar").setValue(apk.getAvar());

    }

    public void updateApkScreenshot(final ApkFileInfoEvent apk) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("apk");
        FirebaseDatabase.getInstance().getReference().child("apk").child(apk.getAppid()).child("screenshot").setValue(apk.getScreenshot()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                getView().visibleSuccessUpload();
            }
        });

    }
}
