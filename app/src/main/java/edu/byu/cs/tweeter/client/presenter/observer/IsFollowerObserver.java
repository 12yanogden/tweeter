package edu.byu.cs.tweeter.client.presenter.observer;

import edu.byu.cs.tweeter.client.model.service.observerInterface.IsFollowerObserverInterface;
import edu.byu.cs.tweeter.client.presenter.MainPresenter;

public class IsFollowerObserver extends MainObserver implements IsFollowerObserverInterface {
    public IsFollowerObserver(MainPresenter presenter, String description) {
        super(presenter, description);
    }

    @Override
    public void handleSuccess(boolean isFollower) {
        System.out.println("isFollowerObserver, isFollower: " + isFollower);

        presenter.getView().updateFollowButton(isFollower);
    }
}
