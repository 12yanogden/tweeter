package edu.byu.cs.tweeter.client.model.service.task;

import android.os.Handler;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Background task that establishes a following relationship between two users.
 */
public class FollowTask extends FollowUnfollowTask {
    private static final String LOG_TAG = "FollowTask";

    public FollowTask(AuthToken authToken, User followee, Handler messageHandler, ServerFacade facade, String urlPath) {
        super(authToken, followee, messageHandler, facade, urlPath);
    }

    protected void runTask() {
        // Will implement in Milestone 3
    }
}
