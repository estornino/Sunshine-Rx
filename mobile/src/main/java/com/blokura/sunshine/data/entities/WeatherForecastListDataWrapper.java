package com.blokura.sunshine.data.entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * This class represents the response of the Open Weather data api to a query for the Forecast.
 */
public class WeatherForecastListDataWrapper extends WeatherDataWrapper {

    public Location city;
    public ArrayList<ForecastDataEnvelope> list;

    public class Location {
        public String name;
    }

    public class ForecastDataEnvelope {
        @SerializedName("dt")
        public long timestamp;

        public Temperature temp;
        public ArrayList<Weather> weather;

        public float getMaxTemp() {
            return temp.max;
        }

        public float getMinTemp() {
            return temp.min;
        }
    }

    class Temperature {
        public float min;
        public float max;
    }
}
