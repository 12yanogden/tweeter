package edu.byu.cs.tweeter.server.dao;

public interface DAOFactory {
    AuthTokenDAO makeAuthTokenDAO();
    FollowDAO makeFollowDAO();
    StoryDAO makeStoryDAO();
    FeedDAO makeFeedDAO();
    UserDAO makeUserDAO();
}
