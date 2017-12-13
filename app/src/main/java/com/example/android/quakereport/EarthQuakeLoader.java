package com.example.android.quakereport;
import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.quakereport.EarthQuake;
import com.example.android.quakereport.QueryUtils;

import java.util.List;

/**
 * Created by Divyansh Singh on 13-12-2017.
 */

public class EarthQuakeLoader extends AsyncTaskLoader<List<EarthQuake>> {

    private static final String LOG_TAG = EarthQuakeLoader.class.getName();
    private String mUrl;

    public EarthQuakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<EarthQuake> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<EarthQuake> earthquakes = QueryUtils.extractFromGeoJson(mUrl);
        return earthquakes;
    }
}