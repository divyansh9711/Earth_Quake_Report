package com.example.android.quakereport;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Divyansh Singh on 11-11-2017.
 */

public class EarthQuake {
    private double mMagnitude;
    private String mLocation;
    private long miliTime;
    private Date date;
    String dateToDisplay;
    private String url;

    private String time;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public EarthQuake(double mMagnitude, String mLocation, long mDate, String url) {
        this.mMagnitude = mMagnitude;
        this.mLocation = mLocation;
        this.url = url;

        this.miliTime = mDate;
        date = new Date(mDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM DD, yyyy");
        dateToDisplay = simpleDateFormat.format(date);
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm a");
        time = simpleTimeFormat.format(date);
    }

    public double getmMagnitude() {
        return mMagnitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setmMagnitude(double mMagnitude) {
        this.mMagnitude = mMagnitude;
    }

    public String getmLocation() {
        return mLocation;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public long getmDate() {
        return miliTime;
    }

    public void setmDate(long mDate) {
        this.miliTime = mDate;
    }
}
