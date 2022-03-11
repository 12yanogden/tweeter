package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.task.FollowCountTask;
import edu.byu.cs.tweeter.client.model.service.observerInterface.CountObserverInterface;

public class CountHandler extends BackgroundTaskHandler<CountObserverInterface> {
    public CountHandler(CountObserverInterface observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, CountObserverInterface observer) {
        int count = data.getInt(FollowCountTask.COUNT_KEY);

        observer.handleSuccess(count);
    }
}
