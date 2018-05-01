package com.paditech.mvpbase.screen.detail;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hung on 2/5/2018.
 */

public class DateAxisValueFormatter implements IAxisValueFormatter {

 // minimum timestamp in your data set
    private DateFormat mDataFormat;
    private Date mDate;
    private ArrayList<ArrayList<String>> priceHistory = null;
    private int size = 0 ;

    public DateAxisValueFormatter(ArrayList<ArrayList<String>> priceHistory ) {
        this.mDataFormat = new SimpleDateFormat("MMM-dd");
        this.mDate = new Date();
        this.priceHistory = priceHistory;
        this.size = priceHistory.size();
    }

    /**
     * Called when a value from an axis is to be formatted
     * before being drawn. For performance reasons, avoid excessive calculations
     * and memory allocations inside this method.
     */

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // convert float value into int value. is index of pricehistory
        int index = (int) value;
        // Retrieve original timestamp
        try{
            Long originalTimestamp = Long.parseLong(priceHistory.get(size-index).get(1));
            return getDate(originalTimestamp);
        }catch(Exception e){
            System.out.println(e);
        }

        // Convert timestamp to hour:minute
        return "Ngay khong xac dinh";
    }

    private String getDate(long timestamp) {
        try {
            mDate.setTime(timestamp * 1000);
            return mDataFormat.format(mDate);
        } catch (Exception ex) {
            return "xx";
        }
    }
}
