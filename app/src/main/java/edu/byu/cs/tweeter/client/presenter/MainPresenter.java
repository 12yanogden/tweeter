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
import edu.byu.cs.tweeter.client.presenter.observer.FollowUnfollowObserver;
import edu.byu.cs.tweeter.client.presenter.observer.GetFollowersCountObserver;
import edu.byu.cs.tweeter.client.presenter.observer.GetFollowingCountObserver;
import edu.byu.cs.tweeter.client.presenter.observer.IsFollowerObserver;
import edu.byu.cs.tweeter.client.presenter.observer.LogoutObserver;
import edu.byu.cs.tweeter.client.presenter.observer.PostStatusObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenter extends ViewPresenter {
    public interface MainView extends View {
        void updateFollowButton(boolean isFollower);
        void logoutUser();
        void displayPostStatusSuccess(String message);
        void updateSelectedUserFollowingAndFollowers(User selectedUser);
        void setFollowersCount(int count);
        void setFollowingCount(int count);
    }

    private final MainView view;
    private final FollowService followService;
    private final StatusService statusService;

    public MainPresenter (MainView view) {
        this.view = view;
        this.followService = new FollowService();
        this.statusService = new StatusService();
    }

    public void updateFollowButton(User selectedUser) {
        followService.isFollower(Cache.getInstance().getCurrUserAuthToken(), Cache.getInstance().getCurrUser(), selectedUser, new IsFollowerObserver(this, "determine following relationship"));
    }

    public void logout() {
        userService.logout(Cache.getInstance().getCurrUserAuthToken(), new LogoutObserver(this, "logout"));
    }

    public void postStatus(String post, String LOG_TAG) {
        try {
            statusService.postStatus(post, Cache.getInstance().getCurrUser(), getFormattedDateTime(), parseURLs(post), parseMentions(post), Cache.getInstance().getCurrUserAuthToken(), new PostStatusObserver(this, "post status"));
        } catch (Exception exception) {
            Log.e(LOG_TAG, exception.getMessage(), exception);
            view.displayToast("Failed to post the status because of exception: " + exception.getMessage());
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
        followService.unfollow(Cache.getInstance().getCurrUserAuthToken(), selectedUser, new FollowUnfollowObserver(this, "unfollow", false));
    }

    public void follow(User selectedUser) {
        followService.follow(Cache.getInstance().getCurrUserAuthToken(), selectedUser, new FollowUnfollowObserver(this, "follow", true));
    }

    public void updateSelectedUsersFollowingAndFollowers(User selectedUser) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        followService.getFollowersCount(Cache.getInstance().getCurrUserAuthToken(), selectedUser, new GetFollowersCountObserver(this, "get following count"), executor);
        followService.getFollowingCount(Cache.getInstance().getCurrUserAuthToken(), selectedUser, new GetFollowingCountObserver(this, "get followers count"), executor);
    }

    @Override
    public MainView getView() {
        return view;
    }
}