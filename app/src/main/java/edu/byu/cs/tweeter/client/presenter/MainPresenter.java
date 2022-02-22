package edu.byu.cs.tweeter.client.presenter;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenter {
    public interface View {
        void displayToast(String message);
        void updateFollowButton(boolean isFollower);
        void logoutUser();
        void displayPostStatusSuccess(String message);
        void updateSelectedUserFollowingAndFollowers(User selectedUser);
        void setFollowersCount(int count);
        void setFollowingCount(int count);
    }

    private View view;
    private FollowService followService;
    private StatusService statusService;
    private UserService userService;

    public MainPresenter (View view) {
        this.view = view;
        this.followService = new FollowService();
        this.statusService = new StatusService();
        this.userService = new UserService();
    }

    public void updateFollowButton(User selectedUser) {
        followService.isFollower(Cache.getInstance().getCurrUserAuthToken(), Cache.getInstance().getCurrUser(), selectedUser, new IsFollowerObserver());
    }

    public class IsFollowerObserver implements FollowService.IsFollowerObserver {
        @Override
        public void handleSuccess(boolean isFollower) {
            view.updateFollowButton(isFollower);
        }

        @Override
        public void handleFailure(String message) {
            view.displayToast("Failed to determine following relationship: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            view.displayToast("Failed to determine following relationship because of exception: " + exception.getMessage());
        }
    }

    public void logout() {
        userService.logout(Cache.getInstance().getCurrUserAuthToken(), new LogoutObserver());
    }

    public class LogoutObserver implements UserService.LogoutObserver {
        @Override
        public void handleSuccess() {
            view.logoutUser();
        }

        @Override
        public void handleFailure(String message) {
            view.displayToast("Failed to logout: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            view.displayToast("Failed to logout because of exception: " + exception.getMessage());
        }
    }

    public void postStatus(String post, String LOG_TAG) {
        try {
            statusService.postStatus(post, Cache.getInstance().getCurrUser(), getFormattedDateTime(), parseURLs(post), parseMentions(post), Cache.getInstance().getCurrUserAuthToken(), new PostStatusObserver());
        } catch (Exception exception) {
            Log.e(LOG_TAG, exception.getMessage(), exception);
            view.displayToast("Failed to post the status because of exception: " + exception.getMessage());
        }
    }

    public class PostStatusObserver implements StatusService.PostStatusObserver {
        @Override
        public void handleSuccess() {
            view.displayPostStatusSuccess("Successfully Posted!");
        }

        @Override
        public void handleFailure(String message) {
            view.displayToast("Failed to post status: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            view.displayToast("Failed to post status because of exception: " + exception.getMessage());
        }
    }

    public String getFormattedDateTime() throws ParseException {
        SimpleDateFormat userFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");

        return statusFormat.format(userFormat.parse(LocalDate.now().toString() + " " + LocalTime.now().toString().substring(0, 8)));
    }

    public List<String> parseURLs(String post) {
        List<String> containedUrls = new ArrayList<>();
        for (String word : post.split("\\s")) {
            if (word.startsWith("http://") || word.startsWith("https://")) {

                int index = findUrlEndIndex(word);

                word = word.substring(0, index);

                containedUrls.add(word);
            }
        }

        return containedUrls;
    }

    public int findUrlEndIndex(String word) {
        if (word.contains(".com")) {
            int index = word.indexOf(".com");
            index += 4;
            return index;
        } else if (word.contains(".org")) {
            int index = word.indexOf(".org");
            index += 4;
            return index;
        } else if (word.contains(".edu")) {
            int index = word.indexOf(".edu");
            index += 4;
            return index;
        } else if (word.contains(".net")) {
            int index = word.indexOf(".net");
            index += 4;
            return index;
        } else if (word.contains(".mil")) {
            int index = word.indexOf(".mil");
            index += 4;
            return index;
        } else {
            return word.length();
        }
    }

    public List<String> parseMentions(String post) {
        List<String> containedMentions = new ArrayList<>();

        for (String word : post.split("\\s")) {
            if (word.startsWith("@")) {
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                word = "@".concat(word);

                containedMentions.add(word);
            }
        }

        return containedMentions;
    }

    public void unfollow(User selectedUser) {
        followService.unfollow(Cache.getInstance().getCurrUserAuthToken(), selectedUser, new UnfollowObserver());
    }

    public class UnfollowObserver implements FollowService.UnfollowObserver {
        @Override
        public void handleSuccess(User followee) {
            view.updateSelectedUserFollowingAndFollowers(followee);
            view.updateFollowButton(false);
        }

        @Override
        public void handleFailure(String message) {
            view.displayToast("Failed to unfollow: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            view.displayToast("Failed to unfollow because of exception: " + exception.getMessage());
        }
    }

    public void follow(User selectedUser) {
        followService.follow(Cache.getInstance().getCurrUserAuthToken(), selectedUser, new FollowObserver());
    }

    public class FollowObserver implements FollowService.FollowObserver {
        @Override
        public void handleSuccess(User followee) {
            view.updateSelectedUserFollowingAndFollowers(followee);
            view.updateFollowButton(true);
        }

        @Override
        public void handleFailure(String message) {
            view.displayToast("Failed to follow: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            view.displayToast("Failed to follow because of exception: " + exception.getMessage());
        }
    }

    public void updateSelectedUsersFollowingAndFollowers(User selectedUser) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        followService.getFollowersCount(Cache.getInstance().getCurrUserAuthToken(), selectedUser, new GetFollowersCountObserver(), executor);
        followService.getFollowingCount(Cache.getInstance().getCurrUserAuthToken(), selectedUser, new GetFollowingCountObserver(), executor);
    }

    public class GetFollowersCountObserver implements FollowService.GetFollowersCountObserver {
        @Override
        public void handleSuccess(int count) {
            view.setFollowersCount(count);
        }

        @Override
        public void handleFailure(String message) {
            view.displayToast("Failed to get followers count: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            view.displayToast("Failed to get followers count because of exception: " + exception.getMessage());
        }
    }

    public class GetFollowingCountObserver implements FollowService.GetFollowingCountObserver {
        @Override
        public void handleSuccess(int count) {
            view.setFollowingCount(count);
        }

        @Override
        public void handleFailure(String message) {
            view.displayToast("Failed to get following count: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            view.displayToast("Failed to get following count because of exception: " + exception.getMessage());
        }
    }
}
