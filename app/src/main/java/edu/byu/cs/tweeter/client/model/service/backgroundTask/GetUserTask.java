package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Background task that returns the profile for a specified user.
 */
public class GetUserTask extends UserTask {
    private static final String LOG_TAG = "GetUserTask";
    private final String alias;

    public GetUserTask(AuthToken authToken, String alias, Handler messageHandler) {
        super(authToken, messageHandler);

        this.alias = alias;
    }

    @Override
    protected User getUser() {
        User user = getFakeData().findUserByAlias(alias);

        return user;
    }

    @Override
    protected void processTask() {
        // Will implement in Milestone 3
    }
}
