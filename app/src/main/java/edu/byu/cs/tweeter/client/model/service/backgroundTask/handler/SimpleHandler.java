package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleObserver;

public class SimpleHandler extends BackgroundTaskHandler<SimpleObserver> {
    public SimpleHandler(SimpleObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, SimpleObserver observer) {
        observer.handleSuccess();
    }
}
