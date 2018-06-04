package com.paditech.mvpbase.common.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hung on 5/30/2018.
 */

public class AppVersion {

    /**
     * a : com.jio.myjio4115
     * appid : com.jio.myjio
     * version : 4.1.15
     * versioncode : 4115
     * uploaddate_file : 2018-05-02T00:00:00.000Z
     * whatsnew : • Enjoy Jio Cricket Play Along!  • Watch Prof LBW, Sabse bada Cricket-Comedy Show, on Jio Dhan Dhana Dhan Live! • Introducing Hello Jio – Jio’s virtual assistant • Setup JioAutoPay for prepaid and postpaid accounts for hassle free payments • Performance improvement and bug fixes
     * sha1 : 54d1ad70f414e474413d8b04dafabe43ca184a55
     * signature : 6c8c73d5c538f64ea5097284081a97148da55133
     * size : 30197057
     * requiresandroid :
     * obbversioncode :
     * obb_size : 0
     * obb_sha1 :
     * patch_size : 0
     * patch_sha1 :
     * download_number : 1
     * @version : 1
     * @timestamp : 2018-05-29T12:54:34.899Z
     */

    @SerializedName("appid")
    private String appid;
    @SerializedName("version")
    private String version;
    @SerializedName("versioncode")
    private int versioncode;
    @SerializedName("uploaddate_file")
    private String uploaddateFile;
    @SerializedName("whatsnew")
    private String whatsnew;
    @SerializedName("sha1")
    private String sha1;
    @SerializedName("signature")
    private String signature;
    @SerializedName("size")
    private float size;
    @SerializedName("requiresandroid")
    private String requiresandroid;
    @SerializedName("obbversioncode")
    private String obbversioncode;
    @SerializedName("obb_size")
    private int obbSize;
    @SerializedName("obb_sha1")
    private String obbSha1;
    @SerializedName("patch_size")
    private float patchSize;
    @SerializedName("patch_sha1")
    private String patchSha1;
    @SerializedName("download_number")
    private int downloadNumber;
    @SerializedName("@timestamp")
    private String _$Timestamp90; // FIXME check this code

    public static AppVersion objectFromData(String str) {

        return new Gson().fromJson(str, AppVersion.class);
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(int versioncode) {
        this.versioncode = versioncode;
    }

    public String getUploaddateFile() {
        return uploaddateFile;
    }

    public void setUploaddateFile(String uploaddateFile) {
        this.uploaddateFile = uploaddateFile;
    }

    public String getWhatsnew() {
        return whatsnew;
    }

    public void setWhatsnew(String whatsnew) {
        this.whatsnew = whatsnew;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public String getRequiresandroid() {
        return requiresandroid;
    }

    public void setRequiresandroid(String requiresandroid) {
        this.requiresandroid = requiresandroid;
    }

    public String getObbversioncode() {
        return obbversioncode;
    }

    public void setObbversioncode(String obbversioncode) {
        this.obbversioncode = obbversioncode;
    }

    public int getObbSize() {
        return obbSize;
    }

    public void setObbSize(int obbSize) {
        this.obbSize = obbSize;
    }

    public String getObbSha1() {
        return obbSha1;
    }

    public void setObbSha1(String obbSha1) {
        this.obbSha1 = obbSha1;
    }

    public float getPatchSize() {
        return patchSize;
    }

    public void setPatchSize(float patchSize) {
        this.patchSize = patchSize;
    }

    public String getPatchSha1() {
        return patchSha1;
    }

    public void setPatchSha1(String patchSha1) {
        this.patchSha1 = patchSha1;
    }

    public int getDownloadNumber() {
        return downloadNumber;
    }

    public void setDownloadNumber(int downloadNumber) {
        this.downloadNumber = downloadNumber;
    }

    public String get_$Timestamp90() {
        return _$Timestamp90;
    }

    public void set_$Timestamp90(String _$Timestamp90) {
        this._$Timestamp90 = _$Timestamp90;
    }
}
