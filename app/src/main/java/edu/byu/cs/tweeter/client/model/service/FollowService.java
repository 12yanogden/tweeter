package edu.byu.cs.tweeter.client.model.service;

import java.util.List;
import java.util.concurrent.Executor;

import edu.byu.cs.tweeter.client.model.service.handler.CountHandler;
import edu.byu.cs.tweeter.client.model.service.handler.IsFollowerHandler;
import edu.byu.cs.tweeter.client.model.service.handler.PagedHandler;
import edu.byu.cs.tweeter.client.model.service.handler.UserHandler;
import edu.byu.cs.tweeter.client.model.service.observerInterface.CountObserverInterface;
import edu.byu.cs.tweeter.client.model.service.observerInterface.IsFollowerObserverInterface;
import edu.byu.cs.tweeter.client.model.service.observerInterface.PagedObserverInterface;
import edu.byu.cs.tweeter.client.model.service.observerInterface.UserObserverInterface;
import edu.byu.cs.tweeter.client.model.service.task.FollowCountTask;
import edu.byu.cs.tweeter.client.model.service.task.FollowUnfollowTask;
import edu.byu.cs.tweeter.client.model.service.task.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.task.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.task.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.task.PagedUserTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowService extends ModelService {
    /**
     * An observer interface to be implemented by observers who want to be notified when
     * asynchronous operations complete.
     */
    public interface GetFollowingObserver {
        void handleSuccess(List<User> followees, boolean hasMorePages);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    /**
     * Creates an instance.
     */
    public FollowService() {
    }

    /**
     * Requests the users that the user specified in the request is following.
     * Limits the number of followees returned and returns the next set of
     * followees after any that were returned in a previous request.
     * This is an asynchronous operation.
     *
     * @param authToken the session auth token.
     * @param targetUser the user for whom followees are being retrieved.
     * @param limit the maximum number of followees to return.
     * @param lastFollowee the last followee returned in the previous request (can be null).
     */
    public void getFollowing(AuthToken authToken, User targetUser, int limit, User lastFollowee, PagedObserverInterface<User> observer) {
        execute(getGetFollowingTask(authToken, targetUser, limit, lastFollowee, observer, "/getfollowing"));
    }

    public void getFollowers(AuthToken authToken, User user, int pageSize, User lastFollowee, PagedObserverInterface<User> observer) {
        execute(getGetFollowersTask(authToken, user, pageSize, lastFollowee, observer, "/getfollowers"));
    }

    public void isFollower(AuthToken authToken, User selectedUser, User user, IsFollowerObserverInterface observer) {
        execute(getIsFollowerTask(authToken, selectedUser, user, observer, "/isfollower"));
    }

    public void unfollow(AuthToken authToken, User selectedUser, User targetUser, UserObserverInterface observer) {
        execute(getFollowUnfollowTask(authToken, selectedUser, targetUser, observer, "/unfollow"));
    }

    public void follow(AuthToken authToken, User selectedUser, User targetUser, UserObserverInterface observer) {
        execute(getFollowUnfollowTask(authToken, selectedUser, targetUser, observer, "/follow"));
    }

    public void getFollowersCount(AuthToken authToken, User selectedUser, CountObserverInterface observer, Executor executor) {
        executor.execute(getFollowCountTask(authToken, selectedUser, observer, "/getfollowerscount"));
    }

    public void getFollowingCount(AuthToken authToken, User selectedUser, CountObserverInterface observer, Executor executor) {
        executor.execute(getFollowCountTask(authToken, selectedUser, observer, "/getfollowingcount"));
    }

    private IsFollowerTask getIsFollowerTask(AuthToken authToken, User selectedUser, User user, IsFollowerObserverInterface observer, String urlPath) {
        return new IsFollowerTask(authToken, selectedUser, user, new IsFollowerHandler(observer), getServerFacade(), urlPath);
    }

    private FollowUnfollowTask getFollowUnfollowTask(AuthToken authToken, User selectedUser, User targetUser, UserObserverInterface observer, String urlPath) {
        return new FollowUnfollowTask(authToken, selectedUser, targetUser, new UserHandler(observer), getServerFacade(), urlPath);
    }

    private GetFollowingTask getGetFollowingTask(AuthToken authToken, User user, int pageSize, User lastFollowee, PagedObserverInterface<User> observer, String urlPath) {
        return new GetFollowingTask(authToken, user, pageSize, lastFollowee, new PagedHandler<>(observer), getServerFacade(), urlPath);
    }

    private GetFollowersTask getGetFollowersTask(AuthToken authToken, User user, int pageSize, User lastFollowee, PagedObserverInterface<User> observer, String urlPath) {
        return new GetFollowersTask(authToken, user, pageSize, lastFollowee, new PagedHandler<>(observer), getServerFacade(), urlPath);
    }

    private FollowCountTask getFollowCountTask(AuthToken authToken, User selectedUser, CountObserverInterface observer, String urlPath) {
        return new FollowCountTask(authToken, selectedUser, new CountHandler(observer), getServerFacade(), urlPath);
    }
}
