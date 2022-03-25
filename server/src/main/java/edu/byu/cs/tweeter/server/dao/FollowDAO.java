package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;

public interface FollowDAO extends PagedDAO<User> {
    void putItem(User followee, User follower);
    boolean isFollower(String followeeAlias, String follwerAlias);
    Pair<List<User>, Boolean> queryFollowing(String follower, int limit, Pair<String, String> lastItemId);
    Pair<List<User>, Boolean> queryFollowers(String targetUserAlias, int limit, Pair<String, String> lastItemId);
    void deleteItem(String followeeAlias, String followerAlias);
    List<User> getFollowers(String followeeAlias);
}
