package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.presenter.observer.GetUserObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class PagedPresenter<T> extends ViewPresenter {
    public interface PagedView<T> extends View {
        void setLoadingStatus(boolean status);
        void addItems(List<T> items);
        void navToUserPage(User user);
    }

    private final PagedView<T> view;
    private static final int PAGE_SIZE = 10;
    private T lastItem;
    private boolean hasMorePages;
    private boolean isLoading;

    private UserService userService;

    public PagedPresenter(PagedView<T> view) {
        this.view = view;

        this.isLoading = false;
        this.userService = new UserService();
    }

    public void loadMoreItems(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            getView().setLoadingStatus(true);

            getItems(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastItem);
        }
    }

    protected abstract void getItems(AuthToken currUserAuthToken, User user, int pageSize, T lastItem);

    public T getLastItem() {
        return lastItem;
    }

    public boolean hasMorePages() {
        return hasMorePages;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public static int getPageSize() {
        return PAGE_SIZE;
    }

    public void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }

    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
        getView().setLoadingStatus(isLoading);
    }

    public void setLastItem(T lastItem) {
        this.lastItem = lastItem;
    }

    public PagedView<T> getView() {
        return this.view;
    }

    public void getUser(String alias) {
        AuthToken authToken = Cache.getInstance().getCurrUserAuthToken();

        getUserService().getUser(authToken, alias, new GetUserObserver(this, "get user's profile"));
    }
}
