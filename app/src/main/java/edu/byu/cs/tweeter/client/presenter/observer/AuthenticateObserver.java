package edu.byu.cs.tweeter.client.presenter.observer;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.presenter.AuthenticatePresenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class AuthenticateObserver extends PresenterObserver implements edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.AuthenticateObserver {
    private final AuthenticatePresenter presenter;

    public AuthenticateObserver(AuthenticatePresenter presenter, String description) {
        super(description);

        this.presenter = presenter;
    }

    @Override
    protected void updatePageState() {
        // Empty
    }

    @Override
    protected AuthenticatePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void handleSuccess(User user, AuthToken authToken) {
        Cache.getInstance().setCurrUser(user);
        Cache.getInstance().setCurrUserAuthToken(authToken);

        presenter.getView().authenticate(user);
    }
}
