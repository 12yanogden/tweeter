package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.observerInterface.SimpleObserverInterface;

public class SimpleHandler extends BackgroundTaskHandler<SimpleObserverInterface> {
    public SimpleHandler(SimpleObserverInterface observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, SimpleObserverInterface observer) {
        observer.handleSuccess();
    }
}
