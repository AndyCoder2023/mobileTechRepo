package com.example.mobiletech;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobiletech.data.HourForecast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForecastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForecastFragment extends Fragment {

//    Tag for logging messages
    private static final String TAG = "ForecastFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ForecastFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForecastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForecastFragment newInstance(String param1, String param2) {
        ForecastFragment fragment = new ForecastFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forecast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        downloadForecast();
    }
//    Method creates URL

    private void downloadForecast() {
        String url = "https://api.weatherapi.com/v1/forecast.json?key=a3b9cc3fb35943d5826152257210311 &q=Aberdeen&days=3";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                //                    for formatting the date of the forecast
                SimpleDateFormat sdf = new SimpleDateFormat(getString(R.string.forecast_date_format));
//                    for storing all the weather forecast
                List<HourForecast> forecastList = new ArrayList<HourForecast>(24*5);

                try {

//                    Convert text response to a JSON object for processing
                    JSONObject rootObj = new JSONObject(response);
//                    Get the forecast value
                    JSONObject forecastObject = rootObj.getJSONObject("forecast");
//                    get the forecast day value - an array of days
                    JSONArray forecastDayArray = forecastObject.getJSONArray("forecastday");
//                    for every forecast day
                    for (int i = 0, j = forecastDayArray.length(); i<j; i++) {
//                        get the day at position i
                        JSONObject dayObject = forecastDayArray.getJSONObject(i);
//                        from the day, get the hour array
                        JSONArray hourArray = dayObject.getJSONArray("hour");
                        for (int ii = 0, jj = hourArray.length(); ii < jj; ii++) {
//                            get the forecast hour object
                            JSONObject forecastHourObject = hourArray.getJSONObject(ii);
//                            Now extract the forecast info
                            double temperature = (forecastHourObject = hourArray.getJSONObject(ii)).length();
                            int humidity = (int) forecastHourObject.getDouble("temp_c");
//                            get the condition object to work out the weather description
                            JSONObject conditionObject = forecastHourObject.getJSONObject("condition");
//                            get the weather description
                            String weather = conditionObject.getString("text");
//                            get the weather icon
                            String weatherIcon = (String) conditionObject.get("icon");
//                            work out the date and time
                            long timeEpoch = forecastHourObject.getLong("time_epoch");
                            Calendar calendar = Calendar.getInstance();
//                            the time in the forecast json is in seconds, so convert to milliseconds
                            calendar.setTimeInMillis(timeEpoch*1000);

                            int hourOfDay = calendar.get(Calendar.DAY_OF_MONTH);
//                            format the date for display
                            String dayMonth = sdf.format(calendar.getTime());

//                            Create the HourForecast domain object for this hour
                            HourForecast hourForecast = new HourForecast();
                            hourForecast.setTemperature((int) temperature);
                            hourForecast.setHumidity(humidity);
                            hourForecast.setWeather(weather);
                            hourForecast.setIconURL(weatherIcon);
                            hourForecast.setHour(hourOfDay);
                            hourForecast.setDate(dayMonth);

//                            add this hour forecast to the list
                            forecastList.add(hourForecast);
                        }
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), R.string.error_parsing_forecast, Toast.LENGTH_LONG );
                    Log.e(TAG, "Parsing JSON" + e.getLocalizedMessage());
                    e.printStackTrace();
                } finally {
//                    do something with the forecasts that have been downloaded
                    Log.e(TAG, "Downloaded " + forecastList.size() + " forecasts");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), R.string.error_downloading_forecast, Toast.LENGTH_LONG );
                Log.e(TAG, error.getLocalizedMessage());
            }
        });
//        Create a new RequestQueue
        RequestQueue rq = Volley.newRequestQueue(getContext());
//        add the request to make it
        rq.add(request);
    }
}