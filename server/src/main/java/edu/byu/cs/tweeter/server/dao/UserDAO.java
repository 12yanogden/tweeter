package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;

public class UserDAO extends DAO {
    public Pair<User, AuthToken> register(String firstName, String lastName, String username, String password, String image) {
        User user = getFakeData().getFirstUser();
        AuthToken authToken = getFakeData().getAuthToken();

        return new Pair<>(user, authToken);
    }

    public Pair<User, AuthToken> login() {
        User user = getFakeData().getFirstUser();
        AuthToken authToken = getFakeData().getAuthToken();

        return new Pair<>(user, authToken);
    }

    public User getUser(String userAlias) {
        return getFakeData().findUserByAlias(userAlias);
    }
}
