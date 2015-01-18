package com.blokura.sunshine.data;

import com.blokura.sunshine.core.models.WeatherForecast;
import com.blokura.sunshine.data.api.ForecastService;
import com.blokura.sunshine.data.entities.WeatherForecastListDataWrapper;
import com.blokura.sunshine.rxutils.EndObserver;

import android.location.Location;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * In memory cache ripped from Jake Wharton u2020
 *
 * @author Imanol Perez Iriarte
 */
@Singleton
public class ForecastDatabase {

    private final ForecastService forecastService;

    private final Map<Location, List<WeatherForecast>> forecastCache = new LinkedHashMap<>();
    private final Map<Location, PublishSubject<List<WeatherForecast>>> forecastRequests = new
            LinkedHashMap<>();

    @Inject
    public ForecastDatabase(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    public Subscription loadForecast(final Location location, final String units,
            final int count, Observer<List<WeatherForecast>> observer) {
        List<WeatherForecast> forecasts = forecastCache.get(location);
        if (forecasts != null) {
            // We have a cached value. Emit it inmediately.
            observer.onNext(forecasts);
        }

        PublishSubject<List<WeatherForecast>> forecastRequest = forecastRequests.get(location);
        if (forecastRequest != null) {
            // There's an in-flight network request for this section already. Join it.
            return forecastRequest.subscribe(observer);
        }

        forecastRequest = PublishSubject.create();
        forecastRequests.put(location, forecastRequest);

        Subscription subscription = forecastRequest.subscribe(observer);

        forecastRequest.subscribe(new EndObserver<List<WeatherForecast>>() {
            @Override
            public void onEnd() {
                forecastRequests.remove(location);
            }

            @Override
            public void onNext(final List<WeatherForecast> weatherForecasts) {
                forecastCache.put(location, weatherForecasts);
            }
        });

        forecastService.fetchForecast(location.getLatitude(), location.getLongitude(), units, count)
                .flatMap(
                        new Func1<WeatherForecastListDataWrapper, Observable<? extends WeatherForecastListDataWrapper>>() {
                            @Override
                            public Observable<? extends WeatherForecastListDataWrapper> call(
                                    final WeatherForecastListDataWrapper dataList) {
                                return dataList.filterWebServiceErrors();
                            }
                        })
                .map(new Func1<WeatherForecastListDataWrapper, List<WeatherForecast>>() {
                    @Override
                    public List<WeatherForecast> call(
                            final WeatherForecastListDataWrapper dataList) {
                        final ArrayList<WeatherForecast> forecastList = new
                                ArrayList<WeatherForecast>();
                        for (WeatherForecastListDataWrapper.ForecastDataEnvelope data : dataList
                                .list) {
                            final WeatherForecast weatherForecast = new WeatherForecast(dataList
                                    .city.name, data.timestamp, data.weather.get(0).description,
                                    data.getMinTemp(), data.getMaxTemp());
                            forecastList.add(weatherForecast);
                        }

                        return forecastList;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(forecastRequest);
        return subscription;
    }

    public Observable<List<WeatherForecast>> fetchWeatherForecasts(final Location location,
            String units, int count) {
        return forecastService.fetchForecast(location.getLatitude(), location.getLongitude(),
                units, count)
                .flatMap(new Func1<WeatherForecastListDataWrapper,
                        Observable<? extends WeatherForecastListDataWrapper>>(){

                    @Override
                    public Observable<? extends WeatherForecastListDataWrapper> call(
                            final WeatherForecastListDataWrapper listData) {
                        return listData.filterWebServiceErrors();
                    }
                })
                .map(new Func1<WeatherForecastListDataWrapper, List<WeatherForecast>>() {
                    @Override
                    public List<WeatherForecast> call(
                            final WeatherForecastListDataWrapper dataList) {
                        // Parse the result and build the list
                        final ArrayList<WeatherForecast> forecastList = new
                                ArrayList<WeatherForecast>();
                        for (WeatherForecastListDataWrapper.ForecastDataEnvelope data : dataList
                                .list) {
                            final WeatherForecast weatherForecast = new WeatherForecast(dataList
                                    .city.name, data.timestamp, data.weather.get(0).description,
                                    data.getMinTemp(), data.getMaxTemp());
                            forecastList.add(weatherForecast);
                        }

                        return forecastList;
                    }
                });
    }

}
