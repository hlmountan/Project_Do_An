package com.paditech.mvpbase.common.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hung on 5/14/2018.
 */

public class CmtGp {


    /**
     * comments : [{"id":1494349,"appid":"com.instagram.android","comment_id":"gp:AOqpTOEzV_qWdLHI78NJTYF6RYmHqHlJe--KOSYFEBlY0w5gTPXXal3eAmEkZIbkMAkwPvgMUbtTHIhdC6YT0Q","comment":"It has deleted my account without my consent Neither giving technical \nsupport to get in touch with them to seek help to get my account back.","author_name":"Rajat Jain","avatar":"https://lh3.googleusercontent.com/-bUGFfrnB5qo/AAAAAAAAAAI/AAAAAAAAYO4/HYNegcvbLgQ/photo.jpg","title_comment":"","star_rating":1,"time":1526925204429,"version":"45.0.0.17.93","replytext":"","replytime":-1,"language":"en-US","is_comment":true,"@version":"1","@timestamp":"2018-05-23T03:00:58.555Z"},{"id":1494350,"appid":"com.instagram.android","comment_id":"gp:AOqpTOEIRNABrSfnuyOfgtadWUpd1dKGiMZLwMrR_LZakAo9eY5LyID_9pH1JNAVqv97C3531szvjw9vAj44pA","comment":"Ok","author_name":"munther ali","avatar":"https://lh3.googleusercontent.com/-dbkazhx9WUA/AAAAAAAAAAI/AAAAAAAAABQ/xYy5mxNBzlk/photo.jpg","title_comment":"","star_rating":5,"time":1526925202642,"version":"","replytext":"","replytime":-1,"language":"en-US","is_comment":true,"@version":"1","@timestamp":"2018-05-23T03:00:58.555Z"},{"id":1494351,"appid":"com.instagram.android","comment_id":"gp:AOqpTOGnk3pi41xrMVTSls0ViQQuExXvKKwMHqgZQ1oJ2WyebtAp4Of9XRT-TZX1VXyyorttlcq6gnPzIe6t0w","comment":"This is good app","author_name":"Vavanish Bughani","avatar":"https://lh6.googleusercontent.com/-sANlgKHFQks/AAAAAAAAAAI/AAAAAAAAAzY/gMWcXPz0L8w/photo.jpg","title_comment":"","star_rating":5,"time":1526925195204,"version":"","replytext":"","replytime":-1,"language":"en-US","is_comment":true,"@version":"1","@timestamp":"2018-05-23T03:00:58.555Z"},{"id":1494352,"appid":"com.instagram.android","comment_id":"gp:AOqpTOFdMWG140wNIUubKWXlWs0inz_noPhBKO1XVX7yPGEW8kPXsYmOpuOkCbMe1CStgtawvr94Kt8qfauymA","comment":"Good","author_name":"Mostofa Marzan","avatar":"https://lh3.googleusercontent.com/-hgQF-xXIkw4/AAAAAAAAAAI/AAAAAAAAAA4/gJdo2cyJAJM/photo.jpg","title_comment":"","star_rating":5,"time":1526925193291,"version":"45.0.0.17.93","replytext":"","replytime":-1,"language":"en-US","is_comment":true,"@version":"1","@timestamp":"2018-05-23T03:00:58.555Z"}]
     * start_count : {"star":{"1":4262,"2":1790,"3":2390,"4":3323,"5":10930}}
     */

    @SerializedName("start_count")
    private StartCountBean startCount;
    @SerializedName("comments")
    private List<Cmt> comments;

    public static CmtGp objectFromData(String str) {

        return new Gson().fromJson(str, CmtGp.class);
    }

    public StartCountBean getStartCount() {
        return startCount;
    }

    public void setStartCount(StartCountBean startCount) {
        this.startCount = startCount;
    }

    public List<Cmt> getComments() {
        return comments;
    }

    public void setComments(List<Cmt> comments) {
        this.comments = comments;
    }
}
