package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.PagedRequest;
import edu.byu.cs.tweeter.model.net.response.PagedResponse;
import edu.byu.cs.tweeter.util.FakeData;

/**
 * A DAO for accessing 'following' data from the database.
 */
public class FollowDAO extends PagedDAO<User> {

    /**
     * Gets the count of users from the database that the user specified is following. The
     * current implementation uses generated data and doesn't actually access a database.
     *
     * @param targetUserAlias the User whose count of how many following is desired.
     * @return said count.
     */
    public Integer getFollowCount(String targetUserAlias) {
        // TODO: uses the dummy data.  Replace with a real implementation.
        assert targetUserAlias != null;
        return getAllItems().size();
    }

    @Override
    protected List<User> getAllItems() {
        return getFakeData().getFakeUsers();
    }

    @Override
    protected String getItemId(User item) {
        return item == null ? null : item.getAlias();
    }
}
