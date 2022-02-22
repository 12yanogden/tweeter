package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public abstract class AuthenticatedTask extends BackgroundTask { // TODO: Should/can inheritance be reflected in directory structure?
    private static final String LOG_TAG = "AuthenticatedTask";
    private AuthToken authToken;

    public AuthenticatedTask(AuthToken authToken, Handler messageHandler) {
        super(messageHandler);

        this.authToken = authToken;
    }
}
