package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

// For follow, unfollow, and isFollower
public class FollowRequest extends AuthenticatedRequest {
    User follower;
    User followee;

    public FollowRequest() {}

    public FollowRequest(AuthToken authToken, User follower, User followee) {
        super(authToken);

        this.follower = follower;
        this.followee = followee;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollowee() {
        return followee;
    }

    public void setFollowee(User followee) {
        this.followee = followee;
    }
}
