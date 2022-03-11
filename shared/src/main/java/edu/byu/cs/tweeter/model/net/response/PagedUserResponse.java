package edu.byu.cs.tweeter.model.net.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public class PagedUserResponse extends PagedResponse<User> {
    public PagedUserResponse(List<User> items, boolean hasMorePages) {
        super(items, hasMorePages);
    }

    public PagedUserResponse(String message) {
        super(message);
    }
}
