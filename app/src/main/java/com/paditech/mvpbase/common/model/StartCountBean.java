package com.paditech.mvpbase.common.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hung on 5/23/2018.
 */

class StartCountBean {
    /**
     * star : {"1":4262,"2":1790,"3":2390,"4":3323,"5":10930}
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
