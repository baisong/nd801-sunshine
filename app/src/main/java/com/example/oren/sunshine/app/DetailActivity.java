/*
 * Copyright (C) 2014 The Android Open Source Project
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

package com.example.oren.sunshine.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import static android.text.TextUtils.concat;

public class DetailActivity extends AppCompatActivity {

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
    }
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_detail_container, new DetailFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {

        private static final String LOG_TAG = DetailFragment.class.getSimpleName();
        private static final String FORECAST_SHARE_HASHTAG = "#SunshineApp";
        private final String EXTRA_DAY_NAME = "com.example.oren.sunshine.app.DayName";
        private final String EXTRA_TEXT = "com.example.oren.sunshine.app.Text";
        private final String EXTRA_DATE = "com.example.oren.sunshine.app.Date";
        private final String EXTRA_HIGH = "com.example.oren.sunshine.app.High";
        private final String EXTRA_LOW = "com.example.oren.sunshine.app.Low";
        private final String EXTRA_HUMIDITY = "com.example.oren.sunshine.app.Humidity";
        private final String EXTRA_WIND = "com.example.oren.sunshine.app.Wind";
        private final String EXTRA_PRESSURE = "com.example.oren.sunshine.app.Pressure";
        private final String EXTRA_LABEL = "com.example.oren.sunshine.app.Label";
        private String mDayNameStr;
        private String mTextStr;
        private String mDateStr;
        private String mHighStr;
        private String mLowStr;
        private String mHumidityStr;
        private String mWindStr;
        private String mPressureStr;
        private String mLabelStr;

        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            Intent intent = getActivity().getIntent();
            if (intent != null
                    && intent.hasExtra(EXTRA_TEXT)
                    ) {
                mDayNameStr = intent.getStringExtra(EXTRA_DAY_NAME);
                mTextStr = intent.getStringExtra(EXTRA_TEXT);
                mDateStr = intent.getStringExtra(EXTRA_DATE);
                mHighStr = intent.getStringExtra(EXTRA_HIGH);
                mLowStr = intent.getStringExtra(EXTRA_LOW);
                mHumidityStr = intent.getStringExtra(EXTRA_HUMIDITY);
                mWindStr = intent.getStringExtra(EXTRA_WIND);
                mPressureStr = intent.getStringExtra(EXTRA_PRESSURE);
                mLabelStr = intent.getStringExtra(EXTRA_LABEL);
                TextView tDetailText = (TextView) rootView.findViewById(R.id.detail_text);
                tDetailText.setText(concat(mTextStr, " - YO"));
                TextView tDayName = (TextView) rootView.findViewById(R.id.detail_day_name);
                tDayName.setText(mDayNameStr);
            }

            return rootView;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            // Inflate the menu; this adds items to the action bar if it is present.
            inflater.inflate(R.menu.detailfragment, menu);

            // Retrieve the share menu item
            MenuItem menuItem = menu.findItem(R.id.action_share);

            // Get the provider and hold onto it to set/change the share intent.
            ShareActionProvider mShareActionProvider =
                    (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

            // Attach an intent to this ShareActionProvider.  You can update this at any time,
            // like when the user selects a new piece of data they might like to share.
            if (mShareActionProvider != null ) {
                mShareActionProvider.setShareIntent(createShareForecastIntent());
            } else {
                Log.d(LOG_TAG, "Share Action Provider is null?");
            }
        }

        private Intent createShareForecastIntent() {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, mTextStr + " " + FORECAST_SHARE_HASHTAG);
            return shareIntent;
        }
    }
}
