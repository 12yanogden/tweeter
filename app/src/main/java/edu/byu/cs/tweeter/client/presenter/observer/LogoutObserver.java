package edu.byu.cs.tweeter.client.presenter.observer;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleObserver;
import edu.byu.cs.tweeter.client.presenter.MainPresenter;

public class LogoutObserver extends MainObserver implements SimpleObserver {
    public LogoutObserver(MainPresenter presenter, String description) {
        super(presenter, description);
    }

    @Override
    public void handleSuccess() {
        presenter.getView().logoutUser();
    }
}
