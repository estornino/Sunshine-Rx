package com.blokura.sunshine.data.api;

import com.blokura.sunshine.daggerutils.ClientId;
import com.blokura.sunshine.data.DataModule;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;

/**
 * This module provides dependencies for the REST api service. It is not a complete module as it
 * needs to be included by {@link DataModule} to fulfill its dependencies.
 *
 * @author Imanol Perez Iriarte
 */
@Module(
        complete = false,
        library = true
)
public class ApiModule {

    // TODO: Remove this before publishing to github
    private static final String CLIENT_ID = "d3a743348087a7e1329ea2d6971d69d3";
    // Example:http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7
    public static final String PRODUCTION_API_URL = "http://api.openweathermap.org/data/2.5";

    @Provides
    @Singleton
    @ClientId
    String provideClientId() {
        return CLIENT_ID;
    }

    @Provides
    @Singleton
    Endpoint provideEndpoint() {
        return Endpoints.newFixedEndpoint(PRODUCTION_API_URL);
    }

    @Provides
    @Singleton
    Client provideClient(OkHttpClient client) {
        return new OkClient(client);
    }

    @Provides
    @Singleton
    RestAdapter provideRestAdapter(Endpoint endpoint, Client client, ApiHeaders headers) {
        return new RestAdapter.Builder()
                .setClient(client)
                .setEndpoint(endpoint)
                .setRequestInterceptor(headers)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
    }

    @Provides @Singleton ForecastService provideForeCastService(RestAdapter restAdapter) {
        return restAdapter.create(ForecastService.class);
    }
}
