package com.blokura.sunshine.rxutils;

import rx.Observer;

/**
 * An {@link Observer} that always informs when it is finished
 *
 * @author Imanol Perez Iriarte
 */
public abstract class EndObserver<T> implements Observer<T> {

    @Override
    public void onCompleted() {
        onEnd();
    }

    @Override
    public void onError(final Throwable e) {
        onEnd();
    }

    public abstract void onEnd();
}
