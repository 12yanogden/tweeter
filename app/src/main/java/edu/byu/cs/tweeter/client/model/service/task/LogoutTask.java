package edu.byu.cs.tweeter.client.model.service.task;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.Request;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.model.net.response.Response;

/**
 * Background task that logs out a user (i.e., ends a session).
 */
public class LogoutTask extends AuthenticatedTask {
    private static final String LOG_TAG = "LogoutTask";

    public LogoutTask(AuthToken authToken, Handler messageHandler, ServerFacade facade, String urlPath) {
        super(authToken, messageHandler, facade, urlPath);
    }

    @Override
    protected void runTask() throws IOException, TweeterRemoteException {
        Request request = new Request();

        Response response = getServerFacade().request(request, getUrlPath(), Response.class);

        if(response.isSuccess()) {
            sendSuccessMessage();
        } else {
            sendFailedMessage(response.getMessage());
        }
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        // No additional data required in msgBundle
    }
}
