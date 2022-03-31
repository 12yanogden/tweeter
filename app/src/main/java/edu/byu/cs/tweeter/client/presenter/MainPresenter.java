package edu.byu.cs.tweeter.client.presenter;

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
import edu.byu.cs.tweeter.model.domain.Status;
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
    private FollowService followService;
    private StatusService statusService;

    public MainPresenter (MainView view) {
        this.view = view;
    }

    protected FollowService getFollowService() {
        if (followService == null) {
            followService = new FollowService();
        }

        return followService;
    }

    protected StatusService getStatusService() {
        if (statusService == null) {
            statusService = new StatusService();
        }

        return statusService;
    }

    public void updateFollowButton(User selectedUser) {
        getFollowService().isFollower(Cache.getInstance().getCurrUserAuthToken(), selectedUser, Cache.getInstance().getCurrUser(), new IsFollowerObserver(this, "determine following relationship"));
    }

    public void logout() {
        getUserService().logout(Cache.getInstance().getCurrUserAuthToken(), new LogoutObserver(this, "logout"));
    }

    public void postStatus(String post, String LOG_TAG) {
        try {
            getView().displayToast("Posting status...");

            Status status = new Status(post, Cache.getInstance().getCurrUser(), getFormattedDateTime(), parseURLs(post), parseMentions(post));

            getStatusService().postStatus(status, Cache.getInstance().getCurrUserAuthToken(), getPostStatusObserver());
        } catch (Exception exception) {
//            Log.e(LOG_TAG, exception.getMessage(), exception);
            getView().displayToast("Failed to post the status because of exception: " + exception.getMessage());
        }
    }

    protected PostStatusObserver getPostStatusObserver() {
        return new PostStatusObserver(this, "post status");
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
        getFollowService().unfollow(Cache.getInstance().getCurrUserAuthToken(), selectedUser, Cache.getInstance().getCurrUser(), new FollowUnfollowObserver(this, "unfollow", false));
    }

    public void follow(User selectedUser) {
        getFollowService().follow(Cache.getInstance().getCurrUserAuthToken(), selectedUser, Cache.getInstance().getCurrUser(), new FollowUnfollowObserver(this, "follow", true));
    }

    public void updateSelectedUsersFollowingAndFollowers(User selectedUser) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        getFollowService().getFollowersCount(Cache.getInstance().getCurrUserAuthToken(), selectedUser, new GetFollowersCountObserver(this, "get following count"), executor);
        getFollowService().getFollowingCount(Cache.getInstance().getCurrUserAuthToken(), selectedUser, new GetFollowingCountObserver(this, "get followers count"), executor);
    }

    @Override
    public MainView getView() {
        return view;
    }
}