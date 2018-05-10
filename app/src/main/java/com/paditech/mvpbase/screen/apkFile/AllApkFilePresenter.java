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
        ArrayList<ArrayList<String>> listApk = new ArrayList<>();

        String path = Environment.getExternalStorageDirectory().toString();
        File directory = new File(path);

        File[] files = directory.listFiles();
        System.out.println("Size: "+ files.length);
        for (int i = 0; i < files.length; i++)
        {
            String pathChild = path+"/"+files[i].getName();

            System.out.println(pathChild);
            File directoryChild = new File(pathChild);
            File[] filesChild = directoryChild.listFiles();
            System.out.println(files[i].getName()+ "  Size: "+ filesChild.length);

            for (int j = 0; j < filesChild.length; j++) {
                if (filesChild[j].getName().indexOf(".apk") >=0){
                    ArrayList<String> apk = new ArrayList<>();
                    String name = filesChild[j].getName();
                    String date = String.valueOf(filesChild[j].lastModified());

                    String finalpath = path + "/" +files[i].getName() + "/"+ name;
                    System.out.println("final path   "  + finalpath);
                    File file = new File( finalpath);
                    int file_size = Integer.parseInt(String.valueOf(file.length() / 1024));
                    System.out.println(file_size + " size day nay " + date);
                    name = name.replace("_", ".");
                    name = name.replace(".apk", "");
                    apk.add(name);
                    apk.add(String.valueOf(file_size/1024));
                    apk.add(date);
                    apk.add(finalpath);
                    listApk.add(apk);

                }else{
                    System.out.println(filesChild[j].getName() + " not apk file");
                }

            }
        }

        getView().loadApkFile(listApk);
    }
}
