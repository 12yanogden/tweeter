package edu.byu.cs.tweeter.client.presenter.observer;

import edu.byu.cs.tweeter.client.presenter.MainPresenter;

public class IsFollowerObserver extends MainObserver implements edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.IsFollowerObserver {
    public IsFollowerObserver(MainPresenter presenter, String description) {
        super(presenter, description);
    }

    @Override
    public void handleSuccess(boolean isFollower) {
        presenter.getView().updateFollowButton(isFollower);
    }
}
