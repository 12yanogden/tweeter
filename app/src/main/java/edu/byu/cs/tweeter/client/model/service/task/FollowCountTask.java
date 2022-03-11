package edu.byu.cs.tweeter.client.model.service.task;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowCountRequest;
import edu.byu.cs.tweeter.model.net.response.FollowCountResponse;

public abstract class FollowCountTask extends AuthenticatedTask {
    private static final String LOG_TAG = "CountTask";
    public static final String COUNT_KEY = "count";

    /**
     * The user whose follower count is being retrieved.
     * (This can be any user, not just the currently logged-in user.)
     */
    private User targetUser;

    private int count;

    public FollowCountTask(AuthToken authToken, User targetUser, Handler messageHandler, ServerFacade facade, String urlPath) {
        super(authToken, messageHandler, facade, urlPath);

        this.targetUser = targetUser;
        this.count = 20;
    }

    @Override
    protected void runTask() throws IOException, TweeterRemoteException {
        FollowCountRequest request = new FollowCountRequest(getTargetUser().getAlias());

        FollowCountResponse response = getServerFacade().request(request, getUrlPath(), FollowCountResponse.class);

        if(response.isSuccess()) {
            sendSuccessMessage();
        } else {
            sendFailedMessage(response.getMessage());
        }
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putInt(COUNT_KEY, count);
    }

    public User getTargetUser() {
        return targetUser;
    }
}
