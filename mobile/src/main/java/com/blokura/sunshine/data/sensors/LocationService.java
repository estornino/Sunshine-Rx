package com.blokura.sunshine.data.sensors;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;

import rx.Observable;
import rx.Subscriber;

/**
 * Implement a Rx-style location service by wrapping the Android LocationManager and providing
 * the location result as an Observable
 */
public class LocationService {

    private final LocationManager locationManager;

    public LocationService(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    public Observable<Location> getLocation() {
        return Observable.create(new Observable.OnSubscribe<Location>() {

            @Override
            public void call(final Subscriber<? super Location> subscriber) {
                final LocationListener locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(final Location location) {
                        subscriber.onNext(location);
                        subscriber.onCompleted();
                        Looper.myLooper().quit();
                    }

                    @Override
                    public void onStatusChanged(final String provider, final int status,
                            final Bundle extras) {
                        // Nothing here
                    }

                    @Override
                    public void onProviderEnabled(final String provider) {
                        // Nothing here
                    }

                    @Override
                    public void onProviderDisabled(final String provider) {
                        // Nothing here
                    }
                };

                final Criteria locationCriteria = new Criteria();
                locationCriteria.setAccuracy(Criteria.ACCURACY_COARSE);
                locationCriteria.setPowerRequirement(Criteria.POWER_LOW);
                final String locationProvider = locationManager.getBestProvider(locationCriteria,
                        true);

                Looper.prepare();
                locationManager.requestSingleUpdate(locationProvider, locationListener, Looper.myLooper());
                Looper.loop();

            }
        });
    }

}
