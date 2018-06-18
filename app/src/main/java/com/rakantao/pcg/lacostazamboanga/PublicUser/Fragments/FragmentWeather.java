package com.rakantao.pcg.lacostazamboanga.PublicUser.Fragments;

import android.Manifest;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.pavlospt.CircleView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rakantao.pcg.lacostazamboanga.PublicUser.Activities.UserHomeActivity;
import com.rakantao.pcg.lacostazamboanga.R;
import com.rakantao.pcg.lacostazamboanga.adapters.RecyclerViewAdapter;
import com.rakantao.pcg.lacostazamboanga.entity.WeatherObject;
import com.rakantao.pcg.lacostazamboanga.helpers.CustomSharedPreference;
import com.rakantao.pcg.lacostazamboanga.helpers.Helper;
import com.rakantao.pcg.lacostazamboanga.json.FiveDaysForecast;
import com.rakantao.pcg.lacostazamboanga.json.FiveWeathers;
import com.rakantao.pcg.lacostazamboanga.json.Forecast;
import com.rakantao.pcg.lacostazamboanga.json.LocationMapObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class FragmentWeather extends Fragment implements LocationListener {

    private static final String TAG = UserHomeActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private TextView cityCountry;
    private TextView currentDate;
    private ImageView weatherImage;
    private CircleView circleTitle;
    private TextView windResult;
    private TextView humidityResult;
    private RequestQueue queue;
    private LocationMapObject locationMapObject;
    private LocationManager locationManager;
    private Location location;
    private final int REQUEST_LOCATION = 200;
    private CustomSharedPreference sharedPreference;
    private String isLocationSaved;



    private String apiUrl;

     FiveDaysForecast fiveDaysForecast;
    View view;

    public FragmentWeather() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_weather, container, false);

        queue = Volley.newRequestQueue(getActivity());
        sharedPreference = new CustomSharedPreference(getActivity());
        isLocationSaved = sharedPreference.getLocationInPreference();

        cityCountry = view.findViewById(R.id.city_country);
        currentDate = view.findViewById(R.id.current_date);
        weatherImage = view.findViewById(R.id.weather_icon);
        circleTitle = view.findViewById(R.id.weather_result);
        windResult = view.findViewById(R.id.wind_result);
        humidityResult = view.findViewById(R.id.humidity_result);

        locationManager = (LocationManager)getActivity().getSystemService(Service.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            if(isLocationSaved.equals("")){
                // make API call with longitude and latitude
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 2, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    if (location.getAltitude() == 0.0 || location.getLongitude() == 0.0){
                        Toast.makeText(getContext(), "Unable to fetch your location", Toast.LENGTH_SHORT).show();
                        showGPSDisabledAlertToUser();
                    }else {
                        apiUrl = "http://api.openweathermap.org/data/2.5/weather?lat="+location.getLatitude()+"&lon="+location.getLongitude()+"&APPID="+ Helper.API_KEY+"&units=metric";
                        makeJsonObject(apiUrl);
                    }


                }
            }else{
                // make API call with city name
                String storedCityName = sharedPreference.getLocationInPreference();
                //String storedCityName = "Enugu";
                System.out.println("Stored city " + storedCityName);
                String[] city = storedCityName.split(",");
                if(!TextUtils.isEmpty(city[0])){
                    System.out.println("Stored city " + city[0]);
                    String url ="http://api.openweathermap.org/data/2.5/weather?q="+city[0]+"&APPID="+ Helper.API_KEY+"&units=metric";
                    makeJsonObject(url);
                }
            }
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);

        recyclerView = view.findViewById(R.id.weather_daily_list);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        return view;
    }

    private void makeJsonObject(final String apiUrl){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response " + response);
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                locationMapObject = gson.fromJson(response, LocationMapObject.class);
                if (null == locationMapObject) {
                    Toast.makeText(getActivity().getApplicationContext(), "Nothing was returned", Toast.LENGTH_LONG).show();
                } else {

                    String city = locationMapObject.getName() + ", " + locationMapObject.getSys().getCountry();
                    String todayDate = getTodayDateInStringFormat();
                    Long tempVal = Math.round(Math.floor(Double.parseDouble(locationMapObject.getMain().getTemp())));
                    String weatherTemp = String.valueOf(tempVal) + "°";
                    String weatherDescription = Helper.capitalizeFirstLetter(locationMapObject.getWeather().get(0).getDescription());
                    String windSpeed = locationMapObject.getWind().getSpeed();
                    String humidityValue = locationMapObject.getMain().getHumudity();

                    //save location in database

                    // populate View data
                    cityCountry.setText(Html.fromHtml(city));
                    currentDate.setText(Html.fromHtml(todayDate));
                    circleTitle.setTitleText(Html.fromHtml(weatherTemp).toString());
                    circleTitle.setSubtitleText(Html.fromHtml(weatherDescription).toString());
                    windResult.setText(Html.fromHtml(windSpeed) + " km/h");
                    humidityResult.setText(Html.fromHtml(humidityValue) + " %");

                    fiveDaysApiJsonObjectCall(locationMapObject.getName());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error " + error.getMessage());
            }
        });
        queue.add(stringRequest);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //make api call
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 2, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        apiUrl = "http://api.openweathermap.org/data/2.5/weather?lat="+location.getLatitude()+"&lon="+location.getLongitude()+"&APPID="+Helper.API_KEY+"&units=metric";
                        makeJsonObject(apiUrl);
                    }else{
                        apiUrl = "http://api.openweathermap.org/data/2.5/weather?lat=51.5074&lon=0.1278&APPID="+Helper.API_KEY+"&units=metric";
                        makeJsonObject(apiUrl);
                    }
                }
            }else{
                Toast.makeText(getActivity(), getString(R.string.permission_notice), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {


    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            showGPSDisabledAlertToUser();
        }
    }
    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);
                    }
                });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    private String getTodayDateInStringFormat(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("E, d MMMM", Locale.getDefault());
        return df.format(c.getTime());
    }

    private void fiveDaysApiJsonObjectCall(String city){
        String apiUrl = "http://api.openweathermap.org/data/2.5/forecast?q="+city+ "&APPID="+Helper.API_KEY+"&units=metric";
        final List<WeatherObject> daysOfTheWeek = new ArrayList<WeatherObject>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response 5 days" + response);
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Forecast forecast = gson.fromJson(response, Forecast.class);
                if (null == forecast) {
                    /*Toast.makeText(getActivity().getApplicationContext(), "Nothing was returned", Toast.LENGTH_LONG).show();*/
                } else {
                    /*Toast.makeText(getActivity().getApplicationContext(), "Response Good", Toast.LENGTH_LONG).show();*/

                    int[] everyday = new int[]{0,0,0,0,0,0,0};

                    List<FiveWeathers> weatherInfo = forecast.getList();
                    if(null != weatherInfo){
                        for(int i = 0; i < weatherInfo.size(); i++){
                            String time = weatherInfo.get(i).getDt_txt();
                            String shortDay = convertTimeToDay(time);
                            String temp = weatherInfo.get(i).getMain().getTemp();
                            String tempMin = weatherInfo.get(i).getMain().getTemp_min();

                            if(convertTimeToDay(time).equals("Mon") && everyday[0] < 1){
                                daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                                everyday[0] = 1;
                            }
                            if(convertTimeToDay(time).equals("Tue") && everyday[1] < 1){
                                daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                                everyday[1] = 1;
                            }
                            if(convertTimeToDay(time).equals("Wed") && everyday[2] < 1){
                                daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                                everyday[2] = 1;
                            }
                            if(convertTimeToDay(time).equals("Thu") && everyday[3] < 1){
                                daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                                everyday[3] = 1;
                            }
                            if(convertTimeToDay(time).equals("Fri") && everyday[4] < 1){
                                daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                                everyday[4] = 1;
                            }
                            if(convertTimeToDay(time).equals("Sat") && everyday[5] < 1){
                                daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                                everyday[5] = 1;
                            }
                            if(convertTimeToDay(time).equals("Sun") && everyday[6] < 1){
                                daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                                everyday[6] = 1;
                            }
                            recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), daysOfTheWeek);
                            recyclerView.setAdapter(recyclerViewAdapter);
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error " + error.getMessage());
            }
        });
        queue.add(stringRequest);
    }

    private String convertTimeToDay(String time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SSSS", Locale.getDefault());
        String days = "";
        try {
            Date date = format.parse(time);
            System.out.println("Our time " + date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            days = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
            System.out.println("Our time " + days);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }
}

