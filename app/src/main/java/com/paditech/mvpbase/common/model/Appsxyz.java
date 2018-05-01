package com.paditech.mvpbase.common.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hung on 12/8/2017.
 */

public class Appsxyz {


    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    private int checkCompare;

    public int getCheckCompare() {
        return checkCompare;
    }

    public void setCheckCompare(int checkCompare) {
        this.checkCompare = checkCompare;
    }

    @SerializedName("page")
    private int page;
    @SerializedName("total_page")
    private int totalPage;
    @SerializedName("result")
    private List<AppModel> result;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<AppModel> getResult() {
        return result;
    }

    public void setResult(List<AppModel> result) {
        this.result = result;
    }


}
