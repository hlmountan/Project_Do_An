package com.paditech.mvpbase.common.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hung on 5/30/2018.
 */

public class ListAppFromDev {
    @SerializedName("data")
    private List<ListAppFromDev.appLessInfo> result;
    @SerializedName("total")
    private int totalPage;

    List<AppModel> appModels = new ArrayList<>();

    public List<AppModel> getAppModels() {
        for (ListAppFromDev.appLessInfo app: result) {
            AppModel newApp = new AppModel(app);
            appModels.add(newApp);
        }
        return appModels;
    }

    public void setAppModels(List<AppModel> appModels) {
        this.appModels = appModels;
    }

    public List<ListAppFromDev.appLessInfo> getResult() {
        return result;
    }

    public void setResult(List<ListAppFromDev.appLessInfo> result) {
        this.result = result;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    class appLessInfo{
        @SerializedName("appid")
        private String appid;
        @SerializedName("title")
        private String title;
        @SerializedName("cover")
        private String cover;
        @SerializedName("total")
        private int totalPage;

        public appLessInfo() {
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }
    }
}
