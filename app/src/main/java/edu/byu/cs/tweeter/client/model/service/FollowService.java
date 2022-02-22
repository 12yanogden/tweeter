package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.Executor;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.CountHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.IsFollowerHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.PagedHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.UserHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.CountObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.IsFollowerObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PagedObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.UserObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowService extends ModelService {
    public void getFollowing(AuthToken authToken, User user, int pageSize, User lastFollowee, PagedObserver<User> observer) {
        GetFollowingTask getFollowingTask = new GetFollowingTask(authToken, user, pageSize, lastFollowee, new PagedHandler<User>(observer));

        execute(getFollowingTask);
    }

    public void getFollowers(AuthToken authToken, User user, int pageSize, User lastFollowee, PagedObserver<User> observer) {
        GetFollowersTask getFollowersTask = new GetFollowersTask(authToken, user, pageSize, lastFollowee, new PagedHandler<>(observer));

        execute(getFollowersTask);
    }

    public void isFollower(AuthToken authToken, User user, User selectedUser, IsFollowerObserver observer) {
        IsFollowerTask isFollowerTask = new IsFollowerTask(authToken, user, selectedUser, new IsFollowerHandler(observer));

        execute(isFollowerTask);
    }

    public void unfollow(AuthToken authToken, User selectedUser, UserObserver observer) {
        UnfollowTask unfollowTask = new UnfollowTask(authToken, selectedUser, new UserHandler(observer));

        execute(unfollowTask);
    }

    public void follow(AuthToken authToken, User selectedUser, UserObserver observer) {
        FollowTask followTask = new FollowTask(authToken, selectedUser, new UserHandler(observer));

        execute(followTask);
    }

    public void getFollowersCount(AuthToken authToken, User selectedUser, CountObserver observer, Executor executor) {
        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(authToken, selectedUser, new CountHandler(observer));

        executor.execute(followersCountTask);
    }

    public void getFollowingCount(AuthToken authToken, User selectedUser, CountObserver observer, Executor executor) {
        GetFollowingCountTask followingCountTask = new GetFollowingCountTask(authToken, selectedUser, new CountHandler(observer));

        executor.execute(followingCountTask);
    }

}
