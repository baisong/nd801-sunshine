package com.example.oren.sunshine.app;

import android.annotation.TargetApi;
import android.os.Build;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by oren on 8/10/16.
 */
public class WeatherDataParser {
    /**
     * Given a string of the form returned by the api call:
     * http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7
     * retrieve the maximum temperature for the day indicated by dayIndex
     * (Note: 0-indexed, so 0 would refer to the first day).
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static double getMaxTemperatureForDay(String weatherJsonStr, int dayIndex)
            throws JSONException {
        double max = -1;
        JSONObject doc = new JSONObject(weatherJsonStr);
        JSONArray list = new JSONArray(doc.getJSONArray("list"));
        if (dayIndex < 7 && list.length() > 6) {
            JSONObject day = list.getJSONObject(dayIndex);
            JSONObject temp = day.getJSONObject("temp");
            max = temp.getDouble("max");
        }
        return max;
    }
}
