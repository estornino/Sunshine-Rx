package com.blokura.sunshine.daggerutils;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation that qualifies the Activity context.
 *
 * @author Imanol Perez Iriarte
 */
@Qualifier @Retention(RUNTIME)
public @interface ForActivity {

}
