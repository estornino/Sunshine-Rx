package com.blokura.sunshine;

import android.app.Application;

import java.util.List;

import dagger.ObjectGraph;

/**
 * Android {@link Application} extension that will handle the ObjectGraph.
 * <p>Dagger is the dependency injector for this project. The ObjectGraph field created in this
 * class is the dependency container that is going to provide every dependency declared in the
 * Dagger modules.</p>
 *
 * @author Imanol Perez Iriarte
 */
public class SunshineApplication extends Application {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeDependencies();
    }

    /**
     * Set up the injector by injecting the basic module and statics.
     */
    private void initializeDependencies() {
        objectGraph = ObjectGraph.create(new SunshineModule(this));
        objectGraph.inject(this);
        objectGraph.injectStatics();
    }

    /**
     * Inject the dependencies declared with the @Inject annotation if they have already been
     * initialized by Dagger.
     *
     * @param object object to inject dependencies.
     */
    public void inject(Object object) {
        objectGraph.inject(object);
    }

    /**
     * Extend the dependency container graph will new dependencies provided by the given modules.
     *
     * @param modules used to populate the dependency container.
     *
     * @return the object graph extended with the given modules.
     */
    public ObjectGraph plus(List<Object> modules) {
        if (modules == null) {
            throw new IllegalArgumentException("You must provide non null modules to inject. "
                    + "Review your getModules() implementation");
        }

        return objectGraph.plus(modules.toArray());
    }

}
