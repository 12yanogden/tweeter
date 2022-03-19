package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.User;

public interface FollowDAO extends PagedDAO<User> {
    int getFollowCount(String alias);
}
