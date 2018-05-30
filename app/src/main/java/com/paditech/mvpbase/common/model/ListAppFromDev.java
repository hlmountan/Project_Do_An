package com.paditech.mvpbase.common.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hung on 5/30/2018.
 */

public class ListAppFromDev {
    @SerializedName("data")
    private List<AppModel.SourceBean> result;
    @SerializedName("total")
    private int totalPage;

    List<AppModel> appModels = new ArrayList<>();

    public List<AppModel> getAppModels() {
        for (AppModel.SourceBean app: result) {
            AppModel newApp = new AppModel(app);
            appModels.add(newApp);
        }
        return appModels;
    }

    public void setAppModels(List<AppModel> appModels) {
        this.appModels = appModels;
    }

    public List<AppModel.SourceBean> getResult() {
        return result;
    }

    public void setResult(List<AppModel.SourceBean> result) {
        this.result = result;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
