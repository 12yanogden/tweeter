package edu.byu.cs.tweeter.server.dao;

public interface DAOFactory {
    AuthTokenDAO makeAuthTokenDAO();
    FollowDAO makeFollowDAO();
    StatusDAO makeStatusDAO();
    UserDAO makeUserDAO();
}
