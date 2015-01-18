package com.blokura.sunshine.data.entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * This entity represents the result of a call to the current weather service at OpenWeather api 2.5
 * <p>If we make a call to api.openweathermap.org/data/2.5/weather?id=2172797 the result will be
 * the following json
 * {"coord":{"lon":145.77,"lat":-16.92},"sys":{"message":0.0364,"country":"AU","sunrise":1421524637,"sunset":1421571437},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04n"}],"base":"cmc stations","main":{"temp":299.045,"temp_min":299.045,"temp_max":299.045,"pressure":1004.25,"sea_level":1021.83,"grnd_level":1004.25,"humidity":100},"wind":{"speed":1.18,"deg":279},"clouds":{"all":92},"dt":1421603890,"id":2172797,"name":"Cairns","cod":200}
 * If we take a closer look we can find the following objects and attributes:
 * </p>
 * <ul>
 *     <li>"dt": represents the timestamp</li>
 *     <li>"cod": represents the response Http code</li>
 *     <li>"id": the request id</li>
 *     <li>"name": the location name</li>
 *     <li>"base: don't know</li>
 *     <li>"coord": a location object with 2 values: longitude and latitude</li>
 *     <li>"sys": an object that contains a message, the country, and the timestamps for the
 *     sunrise and sunsets</li>
 *     <li>"weather": A list of weather objects. Each object contains an id, the weather(clouds,
 *     etc), a description, and an identifier for an icon.
 *     </li>
 *     <li>"main": An object that contains the current temperature, the min and the max,
 *     the pressure, sea level, ground level and humidity</li>
 *     <li>"wind": An object that contains the speed and the orientation in degrees</li>
 *     <li>"clouds": An object with one attribute called all.</li>
 * </ul>
 */
public class CurrentWeatherDataWrapper extends WeatherDataWrapper {

    @SerializedName("name")
    public String locationName;

    @SerializedName("dt")
    public long timeStamp;

    public ArrayList<Weather> weather;

    public Main main;

}
