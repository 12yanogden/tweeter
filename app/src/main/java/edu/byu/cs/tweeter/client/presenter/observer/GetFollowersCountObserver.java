package edu.byu.cs.tweeter.client.presenter.observer;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.CountObserver;
import edu.byu.cs.tweeter.client.presenter.MainPresenter;

public class GetFollowersCountObserver extends MainObserver implements CountObserver {
    public GetFollowersCountObserver(MainPresenter presenter, String description) {
        super(presenter, description);
    }

    @Override
    public void handleSuccess(int count) {
        presenter.getView().setFollowersCount(count);
    }
}
