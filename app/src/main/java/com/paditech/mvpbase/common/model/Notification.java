package com.paditech.mvpbase.common.model;

/**
 * Created by hung on 5/15/2018.
 */

public class Notification {
    int status;
    String title;
    String content;
    Long date;
    String appid;
    Boolean isRead;
    String appTitle;
    String appAvar;



    public Notification() {
    }

    public Notification(int status, String title, String content,
                        Long date, String appid, Boolean isRead, String appTitle, String appAvar) {
        this.status = status;
        this.title = title;
        this.content = content;
        this.date = date;
        this.appid = appid;
        this.isRead = isRead;
        this.appTitle = appTitle;
        this.appAvar = appAvar;
    }

    public String getAppAvar() {
        return appAvar;
    }

    public void setAppAvar(String appAvar) {
        this.appAvar = appAvar;
    }

    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }
}
