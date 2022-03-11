package edu.byu.cs.tweeter.client.model.service.task;

import android.os.Handler;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.Response;

/**
 * Background task that removes a following relationship between two users.
 */
public class UnfollowTask extends FollowUnfollowTask {
    private static final String LOG_TAG = "UnfollowTask";

    public UnfollowTask(AuthToken authToken, User follower, User followee, Handler messageHandler, ServerFacade facade, String urlPath) {
        super(authToken, follower, followee, messageHandler, facade, urlPath);
    }
}
