package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;

public abstract class AuthenticateTask extends BackgroundTask {
    private static final String LOG_TAG = "AuthenticateTask";
    public static final String USER_KEY = "user";
    public static final String AUTH_TOKEN_KEY = "auth-token";

    /**
     * The user's username (or "alias" or "handle"). E.g., "@susan".
     */
    private String username;
    /**
     * The user's password.
     */
    private String password;

    private User user;
    private AuthToken authToken;

    public AuthenticateTask(String username, String password, Handler messageHandler) {
        super(messageHandler);

        this.username = username;
        this.password = password;
    }

    @Override
    protected void processTask() {
        Pair<User, AuthToken> result = authenticate();

        user = result.getFirst();
        authToken = result.getSecond();
    }

    protected abstract Pair<User, AuthToken> authenticate();

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putSerializable(USER_KEY, user);
        msgBundle.putSerializable(AUTH_TOKEN_KEY, authToken);
    }
}
