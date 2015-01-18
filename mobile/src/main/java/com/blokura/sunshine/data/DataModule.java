package com.blokura.sunshine.data;

import com.blokura.sunshine.daggerutils.ForApplication;
import com.blokura.sunshine.data.api.ApiModule;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Data module that provides data layer dependencies.
 *
 * @author Imanol Perez Iriarte
 */
@Module(
        includes = ApiModule.class,
        complete = false,
        library = true)
public final class DataModule {

    static final int DISK_CACHE_SIZE = 2 * 1024 * 1024; // 2 MB

    public DataModule() {
        // Do nothing
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(@ForApplication Application app) {
        return app.getSharedPreferences("shunshine", Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(@ForApplication Application app){
        return createOkHttpClient(app);
    }

    static OkHttpClient createOkHttpClient(Application app) {
        OkHttpClient client = new OkHttpClient();
        // Install an HTTP cache in the application cache directory.
        try {
            File cacheDir = new File(app.getCacheDir(), "http");
            Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
            client.setCache(cache);
        } catch (IOException e) {
//          TODO:  Timber.e(e, "Unable to install disk cache.");
        }

        return client;
    }

    @Provides
    @Singleton
    LocalForecastEntry provideForecastCatalog() {
        return new LocalForecastEntry();
    }

}
