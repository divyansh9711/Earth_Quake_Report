/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.content.AsyncTaskLoader;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<EarthQuake>> {
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query";

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    EarthQuakeAdapter adapter;
    private TextView mEmptyStateTextView;
    private ProgressBar spinner;
    ConnectivityManager cm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        Log.e("LOG 1", "On create called");

        ArrayList<EarthQuake> earthquakes = new ArrayList<EarthQuake>();
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        adapter = new EarthQuakeAdapter(this, earthquakes);
        earthquakeListView.setAdapter(adapter);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(mEmptyStateTextView);
        spinner = (ProgressBar) findViewById(R.id.spin);
        spinner.setVisibility(View.VISIBLE);

         cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(1, null, this);
        Log.e("LOG 2", "INIT loader called");


        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EarthQuake e = (EarthQuake) adapterView.getItemAtPosition(i);
                String url = e.getUrl();
                Intent inten = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(inten);
            }
        });
    }


    @Override
    public Loader<List<EarthQuake>> onCreateLoader(int i, Bundle bundle) {
        Log.e("LOG 3", "LOG on create called");

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));
        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "50");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);

        return new com.example.android.quakereport.EarthQuakeLoader(EarthquakeActivity.this, uriBuilder.toString());
    }
    @Override
    public void onLoadFinished(Loader<List<EarthQuake>> loader, List<EarthQuake> earthQuakes) {
        Log.e("LOG 4", "LOG on finished called");
        adapter.clear();
        if (earthQuakes != null && !earthQuakes.isEmpty()) {
            adapter.addAll(earthQuakes);
        }
        mEmptyStateTextView.setText(R.string.no_earthquakes);
        spinner.setVisibility(View.INVISIBLE);



        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(!isConnected){
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

    }
    @Override
    public void onLoaderReset(Loader<List<EarthQuake>> loader) {
        Log.e("LOG 5", "LOG on reset called");
        adapter.clear();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}






