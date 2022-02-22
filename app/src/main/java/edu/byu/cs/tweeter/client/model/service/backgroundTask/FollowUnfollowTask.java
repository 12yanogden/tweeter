package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowUnfollowTask extends UserTask {
    private User followee;

    public FollowUnfollowTask(AuthToken authToken, User followee, Handler messageHandler) {
        super(authToken, messageHandler);

        this.followee = followee;
    }

    @Override
    protected void processTask() {

    }

    @Override
    protected User getUser() {
        return followee;
    }
}
