package com.paditech.mvpbase.common.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.paditech.mvpbase.common.event.ApkFileInfoEvent;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hung on 1/25/2018.
 */

public class AppModel {

    @SerializedName("_source")
    private SourceBean source;

    public SourceBean getSource() {
        return source;
    }

    public void setSource(SourceBean source) {
        this.source = source;
    }


    public AppModel(ApkFileInfoEvent apk) {
        SourceBean source = new SourceBean(apk);
        this.source = source;

    }

    public AppModel() {
    }

    public static class SourceBean {
        private String devId;

        public String getDevId() {
            return devId;
        }

        public void setDevId(String devId) {
            this.devId = devId;
        }

        private int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        private boolean isFirebaseCmt;

        public boolean isFirebaseCmt() {
            return isFirebaseCmt;
        }

        public void setFirebaseCmt(boolean firebaseCmt) {
            isFirebaseCmt = firebaseCmt;
        }

        private  boolean isUserUpload = false;

        public boolean isUserUpload() {
            return isUserUpload;
        }

        public void setUserUpload(boolean userUpload) {
            isUserUpload = userUpload;
        }

        public SourceBean() {
        }
        private String policy;

        public String getPolicy() {
            return policy;
        }

        public void setPolicy(String policy) {
            this.policy = policy;
        }

        public SourceBean(ApkFileInfoEvent apk) {
            this.screenshotUserUpload = apk.getScreenshot();
            this.description = apk.getDescription();
            this.offerby = apk.getOfferby();
            this.appid = apk.getAppid();
            this.score = apk.getScore();
            this.installs = apk.getNumberDownload();
            this.title = apk.getTitle();
            this.cover = apk.getAvar();
            this.isUserUpload = true;
            this.status = apk.getStatus();
            this.category = apk.getCate();
            this.require = apk.getRequire();
            this.contentrating = apk.getAge();
            this.policy = apk.getPolicy();
            this.devId = apk.getUid();

        }

        private ArrayList<String> screenshotUserUpload;

        public ArrayList<String> getScreenshotUserUpload() {
            return screenshotUserUpload;
        }

        public void setScreenshotUserUpload(ArrayList<String> screenshotUserUpload) {
            this.screenshotUserUpload = screenshotUserUpload;
        }
        @SerializedName("drop_percent")
        private float drop_percent;
        @SerializedName("drop_value")
        private float drop_value;

        public float getDrop_percent() {
            return drop_percent;
        }

        public void setDrop_percent(float drop_percent) {
            this.drop_percent = drop_percent;
        }

        public float getDrop_value() {
            return drop_value;
        }

        public void setDrop_value(float drop_value) {
            this.drop_value = drop_value;
        }

        @SerializedName("description")
        private String description;
        @SerializedName("contentrating")
        private String contentrating;


        public String getContentrating() {
            return contentrating;
        }

        public void setContentrating(String contentrating) {
            this.contentrating = contentrating;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getThumbnails() {
            return thumbnails;
        }

        public void setThumbnails(String thumbnails) {
            this.thumbnails = thumbnails;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getOfferby() {
            return offerby;
        }

        public void setOfferby(String offerby) {
            this.offerby = offerby;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        @SerializedName("thumbnails")
        private String thumbnails;
        @SerializedName("category")
        private String category;

        @SerializedName("size")
        private int size;

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        @SerializedName("version")
        private String version;

        @SerializedName("offerby")
        private String offerby;

        @SerializedName("appid")
        private String appid;
        @SerializedName("price")
        private float price;
        @SerializedName("rate_total")
        private int rate;
        @SerializedName("score")
        private float score;

        public String getRequire() {
            return require;
        }

        public void setRequire(String require) {
            this.require = require;
        }

        @SerializedName("requireandroid")
        private String require;

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }

        public int getRate() {
            return rate;
        }

        public void setRate(int rate) {
            this.rate = rate;
        }

        @SerializedName("installs")
        private int installs;

        public int getInstalls() {
            return installs;
        }

        public void setInstalls(int installs) {
            this.installs = installs;
        }

        @SerializedName("tag")
        private String tag;

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @SerializedName("title")
        private String title;


        public List<Float> getAll_price() {
            return all_price;
        }

        public void setAll_price(List<Float> all_price) {
            this.all_price = all_price;
        }

        @SerializedName("all_price")
        private List<Float> all_price = new ArrayList<>();


        public float getPrice() {
            return price;
        }

        public String getCover() {
            return cover;
        }

        @SerializedName("cover")
        private String cover;


        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        @SerializedName("thumbnail")
        private String thumbnail;


        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }


        private ArrayList<ArrayList<String>> screenShot = null;

        public ArrayList<ArrayList<String>> getScreenShot() {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<ArrayList<String>>>() {
            }.getType();
            screenShot = gson.fromJson(thumbnails, listType);
            return screenShot;
        }

        public void setScreenShot(ArrayList<ArrayList<String>> screenShot) {
            this.screenShot = screenShot;
        }


    }
}
