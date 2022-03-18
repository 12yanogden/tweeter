package edu.byu.cs.tweeter.model.net.request;

public class FollowCountRequest extends Request {
    private String targetUserAlias;

    public FollowCountRequest () {}

    public FollowCountRequest(String targetUserAlias) {
        this.targetUserAlias = targetUserAlias;
    }

    public String getTargetUserAlias() {
        return targetUserAlias;
    }

    public void setTargetUserAlias(String targetUserAlias) {
        this.targetUserAlias = targetUserAlias;
    }
}
