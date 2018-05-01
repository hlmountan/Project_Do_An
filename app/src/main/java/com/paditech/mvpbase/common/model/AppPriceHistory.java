package com.paditech.mvpbase.common.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by hung on 1/31/2018.
 */

public class AppPriceHistory {

    /**
     * data_US : [["0",1517029994],["0.99",1514438040],["0",1513746810],["0.99",1510981967],["0",1510290767],["0.99",1499401903],["0.5",1498703166],["0.99",1467432000],["1.99",1464840000],["0.99",1464408000],["1.99",1462075200],["0.99",1461470400],["1.99",1459483200],["0.99",1457672400],["1.99",1454043600],["4.99",1451883600],["0.50",1450846800],["2.99",1450242000],["4.99",1449637200],["1.99",1449291600],["2.15",1448600400],["5.77",1447909200],["2.15",1447650000],["5.77",1440648000],["3.31",1440129600],["5.77",1433304000]]
     */

    @SerializedName("data_US")
    private String dataUS;
    private ArrayList<ArrayList<String>> priceHistory = null;

    public ArrayList<ArrayList<String>> getPriceHistory() {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<ArrayList<String>>>(){}.getType();
        priceHistory = gson.fromJson(dataUS,listType);
        return priceHistory;
    }

    public void setPriceHistory(ArrayList<ArrayList<String>> priceHistory) {
        this.priceHistory = priceHistory;
    }

    public String getDataUS() {
        return dataUS;
    }

    public void setDataUS(String dataUS) {
        this.dataUS = dataUS;

    }


}
