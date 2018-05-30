package com.paditech.mvpbase.common.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hung on 5/30/2018.
 */

public class AllVersion {

    @SerializedName("")
    private List<AppVersion> versions;

    public static AllVersion objectFromData(String str) {

        return new Gson().fromJson(str, AllVersion.class);
    }

    public List<AppVersion> getVersions() {
        return versions;
    }

    public void setVersions(List<AppVersion> versions) {
        this.versions = versions;
    }
}
