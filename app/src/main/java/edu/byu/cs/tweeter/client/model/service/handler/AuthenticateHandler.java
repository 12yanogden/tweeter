package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.task.AuthenticateTask;
import edu.byu.cs.tweeter.client.model.service.task.LoginTask;
import edu.byu.cs.tweeter.client.model.service.observerInterface.AuthenticateObserverInterface;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class AuthenticateHandler extends BackgroundTaskHandler<AuthenticateObserverInterface>{
    public AuthenticateHandler(AuthenticateObserverInterface observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, AuthenticateObserverInterface observer) {
        User user = (User) data.getSerializable(LoginTask.USER_KEY);
        AuthToken authToken = (AuthToken) data.getSerializable(AuthenticateTask.AUTH_TOKEN_KEY);

        observer.handleSuccess(user, authToken);
    }
}
