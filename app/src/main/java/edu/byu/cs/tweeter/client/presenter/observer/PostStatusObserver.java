package edu.byu.cs.tweeter.client.presenter.observer;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleObserver;
import edu.byu.cs.tweeter.client.presenter.MainPresenter;

public class PostStatusObserver extends MainObserver implements SimpleObserver {
    public PostStatusObserver(MainPresenter presenter, String description) {
        super(presenter, description);
    }

    @Override
    public void handleSuccess() {
        presenter.getView().displayPostStatusSuccess("Successfully Posted!");
    }
}
