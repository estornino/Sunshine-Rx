package com.blokura.sunshine.data.api;

import com.blokura.sunshine.daggerutils.ClientId;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.RequestInterceptor;

/**
 * This class add authentication to all the Rest Requests.
 *
 * @author Imanol Perez Iriarte
 */
@Singleton
public class ApiHeaders implements RequestInterceptor {

    private final String authorizationValue;

    @Inject
    public ApiHeaders(@ClientId String clientId) {
        authorizationValue = clientId;
    }

    @Override
    public void intercept(final RequestFacade request) {
        request.addHeader("x-api-key", authorizationValue);
        request.addHeader("Accept", "application/json");
    }
}
