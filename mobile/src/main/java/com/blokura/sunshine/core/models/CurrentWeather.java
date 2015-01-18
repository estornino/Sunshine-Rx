package com.blokura.sunshine.core.models;

/**
 * Created by imanol on 18/01/15.
 */
public class CurrentWeather extends WeatherForecast {

    private final float currentTemp;

    public CurrentWeather(final String locationName, final long timeStamp,
            final String description, final float minTemp, final float maxTemp,
            final float currentTemp) {
        super(locationName, timeStamp, description, minTemp, maxTemp);
        this.currentTemp = currentTemp;
    }

    public float getCurrentTemp() {
        return currentTemp;
    }
}
