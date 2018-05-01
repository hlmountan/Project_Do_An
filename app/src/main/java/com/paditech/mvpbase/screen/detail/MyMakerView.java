package com.paditech.mvpbase.screen.detail;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.paditech.mvpbase.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hung on 2/5/2018.
 */

public class MyMakerView extends MakerView {
    private TextView tvdate;
    private TextView tvggplay;
    ArrayList<ArrayList<String>> priceHistory = null;

    public MyMakerView(Context context, int layoutResource,
                       ArrayList<ArrayList<String>> priceHistory) {
        super(context, layoutResource);

        tvdate = (TextView) findViewById(R.id.tv_time);
        tvggplay = (TextView) findViewById(R.id.tv_ggplay_price);
        this.priceHistory = priceHistory;
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)


    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        super.refreshContent(e, highlight);
        String a = convertTimeStamp((int) e.getX());
        tvdate.setText("" + a);
        tvggplay.setText("" + Utils.formatNumber(e.getY(), 0, true));


    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }

    public String convertTimeStamp(int index) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");
        Date date = new Date();
        Long originalTimestamp = Long.parseLong(priceHistory.get(priceHistory.size() - index).get(1));
        date.setTime(originalTimestamp * 1000);
        return dateFormat.format(date);
    }
}
