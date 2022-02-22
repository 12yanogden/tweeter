package edu.byu.cs.tweeter.client.presenter.observer;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.UserObserver;
import edu.byu.cs.tweeter.client.presenter.MainPresenter;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowUnfollowObserver extends MainObserver implements UserObserver {
    private final boolean isFollower;

    public FollowUnfollowObserver(MainPresenter presenter, String description, boolean isFollower) {
        super(presenter, description);

        this.isFollower = isFollower;
    }

    @Override
    public void handleSuccess(User user) {
        presenter.getView().updateSelectedUserFollowingAndFollowers(user);
        presenter.getView().updateFollowButton(isFollower);
    }
}
