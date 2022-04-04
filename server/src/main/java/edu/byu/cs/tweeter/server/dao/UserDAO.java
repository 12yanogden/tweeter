package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;

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
    void putUsers(List<Pair<User, String>> users);
}
