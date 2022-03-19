package edu.byu.cs.tweeter.server.dao.dynamoDB;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.util.FakeData;
import edu.byu.cs.tweeter.util.Pair;

public class DynamoDBUserDAO implements UserDAO  {
    @Override
    public Pair<User, AuthToken> register(String firstName, String lastName, String username, String password, String image) {
        User user = getFakeData().getFirstUser();
        AuthToken authToken = getFakeData().getAuthToken();

        return new Pair<>(user, authToken);
    }

    @Override
    public Pair<User, AuthToken> login(String username, String password) {
        User user = getFakeData().getFirstUser();
        AuthToken authToken = getFakeData().getAuthToken();

        return new Pair<>(user, authToken);
    }

    @Override
    public User getUser(String userAlias) {
        return getFakeData().findUserByAlias(userAlias);
    }

    protected FakeData getFakeData() {
        return new FakeData();
    }
}
