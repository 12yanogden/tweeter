package edu.byu.cs.tweeter.client.presenter.observer;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.UserObserver;
import edu.byu.cs.tweeter.client.presenter.PagedPresenter;
import edu.byu.cs.tweeter.client.presenter.PagedPresenter.PagedView;
import edu.byu.cs.tweeter.model.domain.User;

public class GetUserObserver<T> extends PresenterObserver implements UserObserver {
    protected final PagedPresenter<T> presenter;
    protected final PagedView<T> view;

    public GetUserObserver(PagedPresenter<T> presenter, String description) {
        super(description);

        this.presenter = presenter;
        this.view = presenter.getView();
    }

    @Override
    public void handleSuccess(User user) {
        view.navToUserPage(user);
    }

    protected void updatePageState() {
    }

    @Override
    protected PagedPresenter<T> getPresenter() {
        return presenter;
    }
}
