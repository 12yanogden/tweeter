package edu.byu.cs.tweeter.client.model.service;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowService {

    public interface GetFollowingObserver {
        void handleSuccess(List<User> followees, boolean hasMorePages);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    public void getFollowing(AuthToken currUserAuthToken, User user, int pageSize, User lastFollowee, GetFollowingObserver getFollowingObserver) {
        GetFollowingTask getFollowingTask = new GetFollowingTask(currUserAuthToken,
                user, pageSize, lastFollowee, new GetFollowingHandler(getFollowingObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFollowingTask);
    }

    /**
     * Message handler (i.e., observer) for GetFollowingTask.
     */
    private class GetFollowingHandler extends Handler {
        private GetFollowingObserver getFollowingObserver;

        public GetFollowingHandler(GetFollowingObserver observer) {
            this.getFollowingObserver = observer;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(GetFollowingTask.SUCCESS_KEY);

            if (success) {
                List<User> followees = (List<User>) msg.getData().getSerializable(GetFollowingTask.FOLLOWEES_KEY);
                boolean hasMorePages = msg.getData().getBoolean(GetFollowingTask.MORE_PAGES_KEY);

                getFollowingObserver.handleSuccess(followees, hasMorePages);

            } else if (msg.getData().containsKey(GetFollowingTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(GetFollowingTask.MESSAGE_KEY);
                getFollowingObserver.handleFailure(message);

            } else if (msg.getData().containsKey(GetFollowingTask.EXCEPTION_KEY)) {
                Exception exception = (Exception) msg.getData().getSerializable(GetFollowingTask.EXCEPTION_KEY);
                getFollowingObserver.handleException(exception);
            }
        }
    }

    public interface GetFollowersObserver {
        void handleSuccess(List<User> followers, boolean hasMorePages);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    public void getFollowers(AuthToken currUserAuthToken, User user, int pageSize, User lastFollowee, GetFollowersObserver getFollowersObserver) {
        GetFollowersTask getFollowersTask = new GetFollowersTask(currUserAuthToken,
                user, pageSize, lastFollowee, new GetFollowersHandler(getFollowersObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFollowersTask);
    }

    /**
     * Message handler (i.e., observer) for GetFollowersTask.
     */
    private class GetFollowersHandler extends Handler {
        private GetFollowersObserver getFollowersObserver;

        public GetFollowersHandler(GetFollowersObserver observer) {
            this.getFollowersObserver = observer;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(GetFollowersTask.SUCCESS_KEY);

            if (success) {
                List<User> followees = (List<User>) msg.getData().getSerializable(GetFollowersTask.FOLLOWERS_KEY);
                boolean hasMorePages = msg.getData().getBoolean(GetFollowersTask.MORE_PAGES_KEY);

                getFollowersObserver.handleSuccess(followees, hasMorePages);

            } else if (msg.getData().containsKey(GetFollowersTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(GetFollowersTask.MESSAGE_KEY);
                getFollowersObserver.handleFailure(message);

            } else if (msg.getData().containsKey(GetFollowersTask.EXCEPTION_KEY)) {
                Exception exception = (Exception) msg.getData().getSerializable(GetFollowersTask.EXCEPTION_KEY);
                getFollowersObserver.handleException(exception);
            }
        }
    }

    public interface IsFollowerObserver {
        void handleSuccess(boolean isFollower);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    public void isFollower(AuthToken currUserAuthToken, User user, User selectedUser, IsFollowerObserver isFollowerObserver) {
        IsFollowerTask isFollowerTask = new IsFollowerTask(currUserAuthToken,
                user, selectedUser, new IsFollowerHandler(isFollowerObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(isFollowerTask);
    }

    private class IsFollowerHandler extends Handler {
        private IsFollowerObserver isFollowerObserver;

        public IsFollowerHandler(IsFollowerObserver isFollowerObserver) {
            this.isFollowerObserver = isFollowerObserver;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(IsFollowerTask.SUCCESS_KEY);

            if (success) {
                boolean isFollower = msg.getData().getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);

                isFollowerObserver.handleSuccess(isFollower);

            } else if (msg.getData().containsKey(IsFollowerTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(IsFollowerTask.MESSAGE_KEY);

                isFollowerObserver.handleFailure(message);

            } else if (msg.getData().containsKey(IsFollowerTask.EXCEPTION_KEY)) {
                Exception exception = (Exception) msg.getData().getSerializable(IsFollowerTask.EXCEPTION_KEY);

                isFollowerObserver.handleException(exception);
            }
        }
    }

    public interface UnfollowObserver {
        void handleSuccess(User followee);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    public void unfollow(AuthToken authToken, User selectedUser, UnfollowObserver unfollowObserver) {
        UnfollowTask unfollowTask = new UnfollowTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new UnfollowHandler(unfollowObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(unfollowTask);
    }

    private class UnfollowHandler extends Handler {
        private UnfollowObserver unfollowObserver;

        public UnfollowHandler(UnfollowObserver unfollowObserver) {
            this.unfollowObserver = unfollowObserver;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(UnfollowTask.SUCCESS_KEY);

            if (success) {
                String followeeAlias = msg.getData().getString(FollowTask.FOLLOWEE_ALIAS_KEY);
                String followeeFirstName = msg.getData().getString(FollowTask.FOLLOWEE_FIRSTNAME_KEY);
                String followeeLastName = msg.getData().getString(FollowTask.FOLLOWEE_LASTNAME_KEY);
                String followeeImageURL = msg.getData().getString(FollowTask.FOLLOWEE_IMAGEURL_KEY);
                User followee = new User(followeeAlias, followeeFirstName, followeeLastName, followeeImageURL);

                unfollowObserver.handleSuccess(followee);

            } else if (msg.getData().containsKey(UnfollowTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(UnfollowTask.MESSAGE_KEY);

                unfollowObserver.handleFailure(message);

            } else if (msg.getData().containsKey(UnfollowTask.EXCEPTION_KEY)) {
                Exception exception = (Exception) msg.getData().getSerializable(UnfollowTask.EXCEPTION_KEY);

                unfollowObserver.handleException(exception);
            }
        }
    }

    public interface FollowObserver {
        void handleSuccess(User followee);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    public void follow(AuthToken authToken, User selectedUser, FollowObserver followObserver) {
        FollowTask followTask = new FollowTask(authToken,
                selectedUser, new FollowHandler(followObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(followTask);
    }

    private class FollowHandler extends Handler {
        private FollowObserver followObserver;

        public FollowHandler(FollowObserver followObserver) {
            this.followObserver = followObserver;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(FollowTask.SUCCESS_KEY);

            if (success) {
                String followeeAlias = msg.getData().getString(FollowTask.FOLLOWEE_ALIAS_KEY);
                String followeeFirstName = msg.getData().getString(FollowTask.FOLLOWEE_FIRSTNAME_KEY);
                String followeeLastName = msg.getData().getString(FollowTask.FOLLOWEE_LASTNAME_KEY);
                String followeeImageURL = msg.getData().getString(FollowTask.FOLLOWEE_IMAGEURL_KEY);
                User followee = new User(followeeAlias, followeeFirstName, followeeLastName, followeeImageURL);

                followObserver.handleSuccess(followee);

            } else if (msg.getData().containsKey(FollowTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(FollowTask.MESSAGE_KEY);

                followObserver.handleFailure(message);

            } else if (msg.getData().containsKey(FollowTask.EXCEPTION_KEY)) {
                Exception exception = (Exception) msg.getData().getSerializable(FollowTask.EXCEPTION_KEY);

                followObserver.handleException(exception);
            }
        }
    }

    public interface GetFollowersCountObserver {
        void handleSuccess(int count);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    public void getFollowersCount(AuthToken authToken, User selectedUser, GetFollowersCountObserver getFollowersCountObserver, Executor executor) {
        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(authToken,
                selectedUser, new GetFollowersCountHandler(getFollowersCountObserver));
        executor.execute(followersCountTask);
    }

    private class GetFollowersCountHandler extends Handler {
        private GetFollowersCountObserver getFollowersCountObserver;

        public GetFollowersCountHandler(GetFollowersCountObserver getFollowersCountObserver) {
            this.getFollowersCountObserver = getFollowersCountObserver;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(GetFollowersCountTask.SUCCESS_KEY);

            if (success) {
                int count = msg.getData().getInt(GetFollowersCountTask.COUNT_KEY);

                getFollowersCountObserver.handleSuccess(count);

            } else if (msg.getData().containsKey(GetFollowersCountTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(GetFollowersCountTask.MESSAGE_KEY);

                getFollowersCountObserver.handleFailure(message);

            } else if (msg.getData().containsKey(GetFollowersCountTask.EXCEPTION_KEY)) {
                Exception exception = (Exception) msg.getData().getSerializable(GetFollowersCountTask.EXCEPTION_KEY);

                getFollowersCountObserver.handleException(exception);
            }
        }
    }

    public interface GetFollowingCountObserver {
        void handleSuccess(int count);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    public void getFollowingCount(AuthToken authToken, User selectedUser, GetFollowingCountObserver getFollowingCountObserver, Executor executor) {
        GetFollowingCountTask followingCountTask = new GetFollowingCountTask(authToken,
                selectedUser, new GetFollowingCountHandler(getFollowingCountObserver));
        executor.execute(followingCountTask);
    }

    private class GetFollowingCountHandler extends Handler {
        private GetFollowingCountObserver getFollowingCountObserver;

        public GetFollowingCountHandler(GetFollowingCountObserver getFollowingCountObserver) {
            this.getFollowingCountObserver = getFollowingCountObserver;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(GetFollowingCountTask.SUCCESS_KEY);

            if (success) {
                int count = msg.getData().getInt(GetFollowersCountTask.COUNT_KEY);

                getFollowingCountObserver.handleSuccess(count);

            } else if (msg.getData().containsKey(GetFollowingCountTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(GetFollowingCountTask.MESSAGE_KEY);

                getFollowingCountObserver.handleFailure(message);

            } else if (msg.getData().containsKey(GetFollowingCountTask.EXCEPTION_KEY)) {
                Exception exception = (Exception) msg.getData().getSerializable(GetFollowingCountTask.EXCEPTION_KEY);

                getFollowingCountObserver.handleException(exception);
            }
        }
    }
}
