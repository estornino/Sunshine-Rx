package com.blokura.sunshine.data.entities;

/**
 * This object represents a Weather Object in the response from the OpenWeather Api
 * <p>If we make a query to the api the Weather json object contains the following params:
 * id: 802
 * main: "Clouds"
 * description: "scattered clouds"
 * icon: "03d"
 * We are going to use only the description param.
 * </p>
 */
public class Weather {

    public String description;

}
