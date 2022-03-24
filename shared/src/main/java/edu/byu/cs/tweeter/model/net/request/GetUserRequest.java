package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class GetUserRequest extends AuthenticatedRequest {
    private String userAlias;

    public GetUserRequest() {}

    public GetUserRequest(AuthToken authToken, String userAlias) {
        super(authToken);

        this.userAlias = userAlias;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }
}
