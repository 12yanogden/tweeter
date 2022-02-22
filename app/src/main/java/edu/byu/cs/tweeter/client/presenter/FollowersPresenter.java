package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.presenter.observer.PagedObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowersPresenter extends PagedUserPresenter {
    public FollowersPresenter(PagedView<User> view) {
        super(view);
    }

    @Override
    protected void getItems(AuthToken authToken, User user, int pageSize, User lastItem) {
        getFollowService().getFollowers(authToken, user, pageSize, lastItem, new PagedObserver<User>(this, "get followers"));
    }
}
