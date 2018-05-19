package com.paditech.mvpbase.screen.apkFile;

import android.os.Environment;

import com.paditech.mvpbase.common.mvp.activity.ActivityPresenter;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by hung on 5/10/2018.
 */

public class AllApkFilePresenter extends ActivityPresenter<AllApkFileContact.ViewOps> implements AllApkFileContact.PresenterViewOps {
    @Override
    public void getListApkFile() {
        try {
            ArrayList<ArrayList<String>> listApk = new ArrayList<>();

            String path = Environment.getExternalStorageDirectory().toString();
            File directory = new File(path);

            File[] files = directory.listFiles();
            System.out.println("Size: " + files.length);
            for (File file1 : files) {
                String pathChild = path + "/" + file1.getName();

                System.out.println(pathChild);
                File directoryChild = new File(pathChild);
                File[] filesChild = directoryChild.listFiles();
                if (filesChild != null) {
                    System.out.println(file1.getName() + "  Size: " + filesChild.length);

                    for (File aFilesChild : filesChild) {
                        if (aFilesChild.getName().contains(".apk")) {
                            ArrayList<String> apk = new ArrayList<>();
                            String name = aFilesChild.getName();
                            String date = String.valueOf(aFilesChild.lastModified());

                            String finalpath = path + "/" + file1.getName() + "/" + name;
                            System.out.println("final path   " + finalpath);
                            File file = new File(finalpath);
                            int file_size = Integer.parseInt(String.valueOf(file.length() / 1024));
                            System.out.println(file_size + " size day nay " + date);
                            name = name.replace("_", ".");
                            name = name.replace(".apk", "");
                            apk.add(name);
                            apk.add(String.valueOf(file_size / 1024));
                            apk.add(date);
                            apk.add(finalpath);
                            listApk.add(apk);

                        } else {
                            System.out.println(aFilesChild.getName() + " not apk file");
                        }
                    }
                }
            }

            getView().loadApkFile(listApk);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
