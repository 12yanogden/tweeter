package edu.byu.cs.tweeter.client.model.service.task;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.response.Response;

public class FollowUnfollowTask extends AuthenticatedTask {
    private User follower;
    private User followee;

    public FollowUnfollowTask(AuthToken authToken, User follower, User followee, Handler messageHandler, ServerFacade facade, String urlPath) {
        super(authToken, messageHandler, facade, urlPath);

        this.follower = follower;
        this.followee = followee;
    }

    protected void runTask() throws IOException, TweeterRemoteException {
        FollowRequest request = new FollowRequest(getFollower().getAlias(), getFollowee().getAlias());

        Response response = getServerFacade().request(request, getUrlPath(), Response.class);

        if(response.isSuccess()) {
            sendSuccessMessage();
        } else {
            sendFailedMessage(response.getMessage());
        }
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putSerializable(USER_KEY, getFollowee());
    }

    public User getFollower() {
        return follower;
    }

    public User getFollowee() {
        return followee;
    }
}
