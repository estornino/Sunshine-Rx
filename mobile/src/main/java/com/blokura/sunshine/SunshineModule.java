package com.blokura.sunshine;

import com.blokura.sunshine.daggerutils.ForApplication;
import com.blokura.sunshine.data.DataModule;

import android.app.Application;
import android.content.Context;
import android.location.LocationManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Base Dagger module created to work as junction between all the modules in the application scope.
 * <p>It includes the data module, interactor module and ui module. It injects into the {@link
 * SunshineApplication} class and it is marked as library because it does not consume all the
 * dependencies that it provides.</p>
 *
 * @author Imanol Perez Iriarte
 */
@Module(
        includes = {DataModule.class},
        injects = {SunshineApplication.class},
        library = true
)
public class SunshineModule {

    private final SunshineApplication app;

    public SunshineModule(SunshineApplication app) {
        this.app = app;
    }

    @Provides
    @Singleton
    @ForApplication
    Application provideApplicationContext() {
        return app;
    }

    @Provides
    LocationManager provideLocationManager() {
        return (LocationManager) app.getSystemService(Context.LOCATION_SERVICE);
    }

}
