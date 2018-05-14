package com.paditech.mvpbase.common.model;

import com.google.gson.Gson;

/**
 * Created by hung on 5/14/2018.
 */

class RateBean {
    /**
     * 2018-05-04 : {"total":73,"star":{"1":14,"2":3,"3":5,"4":8,"5":43}}
     * 2018-05-05 : {"total":258,"star":{"1":37,"2":7,"3":17,"4":34,"5":163}}
     * 2018-05-06 : {"total":308,"star":{"1":34,"2":11,"3":15,"4":37,"5":211}}
     * 2018-05-07 : {"total":276,"star":{"1":44,"2":12,"3":13,"4":31,"5":176}}
     * 2018-05-08 : {"total":275,"star":{"1":29,"2":6,"3":15,"4":49,"5":176}}
     * 2018-05-09 : {"total":228,"star":{"1":25,"2":5,"3":9,"4":31,"5":158}}
     * 2018-05-10 : {"total":35,"star":{"1":16,"2":1,"3":5,"4":7,"5":6}}
     */



    public static RateBean objectFromData(String str) {

        return new Gson().fromJson(str, RateBean.class);
    }


}
