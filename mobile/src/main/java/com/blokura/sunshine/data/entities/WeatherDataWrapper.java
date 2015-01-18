package com.blokura.sunshine.data.entities;

import com.google.gson.annotations.SerializedName;

import org.apache.http.HttpException;

import rx.Observable;

/**
 * This class represents a Response from openWeatherMap 2.5 api.
 * <p>An example response for the query
 * http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7
 * will return the following response:
 *
 * {"cod":"200","message":0.0274,"city":{"id":"5375480","name":"Mountain View","coord":{"lon":-122.077,"lat":37.4123},"country":"United States of America","population":0},"cnt":7,"list":[{"dt":1421524800,"temp":{"day":17.18,"min":8.49,"max":17.18,"night":8.49,"eve":11.27,"morn":11.64},"pressure":999.16,"humidity":52,"weather":[{"id":802,"main":"Clouds","description":"scattered clouds","icon":"03d"}],"speed":1.31,"deg":72,"clouds":32},{"dt":1421611200,"temp":{"day":17.31,"min":6.31,"max":17.31,"night":6.32,"eve":11.73,"morn":6.31},"pressure":999.32,"humidity":75,"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04d"}],"speed":1.37,"deg":343,"clouds":56},{"dt":1421697600,"temp":{"day":16.53,"min":4.19,"max":16.53,"night":4.19,"eve":8.53,"morn":8.02},"pressure":998.69,"humidity":76,"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04d"}],"speed":1.37,"deg":11,"clouds":68},{"dt":1421784000,"temp":{"day":12.5,"min":0.54,"max":13.29,"night":0.54,"eve":6.21,"morn":4.81},"pressure":994.04,"humidity":74,"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04d"}],"speed":1.34,"deg":14,"clouds":88},{"dt":1421870400,"temp":{"day":11.6,"min":5.38,"max":16.28,"night":8.14,"eve":16.28,"morn":5.38},"pressure":1012.7,"humidity":0,"weather":[{"id":800,"main":"Clear","description":"sky is clear","icon":"01d"}],"speed":1.64,"deg":49,"clouds":0},{"dt":1421956800,"temp":{"day":12.59,"min":5.54,"max":17.75,"night":8.6,"eve":17.75,"morn":5.54},"pressure":1016.67,"humidity":0,"weather":[{"id":800,"main":"Clear","description":"sky is clear","icon":"01d"}],"speed":2.13,"deg":39,"clouds":0},{"dt":1422043200,"temp":{"day":12.22,"min":5.75,"max":18.07,"night":8.61,"eve":18.07,"morn":5.75},"pressure":1020.49,"humidity":0,"weather":[{"id":800,"main":"Clear","description":"sky is clear","icon":"01d"}],"speed":2.03,"deg":39,"clouds":0}]}
 *
 * The first value is always the response code followed by the city model
 * </p>
 */
public class WeatherDataWrapper {

    @SerializedName("cod")
    private int httpCode;

    public Observable filterWebServiceErrors() {
        if (httpCode == 200) {
            return Observable.just(this);
        } else {
            return Observable.error(new HttpException("There was a problem fetching the weather "
                    + "data"));
        }
    }
}
