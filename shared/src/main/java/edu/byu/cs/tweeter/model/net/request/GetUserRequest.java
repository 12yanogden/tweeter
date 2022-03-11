package edu.byu.cs.tweeter.model.net.request;

public class GetUserRequest extends Request {
    private String userAlias;

    public GetUserRequest(String userAlias) {
        this.userAlias = userAlias;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }
}
