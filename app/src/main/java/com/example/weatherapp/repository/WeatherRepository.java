package com.example.weatherapp.repository;

import androidx.lifecycle.LiveData;

import com.example.weatherapp.model.WeatherModel;
import com.example.weatherapp.request.RemoteApi;

/*
   * This class basically call RemoteDataSourceApi for getting city data
   * this additional class is created to change conditions or api end points
 */

public class WeatherRepository {

    private RemoteApi remoteApi;

    private static WeatherRepository instance;

    public static WeatherRepository getInstance(){
        if (instance == null){
            instance = new WeatherRepository();
        }
        return instance;
    }

    private WeatherRepository(){
        remoteApi = RemoteApi.getInstance();
    }

    public LiveData<WeatherModel> observerWeather(){
        return remoteApi.getWeatherModelMutableLiveData();
    }

    public void weatherApi(String query){
        remoteApi.weatherApi(query);
    }
}
