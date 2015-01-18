package com.blokura.sunshine.rxutils;

import rx.Observer;

/**
 * Observer that you only have to implement {@link OnNext(Object)}
 *
 * @author Imanol Perez Iriarte
 */
public abstract class EndlessObserver<T> implements Observer<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(final Throwable e) {

    }
}
