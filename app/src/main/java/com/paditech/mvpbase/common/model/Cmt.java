package com.paditech.mvpbase.common.model;

/**
 * Created by hung on 5/14/2018.
 */

public class Cmt {
    private String uid;
    private String content;
    private String appid;
    private float rate;
    private long  date;
    private String replyedId;
    private String replyForId;
    private String title;
    private String authorName;
    private String avar;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAvar() {
        return avar;
    }

    public void setAvar(String avar) {
        this.avar = avar;
    }



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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
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
