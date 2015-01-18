package com.blokura.sunshine.ui;

import com.blokura.sunshine.SunshineApplication;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import java.util.List;

import butterknife.ButterKnife;
import dagger.ObjectGraph;

/**
 * Base activity common to all the activities in the app. In does the basic injection of
 * dependencies.
 *
 * @author Imanol Perez Iriarte
 */
public abstract class BaseActivity extends ActionBarActivity {

    private ObjectGraph activityScopeGraph;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
        injectViews();
    }

    /**
     * Crate a new Dagger ObjectGraph to add new dependencies using a plus operation and inject
     * the declared one in the activity. This new graph will be destroyed once the activity
     * lifecycle finishes.
     *
     * <p>This is the key of how to use Activity scope dependency injection.</p>
     */
    private void injectDependencies() {
        // Create the activity graph by .plus-ing our modules onto the application graph.
        SunshineApplication application = (SunshineApplication) getApplication();
        List<Object> activityScopeModules = getModules();
        activityScopeModules.add(new ActivityModule(this));
        activityScopeGraph = application.plus(activityScopeModules);

        // Inject ourselves so subclasses will have dependencies fulfilled when this method returns.
        inject(this);
    }

    /**
     * A list of modules to use for the individual activity graph. Subclasses must implement this
     * method to provide additional modules provided they call and include the modules returned.
     * This method can be replaced by a simple method that returns a new ActivityModule and then
     * other activities can override it to add new dependencies.
     *
     * @return a list (might be empty) or additional modules.
     */
    protected abstract List<Object> getModules();

    /**
     * Inject the supplied object (generally an Activity or a Fragment) to provide every @Inject
     * annotation contained using the activity-specific graph.
     *
     * @param object to inject.
     */
    public void inject(Object object) {
        activityScopeGraph.inject(object);
    }

    /**
     * Inject every field annotated with ButterKnife annotations like @InjectView.
     */
    private void injectViews() {
        ButterKnife.inject(this);
    }

    @Override
    protected void onDestroy() {
        // Eagerly clear the reference to the activity graph to allow it to be garbage collected
        // as soon as possible.
        activityScopeGraph = null;
        super.onDestroy();
    }
}
