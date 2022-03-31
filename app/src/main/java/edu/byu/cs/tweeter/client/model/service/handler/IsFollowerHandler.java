package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.task.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.observerInterface.IsFollowerObserverInterface;

public class IsFollowerHandler extends BackgroundTaskHandler<IsFollowerObserverInterface> {
    public IsFollowerHandler(IsFollowerObserverInterface observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, IsFollowerObserverInterface observer) {
        boolean isFollower = data.getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);

        System.out.println("isFollowerHandler, isFollower: " + isFollower);

        observer.handleSuccess(isFollower);
    }
}
