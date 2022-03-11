package edu.byu.cs.tweeter.client.model.service.task;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.AuthenticateRequest;
import edu.byu.cs.tweeter.model.net.response.AuthenticateResponse;

/**
 * Background task that logs in a user (i.e., starts a session).
 */
public class LoginTask extends AuthenticateTask {
    private static final String LOG_TAG = "LoginTask";

    public LoginTask(String username, String password, Handler messageHandler, ServerFacade facade, String urlPath) {
        super(username, password, messageHandler, facade, urlPath);
    }

    @Override
    protected AuthenticateResponse authenticate(String username, String password, ServerFacade facade, String urlPath) throws IOException, TweeterRemoteException {
        AuthenticateRequest request = new AuthenticateRequest(username, password);  // TODO: Use getAuthenticateRequest()

        return facade.request(request, urlPath, AuthenticateResponse.class);
    }
}
