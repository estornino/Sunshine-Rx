package com.blokura.sunshine.data.entities;

/**
 * This class is a model for the OpenWeather API call to the get current weather service.
 * <p>The json response contains the following params:
 * main: {
 *  temp: 299.045
 *  temp_min: 299.045
 *  temp_max: 299.045
 *  pressure: 1004.25
 *  sea_level: 1021.83
 *  grnd_level: 1004.25
 *  humidity: 100
 * }
 * You can extend this object by adding the remaining params as members. Remember to make name
 * them the same way as in the json response to allow the Gson parser to parse it automatically.
 * If you want to change the names, check Gson docs.
 * </p>
 */
public class Main {

    public float temp;
    public float temp_min;
    public float temp_max;

}
