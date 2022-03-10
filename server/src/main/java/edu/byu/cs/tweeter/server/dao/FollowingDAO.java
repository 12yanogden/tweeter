package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public class FollowingDAO extends FollowDAO {
    @Override
    protected List<User> getAllUsers() {
        return getDummyFollowees();
    }
}
