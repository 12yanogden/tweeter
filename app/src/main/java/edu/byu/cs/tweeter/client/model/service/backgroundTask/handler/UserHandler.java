package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.UserObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class UserHandler extends BackgroundTaskHandler<UserObserver> {
    public UserHandler(UserObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, UserObserver observer) {
        User user = (User) data.getSerializable(GetUserTask.USER_KEY);

        observer.handleSuccess(user);
    }
}
