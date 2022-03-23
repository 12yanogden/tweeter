package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;

public interface UserDAO {
    Pair<User, AuthToken> register(String firstName, String lastName, String alias, String password, String image);
    Pair<User, AuthToken> login(String alias, String password);
    User getUser(String alias);
}
