package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.User;

public interface FollowDAO extends PagedDAO<User> {
    int getFollowerCount(String alias);
    int getFollowingCount(String alias);
    void follow(String followeeAlias, String followerAlias);
    void unfollow(String followeeAlias, String followerAlias);
    boolean isFollower(String followeeAlias, String follwerAlias);
}
