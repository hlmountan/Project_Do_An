package com.paditech.mvpbase.common.model;

/**
 * Created by hung on 5/14/2018.
 */

public class Cmt {
    private String uid;
    private String content;
    private String appid;
    private float rate;
    private int  date;
    private String replyedId;
    private String replyForId;
    private String title;
    private String authorName;
    private String avar;

    public Cmt() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getReplyedId() {
        return replyedId;
    }

    public void setReplyedId(String replyedId) {
        this.replyedId = replyedId;
    }

    public String getReplyForId() {
        return replyForId;
    }

    public void setReplyForId(String replyForId) {
        this.replyForId = replyForId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
