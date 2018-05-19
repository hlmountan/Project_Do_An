package com.paditech.mvpbase.common.event;

/**
 * Created by hung on 5/11/2018.
 */

public class ChipCateTagEvent {
    String tag;

    public ChipCateTagEvent(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
