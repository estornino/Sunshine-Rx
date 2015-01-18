package com.blokura.sunshine.data.api;


import com.blokura.sunshine.data.entities.CurrentWeatherDataWrapper;
import com.blokura.sunshine.data.entities.WeatherForecastListDataWrapper;

import retrofit.Endpoint;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * This interfaces defines a Retrofit {@link Endpoint} for fetching the forecast
 *
 * @author Imanol Perez Iriarte
 */
public interface ForecastService {
    @GET("/forecast/daily")
    Observable<WeatherForecastListDataWrapper> fetchForecast(
            @Query("lat") Double latitude,
            @Query("lon") Double longitude,
            @Query("units") String units,
            @Query("cnt") int count
    );

    @GET("/weather")
    Observable<CurrentWeatherDataWrapper> fetchCurrentWeather(
            @Query("lat") Double latitude,
            @Query("lon") Double longitude,
            @Query("units") String units
    );

}
