package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryPresenter {
    private static final int PAGE_SIZE = 10;
    private Status lastStatus;
    private boolean hasMorePages;
    private boolean isLoading = false;

    public interface View {
        void displayToast(String message);
        void setLoadingStatus(boolean status);
        void addStatuses(List<Status> statuses);
        void navToUserPage(User user);
    }

    private View view;
    private StatusService statusService;
    private UserService userService;

    public StoryPresenter(View view) {
        this.view = view;
        statusService = new StatusService();
        userService = new UserService();
    }

    public boolean isHasMorePages() {
        return hasMorePages;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }

    public void loadMoreItems(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingStatus(true);

            statusService.getStory(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastStatus, new GetStoryObserver());
        }
    }

    public class GetStoryObserver implements StatusService.GetStoryObserver {
        @Override
        public void handleSuccess(List<Status> statuses, boolean hasMorePages) {
            isLoading = false;
            view.setLoadingStatus(false);

            lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null;

            setHasMorePages(hasMorePages);

            view.addStatuses(statuses);
        }

        @Override
        public void handleFailure(String message) {
            isLoading = false;
            view.setLoadingStatus(false);

            view.displayToast("Failed to get story: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            isLoading = false;
            view.setLoadingStatus(false);

            view.displayToast("Failed to get story because of exception: " + exception.getMessage());
        }
    }

    public void getUser(String alias) {
        AuthToken authToken = Cache.getInstance().getCurrUserAuthToken();

        userService.getUser(authToken, alias, new GetUserObserver());
    }

    public class GetUserObserver implements UserService.GetUserObserver {
        @Override
        public void handleSuccess(User user) {
            view.navToUserPage(user);
        }

        @Override
        public void handleFailure(String message) {
            view.displayToast("Failed to get user's profile: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            view.displayToast("Failed to get user's profile because of exception: " + exception.getMessage());
        }
    }
}
