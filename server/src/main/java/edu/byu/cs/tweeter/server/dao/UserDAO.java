package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.User;

public interface UserDAO {
    User getUser(String alias);
    User getUser(String alias, String password);
    int getFollowerCount(String alias);
    int getFollowingCount(String alias);
    void incrementFollowingCount(String alias);
    void incrementFollowerCount(String alias);
    void decrementFollowingCount(String alias);
    void decrementFollowerCount(String alias);
    void putUser(User user, String password);
}
