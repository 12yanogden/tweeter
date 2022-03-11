package edu.byu.cs.tweeter.client.model.service.task;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;

/**
 * Background task that returns the profile for a specified user.
 */
public class GetUserTask extends UserTask {
    private static final String LOG_TAG = "GetUserTask";
    private final String alias;

    public GetUserTask(AuthToken authToken, String alias, Handler messageHandler, ServerFacade facade, String urlPath) {
        super(authToken, messageHandler, facade, urlPath);

        this.alias = alias;
    }

    @Override
    protected User getUser() {
        User user = getFakeData().findUserByAlias(alias);

        return user;
    }

    protected void runTask() throws IOException, TweeterRemoteException {
        GetUserRequest request = new GetUserRequest(getAlias());

        GetUserResponse response = getServerFacade().request(request, getUrlPath(), GetUserResponse.class);

        if(response.isSuccess()) {
            sendSuccessMessage();
        } else {
            sendFailedMessage(response.getMessage());
        }
    }

    public String getAlias() {
        return alias;
    }
}
