package edu.byu.cs.tweeter.client.model.service.task;

import android.os.Handler;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;

public abstract class AuthenticatedTask extends BackgroundTask {
    private static final String LOG_TAG = "AuthenticatedTask";
    private AuthToken authToken;

    public AuthenticatedTask(AuthToken authToken, Handler messageHandler, ServerFacade facade, String urlPath) {
        super(messageHandler, facade, urlPath);

        this.authToken = authToken;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }
}
