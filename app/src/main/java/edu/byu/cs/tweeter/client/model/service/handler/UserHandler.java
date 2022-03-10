package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.task.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.observerInterface.UserObserverInterface;
import edu.byu.cs.tweeter.model.domain.User;

public class UserHandler extends BackgroundTaskHandler<UserObserverInterface> {
    public UserHandler(UserObserverInterface observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, UserObserverInterface observer) {
        User user = (User) data.getSerializable(GetUserTask.USER_KEY);

        observer.handleSuccess(user);
    }
}
