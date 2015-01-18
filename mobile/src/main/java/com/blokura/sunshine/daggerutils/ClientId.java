package com.blokura.sunshine.daggerutils;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation to provide the OpenWeatherApi client id.
 *
 * @author Imanol Perez Iriarte
 */
@Qualifier @Retention(RUNTIME)
public @interface ClientId {

}
