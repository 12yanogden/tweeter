package edu.byu.cs.tweeter.client.model.service.task;

import android.os.Handler;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Background task that retrieves a page of other users being followed by a specified user.
 */
public class GetFollowingTask extends PagedUserTask {
    private static final String LOG_TAG = "GetFollowingTask";
    public static final String FOLLOWEES_KEY = "followees";

    public GetFollowingTask(AuthToken authToken, User targetUser, int limit, User lastFollowee,
                            Handler messageHandler, ServerFacade facade, String urlPath) {
        super(authToken, targetUser, limit, lastFollowee, messageHandler, facade, urlPath);
    }
}
