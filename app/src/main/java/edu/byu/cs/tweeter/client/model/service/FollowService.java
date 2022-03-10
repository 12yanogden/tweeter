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
import edu.byu.cs.tweeter.client.model.service.task.FollowTask;
import edu.byu.cs.tweeter.client.model.service.task.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.task.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.task.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.model.service.task.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.task.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.task.UnfollowTask;
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

    public void isFollower(AuthToken authToken, User user, User selectedUser, IsFollowerObserverInterface observer) {
        execute(getIsFollowerTask(authToken, user, selectedUser, observer, "/isfollower"));
    }

    public void unfollow(AuthToken authToken, User selectedUser, UserObserverInterface observer) {
        execute(getUnfollowTask(authToken, selectedUser, observer, "/unfollow"));
    }

    public void follow(AuthToken authToken, User selectedUser, UserObserverInterface observer) {
        execute(getFollowTask(authToken, selectedUser, observer, "/follow"));
    }

    public void getFollowersCount(AuthToken authToken, User selectedUser, CountObserverInterface observer, Executor executor) {
        executor.execute(getGetFollowersCountTask(authToken, selectedUser, observer, executor, "/getfollowerscount"));
    }

    public void getFollowingCount(AuthToken authToken, User selectedUser, CountObserverInterface observer, Executor executor) {
        executor.execute(getGetFollowingCount(authToken, selectedUser, observer, executor, "/getfollowingcount"));
    }

    /**
     * Returns an instance of {@link GetFollowingTask}. Allows mocking of the
     * GetFollowingTask class for testing purposes. All usages of GetFollowingTask
     * should get their instance from this method to allow for proper mocking.
     *
     * @return the instance.
     */
    // This method is public so it can be accessed by test cases
    public GetFollowingTask getGetFollowingTask(AuthToken authToken, User targetUser, int limit, User lastFollowee, PagedObserverInterface<User> observer, String urlPath) {
        return new GetFollowingTask(authToken, targetUser, limit, lastFollowee, new PagedHandler(observer), getServerFacade(), urlPath);
    }

    private GetFollowersTask getGetFollowersTask(AuthToken authToken, User user, int pageSize, User lastFollowee, PagedObserverInterface<User> observer, String urlPath) {
        return new GetFollowersTask(authToken, user, pageSize, lastFollowee, new PagedHandler<>(observer), getServerFacade(), urlPath);
    }

    private IsFollowerTask getIsFollowerTask(AuthToken authToken, User user, User selectedUser, IsFollowerObserverInterface observer, String urlPath) {
        return new IsFollowerTask(authToken, user, selectedUser, new IsFollowerHandler(observer), getServerFacade(), urlPath);
    }

    private UnfollowTask getUnfollowTask(AuthToken authToken, User selectedUser, UserObserverInterface observer, String urlPath) {
        return new UnfollowTask(authToken, selectedUser, new UserHandler(observer), getServerFacade(), urlPath);
    }

    private FollowTask getFollowTask(AuthToken authToken, User selectedUser, UserObserverInterface observer, String urlPath) {
        return new FollowTask(authToken, selectedUser, new UserHandler(observer), getServerFacade(), urlPath);
    }

    private GetFollowersCountTask getGetFollowersCountTask(AuthToken authToken, User selectedUser, CountObserverInterface observer, Executor executor, String urlPath) {
        return new GetFollowersCountTask(authToken, selectedUser, new CountHandler(observer), getServerFacade(), urlPath);
    }

    private GetFollowingCountTask getGetFollowingCount(AuthToken authToken, User selectedUser, CountObserverInterface observer, Executor executor, String urlPath) {
        return new GetFollowingCountTask(authToken, selectedUser, new CountHandler(observer), getServerFacade(), urlPath);
    }
}
