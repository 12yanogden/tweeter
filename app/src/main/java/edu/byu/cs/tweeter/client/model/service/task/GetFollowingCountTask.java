package edu.byu.cs.tweeter.client.model.service.task;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowCountRequest;
import edu.byu.cs.tweeter.model.net.response.FollowCountResponse;

/**
 * Background task that queries how many other users a specified user is following.
 */
public class GetFollowingCountTask extends FollowCountTask {
    private static final String LOG_TAG = "GetFollowingCountTask";

    public GetFollowingCountTask(AuthToken authToken, User targetUser, Handler messageHandler, ServerFacade facade, String urlPath) {
        super(authToken, targetUser, messageHandler, facade, urlPath);
    }
}
