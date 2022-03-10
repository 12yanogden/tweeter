package edu.byu.cs.tweeter.client.presenter.observer;

import edu.byu.cs.tweeter.client.model.service.observerInterface.CountObserverInterface;
import edu.byu.cs.tweeter.client.presenter.MainPresenter;

public class GetFollowingCountObserver extends MainObserver implements CountObserverInterface {
    public GetFollowingCountObserver(MainPresenter presenter, String description) {
        super(presenter, description);
    }

    @Override
    public void handleSuccess(int count) {
        presenter.getView().setFollowingCount(count);
    }
}
