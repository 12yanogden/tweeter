package edu.byu.cs.tweeter.model.net.response;

public class FollowCountResponse extends Response {
    private int count;

    public FollowCountResponse(int count) {
        super(true);

        this.count = count;
    }

    public FollowCountResponse(String message) {
        super(false, message);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
