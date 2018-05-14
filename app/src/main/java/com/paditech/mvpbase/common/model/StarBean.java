package com.paditech.mvpbase.common.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hung on 5/14/2018.
 */

class StarBean {
    /**
     * 1 : 14
     * 2 : 3
     * 3 : 5
     * 4 : 8
     * 5 : 43
     */

    @SerializedName("1")
    private int $1;
    @SerializedName("2")
    private int $2;
    @SerializedName("3")
    private int $3;
    @SerializedName("4")
    private int $4;
    @SerializedName("5")
    private int $5;

    public static StarBean objectFromData(String str) {

        return new Gson().fromJson(str, StarBean.class);
    }

    public int get$1() {
        return $1;
    }

    public void set$1(int $1) {
        this.$1 = $1;
    }

    public int get$2() {
        return $2;
    }

    public void set$2(int $2) {
        this.$2 = $2;
    }

    public int get$3() {
        return $3;
    }

    public void set$3(int $3) {
        this.$3 = $3;
    }

    public int get$4() {
        return $4;
    }

    public void set$4(int $4) {
        this.$4 = $4;
    }

    public int get$5() {
        return $5;
    }

    public void set$5(int $5) {
        this.$5 = $5;
    }
}
