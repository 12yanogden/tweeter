package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public interface AuthTokenDAO {
    AuthToken getAuthToken(String token);
    void putAuthToken(AuthToken authToken);
    void deleteAuthToken(String token);
}
