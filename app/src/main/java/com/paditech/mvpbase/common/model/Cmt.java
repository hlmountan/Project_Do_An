package com.paditech.mvpbase.common.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hung on 5/14/2018.
 */

public class Cmt {

    @SerializedName("id")
    private int id;
    @SerializedName("appid")
    private String appid;
    @SerializedName("comment_id")
    private String commentId;
    @SerializedName("comment")
    private String comment;
    @SerializedName("author_name")
    private String authorName;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("title_comment")
    private String titleComment;
    @SerializedName("star_rating")
    private float starRating;
    @SerializedName("time")
    private long time;
    @SerializedName("version")
    private String version;// FIXME check this code

    private String uid;

    public static Cmt objectFromData(String str) {

        return new Gson().fromJson(str, Cmt.class);
    }

    public Cmt() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTitleComment() {
        return titleComment;
    }

    public void setTitleComment(String titleComment) {
        this.titleComment = titleComment;
    }

    public float getStarRating() {
        return starRating;
    }

    public void setStarRating(float starRating) {
        this.starRating = starRating;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }



    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
