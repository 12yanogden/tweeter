package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class PagedUserPresenter extends PagedPresenter<User> {
    protected FollowService followService;

    public PagedUserPresenter(PagedView<User> view) {
        super(view);
    }

    protected FollowService getFollowService() {
        if (followService == null) {
            followService = new FollowService();
        }

        return followService;
    }
}
