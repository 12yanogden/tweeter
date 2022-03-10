package edu.byu.cs.tweeter.client.presenter.observer;

import edu.byu.cs.tweeter.client.model.service.observerInterface.SimpleObserverInterface;
import edu.byu.cs.tweeter.client.presenter.MainPresenter;

public class PostStatusObserver extends MainObserver implements SimpleObserverInterface {
    public PostStatusObserver(MainPresenter presenter, String description) {
        super(presenter, description);
    }

    @Override
    public void handleSuccess() {
        getPresenter().getView().displayPostStatusSuccess("Successfully Posted!");
    }
}
