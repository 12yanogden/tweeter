package edu.byu.cs.tweeter.client.model.service.task;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.PagedRequest;
import edu.byu.cs.tweeter.model.net.response.PagedResponse;
import edu.byu.cs.tweeter.util.Pair;

public abstract class PagedTask<T> extends AuthenticatedTask {
    private static final String LOG_TAG = "PagedTask";
    public static final String ITEMS_KEY = "items";
    public static final String MORE_PAGES_KEY = "more-pages";

    /**
     * The user whose items are being retrieved.
     * (This can be any user, not just the currently logged-in user.)
     */
    private final User targetUser;
    /**
     * Maximum number of items to return (i.e., page size).
     */
    private final int limit;
    /**
     * The last item returned in the previous page of results (can be null).
     * This allows the new page to begin where the previous page ended.
     */
    private final T lastItem;

    private List<T> items;
    private boolean hasMorePages;

    public PagedTask(AuthToken authToken, User targetUser, int limit, T lastItem, Handler messageHandler, ServerFacade facade, String urlPath) {
        super(authToken, messageHandler, facade, urlPath);

        this.targetUser = targetUser;
        this.limit = limit;
        this.lastItem = lastItem;
    }

    public User getTargetUser() {
        return targetUser;
    }

    public int getLimit() {
        return limit;
    }

    public T getLastItem() {
        return lastItem;
    }

    @Override
    protected void runTask() throws IOException, TweeterRemoteException {
        String targetUserAlias = getTargetUser() == null ? null : getTargetUser().getAlias();
        Pair<String, String> lastItemId = getLastItemId(getTargetUser(), getLastItem());

        PagedRequest request = new PagedRequest(getAuthToken(), targetUserAlias, getLimit(), lastItemId);
        PagedResponse<T> response = request(request, getUrlPath());

        if(response.isSuccess()) {
            setItems(response.getItems());
            setHasMorePages(response.getHasMorePages());
            sendSuccessMessage();

        } else {
            sendFailedMessage(response.getMessage());
        }
    }

    protected abstract PagedResponse<T> request(PagedRequest request, String urlPath) throws IOException, TweeterRemoteException;

    protected abstract Pair<String, String> getLastItemId(User targetUser, T lastItem);

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putSerializable(ITEMS_KEY, (Serializable) items);
        msgBundle.putBoolean(MORE_PAGES_KEY, hasMorePages);
    }

    protected void setItems(List<T> items) {
        this.items = items;
    }

    protected void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }
}
