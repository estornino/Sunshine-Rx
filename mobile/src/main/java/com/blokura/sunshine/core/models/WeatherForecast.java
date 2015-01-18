package com.blokura.sunshine.core.models;

/**
 * Created by imanol on 18/01/15.
 */
public class WeatherForecast {

    private final String locationName;
    private final long timeStamp;
    private final String description;
    private final float minTemp;
    private final float maxTemp;

    public WeatherForecast(final String locationName, final long timeStamp,
            final String description, final float minTemp,
            final float maxTemp) {
        this.locationName = locationName;
        this.timeStamp = timeStamp;
        this.description = description;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }

    public String getLocationName() {
        return locationName;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getDescription() {
        return description;
    }

    public float getMinTemp() {
        return minTemp;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    @Override
    public String toString() {
        return "WeatherForecast{" +
                "locationName='" + locationName + '\'' +
                ", minTemp=" + minTemp +
                ", maxTemp=" + maxTemp +
                '}';
    }
}
