package edu.byu.cs.tweeter.server.dao;

public interface DAOFactory {
    FollowDAO makeFollowDAO();
    StatusDAO makeStatusDAO();
    UserDAO makeUserDAO();
}
