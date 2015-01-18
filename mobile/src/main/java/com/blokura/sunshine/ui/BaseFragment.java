package com.blokura.sunshine.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Common fragment that does the default injection.
 *
 * @author Imanol Perez Iriarte
 */
public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        return inflater.inflate(getFragmentLayout(), container, false);
    }

    /**
     * Common method that gets the layout of the fragment and avoid duplicating code.
     *
     * @return the layout for the fragment.
     */
    protected abstract int getFragmentLayout();

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        injectViews(view);
    }

    /**
     * Inject every field annotated with ButterKnife annotations like @InjectView.
     *
     * @param view where the fields will be injected.
     */
    private void injectViews(final View view) {
        ButterKnife.inject(this, view);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity) getActivity()).inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clear the injected views to free memory.
        ButterKnife.reset(this);
    }
}
