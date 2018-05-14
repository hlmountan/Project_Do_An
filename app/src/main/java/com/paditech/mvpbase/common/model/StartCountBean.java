package com.paditech.mvpbase.common.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hung on 5/14/2018.
 */

class StartCountBean {
    /**
     * star : {"1":1083,"2":143,"3":188,"4":474,"5":2128}
     */

    @SerializedName("star")
    private StarBean star;

    public static StartCountBean objectFromData(String str) {

        return new Gson().fromJson(str, StartCountBean.class);
    }

    public StarBean getStar() {
        return star;
    }

    public void setStar(StarBean star) {
        this.star = star;
    }
}
