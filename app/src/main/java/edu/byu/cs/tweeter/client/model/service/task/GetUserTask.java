package edu.byu.cs.tweeter.client.model.service.task;

import android.os.Handler;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

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

    protected void runTask() {
        // Will implement in Milestone 3
    }
}
