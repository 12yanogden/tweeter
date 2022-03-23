package edu.byu.cs.tweeter.client.model.service.task;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.response.AuthenticateResponse;

public abstract class AuthenticateTask extends BackgroundTask {
    private static final String LOG_TAG = "AuthenticateTask";
    public static final String USER_KEY = "user";
    public static final String AUTH_TOKEN_KEY = "auth-token";

    /**
     * The user's alias (or "alias" or "handle"). E.g., "@susan".
     */
    private String alias;
    /**
     * The user's password.
     */
    private String password;

    private User user;
    private AuthToken authToken;

    public AuthenticateTask(String alias, String password, Handler messageHandler, ServerFacade facade, String urlPath) {
        super(messageHandler, facade, urlPath);

        this.alias = alias;
        this.password = password;
    }

    @Override
    protected void runTask() throws IOException, TweeterRemoteException {
        AuthenticateResponse response = authenticate(alias, password, getServerFacade(), getUrlPath());

        if (response.isSuccess()) {
            setUser(response.getUser());
            setAuthToken(response.getAuthToken());

            sendSuccessMessage();

        } else {
            sendFailedMessage(response.getMessage());
        }
    }

    protected abstract AuthenticateResponse authenticate(String alias, String password, ServerFacade facade, String urlPath) throws IOException, TweeterRemoteException;

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putSerializable(USER_KEY, user);
        msgBundle.putSerializable(AUTH_TOKEN_KEY, authToken);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
