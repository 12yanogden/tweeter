package edu.byu.cs.tweeter.client.model.service.task;

import android.os.Handler;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowUnfollowTask extends UserTask {
    private User followee;

    public FollowUnfollowTask(AuthToken authToken, User followee, Handler messageHandler, ServerFacade facade, String urlPath) {
        super(authToken, messageHandler, facade, urlPath);

        this.followee = followee;
    }

    protected void runTask() {

    }

    @Override
    protected User getUser() {
        return followee;
    }
}
