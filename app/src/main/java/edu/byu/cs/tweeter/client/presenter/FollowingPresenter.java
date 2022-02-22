package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowingPresenter {
    private static final int PAGE_SIZE = 10;
    private User lastFollowee;
    private boolean hasMorePages;
    private boolean isLoading = false;

    public interface View {
        void displayToast(String message);
        void setLoadingStatus(boolean status);
        void addFollowees(List<User> followees);
        void navToUserPage(User user);
    }

    private View view;
    private FollowService followService;
    private UserService userService;

    public FollowingPresenter(View view) {
        this.view = view;
        followService = new FollowService();
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

            followService.getFollowing(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastFollowee, new GetFollowingObserver());
        }
    }

    public class GetFollowingObserver implements FollowService.GetFollowingObserver {
        @Override
        public void handleSuccess(List<User> followees, boolean hasMorePages) {
            isLoading = false;
            view.setLoadingStatus(false);

            lastFollowee = (followees.size() > 0) ? followees.get(followees.size() - 1) : null;

            setHasMorePages(hasMorePages);

            view.addFollowees(followees);
        }

        @Override
        public void handleFailure(String message) {
            isLoading = false;
            view.setLoadingStatus(false);

            view.displayToast("Failed to get following: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            isLoading = false;
            view.setLoadingStatus(false);

            view.displayToast("Failed to get following because of exception: " + exception.getMessage());
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
