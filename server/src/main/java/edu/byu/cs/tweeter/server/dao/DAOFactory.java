package edu.byu.cs.tweeter.server.dao;

public interface DAOFactory {
    AuthTokenDAO makeAuthTokenDAO();
    BucketDAO makeBucketDAO();
    FollowDAO makeFollowDAO();
    FeedDAO makeFeedDAO();
    QueueDAO makePostStatusQueue1DAO();
    QueueDAO makePostStatusQueue2DAO();
    StoryDAO makeStoryDAO();
    UserDAO makeUserDAO();
}
