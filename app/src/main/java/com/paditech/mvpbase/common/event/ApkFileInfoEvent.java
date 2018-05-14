package com.paditech.mvpbase.common.event;

import java.util.ArrayList;

/**
 * Created by hung on 5/12/2018.
 */

public class ApkFileInfoEvent {
    String title;
    String size;
    String path;
    String dateModify;
    String description;
    String policy;
    int numberDownload;
    float score;
    String appid;
    String uid;
    String linkDownload;
    String avar;
    ArrayList<String> screenshot;
    String offerby;

    public String getOfferby() {
        return offerby;
    }

    public void setOfferby(String offerby) {
        this.offerby = offerby;
    }

    public String getavar() {
        return avar;
    }

    public void setavar(String avar) {
        this.avar = avar;
    }

    public ArrayList<String> getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(ArrayList<String> screenshot) {
        this.screenshot = screenshot;
    }

    public String getLinkDownload() {
        return linkDownload;
    }

    public void setLinkDownload(String linkDownload) {
        this.linkDownload = linkDownload;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public int getNumberDownload() {
        return numberDownload;
    }

    public void setNumberDownload(int numberDownload) {
        this.numberDownload = numberDownload;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }




    public ApkFileInfoEvent() {
    }

    public ApkFileInfoEvent(ArrayList<String> apk) {
        this.title = apk.get(0);
        this.size = apk.get(1);
        this.path = apk.get(3);
        this.dateModify = apk.get(2);
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDateModify() {
        return dateModify;
    }

    public void setDateModify(String dateModify) {
        this.dateModify = dateModify;
    }
}
