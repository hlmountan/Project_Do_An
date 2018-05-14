package com.paditech.mvpbase.common.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hung on 5/14/2018.
 */

public class CommentsBean {
    /**
     * id : 848768
     * appid : in.amazon.mShop.android.shopping
     * comment_id : gp:AOqpTOFAznOVC7I-1rfJb3-PYsdYiXl6y1l-N60w5hgfZnmYHmzOybmuDHYJ0bWE7gXwfrNHPI8Vrvd9iKaqHyg
     * comment : Trustable app
     * author_name : Gaurav Shrama
     * avatar : https://lh6.googleusercontent.com/-MBZZ-hi4SCQ/AAAAAAAAAAI/AAAAAAAAAAA/AIcfdXCzhhfenf7r3cvsReN1OZDsLIQ5Cw/photo.jpg
     * title_comment :
     * star_rating : 4
     * time : 1525945615329
     * version : 16.9.0.300
     * replytext :
     * replytime : -1
     * language : en-US
     * is_comment : true
     * @version : 1
     * @timestamp : 2018-05-11T05:39:57.589Z
     */

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
    private int starRating;
    @SerializedName("time")
    private long time;
    @SerializedName("version")
    private String version;
    @SerializedName("replytext")
    private String replytext;
    @SerializedName("replytime")
    private int replytime;
    @SerializedName("language")
    private String language;
    @SerializedName("is_comment")
    private boolean isComment;
    @SerializedName("@version")
    private String _$Version113; // FIXME check this code
    @SerializedName("@timestamp")
    private String _$Timestamp62; // FIXME check this code

    public static CommentsBean objectFromData(String str) {

        return new Gson().fromJson(str, CommentsBean.class);
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

    public int getStarRating() {
        return starRating;
    }

    public void setStarRating(int starRating) {
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

    public String getReplytext() {
        return replytext;
    }

    public void setReplytext(String replytext) {
        this.replytext = replytext;
    }

    public int getReplytime() {
        return replytime;
    }

    public void setReplytime(int replytime) {
        this.replytime = replytime;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isIsComment() {
        return isComment;
    }

    public void setIsComment(boolean isComment) {
        this.isComment = isComment;
    }

    public String get_$Version113() {
        return _$Version113;
    }

    public void set_$Version113(String _$Version113) {
        this._$Version113 = _$Version113;
    }

    public String get_$Timestamp62() {
        return _$Timestamp62;
    }

    public void set_$Timestamp62(String _$Timestamp62) {
        this._$Timestamp62 = _$Timestamp62;
    }
}
