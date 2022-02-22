package edu.byu.cs.tweeter.client.presenter.observer;

import edu.byu.cs.tweeter.client.presenter.MainPresenter;

public abstract class MainObserver extends PresenterObserver {
    protected final MainPresenter presenter;

    public MainObserver(MainPresenter presenter, String description) {
        super(description);

        this.presenter = presenter;
    }

    @Override
    protected void updatePageState() {
        // No action required here
    }

    @Override
    protected MainPresenter getPresenter() {
        return presenter;
    }
}
