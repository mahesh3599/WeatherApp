package com.example.weatherapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.model.WeatherModel;
import com.example.weatherapp.repository.WeatherRepository;

/*
* ViewModel class for Main activity
* Requests data from the Weather Repository Class
* */

public class WeatherViewModel extends ViewModel {

    private WeatherRepository weatherRepository;

    public WeatherViewModel(){
        weatherRepository = WeatherRepository.getInstance();
    }

    public LiveData<WeatherModel> observerWeather(){
        return weatherRepository.observerWeather();
    }

    public void weatherApi(String query){
        weatherRepository.weatherApi(query);
    }
}
