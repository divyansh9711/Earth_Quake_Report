package com.example.android.quakereport;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import java.util.List;

/**
 * Created by Divyansh Singh on 13-12-2017.
 */

public class EarthQuakeLoader extends AsyncTaskLoader<List<EarthQuake>> {

    private static final String LOG_TAG = EarthQuakeLoader.class.getName();
    private String mUrl;

    public EarthQuakeLoader(Context context, String url) {
        super(context);
        Log.e("LOG 6","Eqloader constructor");

        mUrl = url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<EarthQuake> loadInBackground() {
        Log.e("LOG 7","Workin in backgroud");

        if (mUrl == null) {
            return null;
        }
        List<EarthQuake> earthquakes = QueryUtils.extractFromGeoJson(mUrl);
        return earthquakes;
    }
}