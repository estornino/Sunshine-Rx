package com.blokura.sunshine.ui;

import com.blokura.sunshine.SunshineModule;
import com.blokura.sunshine.daggerutils.ForActivity;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * This module represents objects which exist only for the scope of a single activity. We can
 * safely create sigletons using the activity instance because the entire object graph will only
 * ever exist inside of that activity.
 *
 * @author Imanol Perez Iriarte
 */
@Module(
        addsTo = SunshineModule.class,
        injects = {
            MainActivity.class,
            WeatherListFragment.class
        },
        library = true)
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    /**
     * Allow the activity context to be injected but require it be annotated with {@link
     * ForActivity @ForActivity} to explicitly differentiate it from application context.
     *
     * @return activity context.
     */
    @ForActivity
    @Provides
    Context provideActivityContext() {
        return activity;
    }
}
