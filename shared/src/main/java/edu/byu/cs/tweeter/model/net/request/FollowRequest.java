package edu.byu.cs.tweeter.model.net.request;

// For follow, unfollow, and isFollower
public class FollowRequest extends Request {
    String targetUserAlias;
    String followeeAlias;

    public FollowRequest(String targetUserAlias, String followeeAlias) {
        this.targetUserAlias = targetUserAlias;
        this.followeeAlias = followeeAlias;
    }

    public String getTargetUserAlias() {
        return targetUserAlias;
    }

    public void setTargetUserAlias(String targetUserAlias) {
        this.targetUserAlias = targetUserAlias;
    }

    public String getFolloweeAlias() {
        return followeeAlias;
    }

    public void setFolloweeAlias(String followeeAlias) {
        this.followeeAlias = followeeAlias;
    }
}
