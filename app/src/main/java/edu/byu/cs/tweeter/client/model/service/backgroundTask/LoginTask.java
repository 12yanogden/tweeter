package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that logs in a user (i.e., starts a session).
 */
public class LoginTask extends AuthenticateTask {
    private static final String LOG_TAG = "LoginTask";

    public LoginTask(String username, String password, Handler messageHandler) {
        super(username, password, messageHandler);
    }

    @Override
    protected Pair<User, AuthToken> authenticate() {
        User user = getFakeData().getFirstUser();
        AuthToken authToken = getFakeData().getAuthToken();

        // Will fully implement in Milestone 3

        return new Pair<>(user, authToken);
    }
}
