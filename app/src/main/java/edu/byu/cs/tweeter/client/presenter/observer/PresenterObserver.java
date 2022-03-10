package edu.byu.cs.tweeter.client.presenter.observer;

import edu.byu.cs.tweeter.client.model.service.observerInterface.ServiceObserverInterface;
import edu.byu.cs.tweeter.client.presenter.ViewPresenter;

public abstract class PresenterObserver implements ServiceObserverInterface {
    protected String description;

    public PresenterObserver(String description) {
        this.description = description;
    }

    protected abstract void updatePageState();

    protected abstract <P extends ViewPresenter> P getPresenter();

    @Override
    public void handleFailure(String message) {
        updatePageState();

        getPresenter().getView().displayToast("Failed to " + description + ": " + message);
    }

    @Override
    public void handleException(Exception exception) {
        updatePageState();

        getPresenter().getView().displayToast("Failed to " + description + " because of exception: " + exception.getMessage());
    }
}
